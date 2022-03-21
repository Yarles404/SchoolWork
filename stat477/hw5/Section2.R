##############################################################
##Load the necessary libraries for analysis
  library(ggplot2)
  library(ggmosaic)

##Run the functions to calculate sample sizes for hypothesis 
##tests and confidence intervals
  n2prop.test<- function(p1, p2, alternative, alpha, power) {
    
    ztwo<- qnorm(1 - alpha/2)
    zone<- qnorm(1 - alpha)
    zpower<- qnorm(power)
    
    p0<- (p1 + p2)/2
    R<- sqrt((2*p0*(1-p0))/((p1*(1-p1) + p2*(1-p2))))
    
    switch(alternative, two.sided = ceiling((zpower + R*ztwo)^2*(p1*(1-p1)+p2*(1-p2))/(p1-p2)^2), 
           greater = ceiling((zpower + R*zone)^2*(p1*(1-p1)+p2*(1-p2))/(p1-p2)^2), 
           less = ceiling((zpower + R*zone)^2*(p1*(1-p1)+p2*(1-p2))/(p1-p2)^2))
  }

  n2prop.ci<- function(p1, p2, m, conf.level) {
    
    alpha<- 1 - conf.level
    z<- qnorm(1 - alpha/2)
    ceiling((z/m)^2*(p1*(1-p1)+p2*(1-p2)))
  }
################################################################
##Part A
################################################################

##Analysis of the Doctor's Survey Data
  ##Read in the data file
  survey.data<- read.csv(file.choose(), header = T)
  ##Set the baseline category to be No for both variables
  survey.data$Receive.Letter<- factor(survey.data$Receive.Letter, 
                  levels = c("Yes", "No"))
  survey.data$Return.Survey<- factor(survey.data$Return.Survey, 
                  levels = c("Yes", "No"))

  ##Calculate the contingency table
  survey.table<- table(survey.data$Receive.Letter, 
                     survey.data$Return.Survey)
  survey.table
  
  ##Graph the mosaic plot
  ggplot(data = survey.data)+
    geom_mosaic(aes(x = product(Return.Survey, Receive.Letter), 
                    fill = Return.Survey), 
                na.rm = TRUE, divider =  mosaic("h"))+
    theme_bw()+
    theme(plot.title = element_text(hjust=0.5, size = rel(2)),
          axis.title.y = element_text(size = rel(1.8)),
          axis.title.x = element_text(size = rel(1.8)),
          axis.text.x = element_text(size = rel(1.8)),
          axis.text.y = element_text(size = rel(1.8)),
          strip.text.y = element_text(size = rel(1.8)))+
    labs(x = "Did they Receive the Letter?", 
         y = "Did they Return the Survey?", 
         fill = "Did they Return the Survey?", 
         title = "Mosaic Plot of Doctor Survey Data")
  
  ##Calculate the size for each group
  letter<- margin.table(survey.table, 1)

  ##Obtain the hypothesis test for the difference in
  ##two proportions
  prop.test(survey.table[,1], letter, 
          alternative = "two.sided", correct = F)

##Analysis of Cancer Surgery Data
  ##Read in the data file
  surgery.data<- read.csv(file.choose(), header = T)
  ##Set the baseline category for each variable to No
  surgery.data$Surgery<- factor(surgery.data$Surgery, 
                    levels = c("Yes", "No"))
  surgery.data$Died<- factor(surgery.data$Died, 
                    levels = c("Yes", "No"))

  ##Calculate the contingency table for the two variables
  surgery.table<- table(surgery.data$Surgery, 
                        surgery.data$Died)
  surgery.table
  
  ##Graph the mosaic plot
  ggplot(data = surgery.data)+
    geom_mosaic(aes(x = product(Died, Surgery), 
                    fill = Died), 
                na.rm = TRUE, divider =  mosaic("h"))+
    theme_bw()+
    theme(plot.title = element_text(hjust=0.5, size = rel(2)),
          axis.title.y = element_text(size = rel(1.8)),
          axis.title.x = element_text(size = rel(1.8)),
          axis.text.x = element_text(size = rel(1.8)),
          axis.text.y = element_text(size = rel(1.8)),
          strip.text.y = element_text(size = rel(1.8)))+
    labs(x = "Surgery for Prostate Cancer", 
         y = "Died from Prostate Cancer", 
         fill = "Died from Prostate Cancer", 
         title = "Mosaic Plot of Cancer Surgery Data")
  
  ##Calculate the size for each group
  groups<- margin.table(surgery.table, 1)
  
  ##Find the confidence interval for the difference in 
  ##two proportions
  prop.test(surgery.table[,1], groups, 
            alternative = "two.sided", conf.level = 0.95, 
            correct = F)

##Sample size Calculations
  
  ##For Doctor Survey Hypothesis Test
  n2prop.test(p1 = 0.55, p2 = 0.5, alternative = "two.sided",
            alpha = 0.05, power = 0.9)

  ##For Cancer Surgery Confidence Interval
  n2prop.ci(p1 = 0.05, p2 = 0.09, m = 0.03, conf.level = 0.95)

################################################################
##Part B
################################################################

##Analysis of angina data
  ##Read in the data
  angina.data<- read.csv(file.choose(), header = T)
  
  ##Set the category order for the two variables
  angina.data$Outcome<- factor(angina.data$Outcome, 
                    levels = c("No.Angina", "Angina"))
  angina.data$Drug<- factor(angina.data$Drug, 
                            levels = c("Timolol", "Placebo"))
  
  ##Calculate the contingency table for the two variables
  angina.table<- table(angina.data$Drug, angina.data$Outcome)
  angina.table
  
  ##Graph the mosaic plot
  ggplot(data = angina.data)+
    geom_mosaic(aes(x = product(Outcome, Drug), 
                    fill = Outcome), 
                na.rm = TRUE, divider =  mosaic("h"))+
    theme_bw()+
    theme(plot.title = element_text(hjust=0.5, size = rel(2)),
          axis.title.y = element_text(size = rel(1.8)),
          axis.title.x = element_text(size = rel(1.8)),
          axis.text.x = element_text(size = rel(1.8)),
          axis.text.y = element_text(size = rel(1.8)),
          strip.text.y = element_text(size = rel(1.8)))+
    labs(x = "Drug Taken", 
         y = "Outcome", 
         fill = "Outcome", 
         title = "Mosaic Plot of Angina Treatment Data")
  
  ##Calculate the size of each group
  group<- margin.table(angina.table, 1)
  
  ##Obtain the hypothesis test for the equality of proportion
  ##of non-conforming in each lot
  prop.test(angina.table[,1], group, alternative = "two.sided",
            correct = F)
  
##Analysis of Diode data
  ##Read in the data
  diode.data<- read.csv(file.choose(), header = T)

  ##Set the category order for the response variable
  diode.data$Status<- factor(diode.data$Status, 
                    levels = c("Non-Conforming", "Conforming"))
  ##Set the grouping variable to be categorical
  diode.data$Lot<- as.factor(diode.data$Lot)

  ##Calculate the contingency table for the two variables
  diode.table<- table(diode.data$Lot, diode.data$Status)
  diode.table
  
  ##Graph the mosaic plot
  ggplot(data = diode.data)+
    geom_mosaic(aes(x = product(Status, Lot), 
                    fill = Status), 
                na.rm = TRUE, divider =  mosaic("h"))+
    theme_bw()+
    theme(plot.title = element_text(hjust=0.5, size = rel(2)),
          axis.title.y = element_text(size = rel(1.8)),
          axis.title.x = element_text(size = rel(1.8)),
          axis.text.x = element_text(size = rel(1.8)),
          axis.text.y = element_text(size = rel(1.8)),
          strip.text.y = element_text(size = rel(1.8)))+
    labs(x = "Lot Number", 
         y = "Status", 
         fill = "Status", 
         title = "Mosaic Plot of Diode Data")
  
  ##Calculate the size of each group
  lotsize<- margin.table(diode.table, 1)

  ##Obtain the hypothesis test for the equality of proportion
  ##of non-conforming in each lot
  prop.test(diode.table[,1], lotsize)

  ##Find the pairwise comparisons for the proportion 
  ##of non-conforming in each lot
  pairwise.prop.test(diode.table[,1], lotsize, 
                   p.adjust.method = "BH")

###############################################################
##Part C
###############################################################
  
  ##Read in the baby survey data
  baby2.data<- read.csv(file.choose(), header = T)
  
  ##Calculate the contingency table
  baby2.table<- table(baby2.data$Gender, baby2.data$Baby)
  baby2.table
  
  ##Graph the mosaic plot
  ggplot(data = baby2.data)+
    geom_mosaic(aes(x = product(Baby, Gender), 
                    fill = Baby), 
                na.rm = TRUE, divider =  mosaic("h"))+
    theme_bw()+
    theme(plot.title = element_text(hjust=0.5, size = rel(2)),
          axis.title.y = element_text(size = rel(1.8)),
          axis.title.x = element_text(size = rel(1.8)),
          axis.text.x = element_text(size = rel(1.8)),
          axis.text.y = element_text(size = rel(1.8)),
          strip.text.y = element_text(size = rel(1.8)))+
    labs(x = "Gender of Judge", 
         y = "Baby Selected", 
         fill = "Baby Selected", 
         title = "Mosaic Plot of Baby Survey Data")
  
  ##Obtain the hypothesis test for the equality of the 
  ##distribution of babies selected between the two genders
  baby2.test<- chisq.test(baby2.table)
  baby2.test

  ##Additional information can be obtained from the 
  ##variable baby2.test
    ##Value of the test statistic
    baby2.test$statistic 
    ##Estimated expected values for each cell
    baby2.test$expected
    ##Contribution to the test statistic for each cell
    (baby2.test$residual)^2
    