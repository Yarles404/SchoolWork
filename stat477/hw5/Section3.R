###################################################################
##Run the function for confidence intervals for relative risk
##and odds ratio
###################################################################

 rr.ci<- function(y, n, conf.level){
   y1<- y[1]
   y2<- y[2]
   n1<- n[1]
   n2<- n[2]
   alpha<- 1 - conf.level
   z<- qnorm(1 - alpha/2)
   
   phat1<- y1/n1
   phat2<- y2/n2
   
   rr<- phat1/phat2
   
   selogrr<- sqrt((1-phat1)/(n1*phat1) + (1-phat2)/(n2*phat2))
   
   logrr.lower<- log(rr) - z*selogrr
   logrr.upper<- log(rr) + z*selogrr
   
   rr.lower<- exp(logrr.lower)
   rr.upper<- exp(logrr.upper)
   
   cat("Estimated Relative Risk = ", rr, "\n")
   cat("Confidence Interval for Population Relative Risk = ", rr.lower, rr.upper, "\n")
 }
 
 or.ci<- function(y, n, conf.level){
   y1<- y[1]
   y2<- y[2]
   n1<- n[1]
   n2<- n[2]
   alpha<- 1 - conf.level
   z<- qnorm(1 - alpha/2)
   
   phat1<- y1/n1
   phat2<- y2/n2
   
   or<- (phat1/(1-phat1))/(phat2/(1-phat2))
   
   selogor<- sqrt(1/(n1*phat1) + 1/(n1*(1-phat1)) + 1/(n2*phat2) + 1/(n2*(1-phat2)))
   
   logor.lower<- log(or) - z*selogor
   logor.upper<- log(or) + z*selogor
   
   or.lower<- exp(logor.lower)
   or.upper<- exp(logor.upper)
   
   cat("Estimated Odds Ratio = ", or, "\n")
   cat("Confidence Interval for Population Odds Ratio = ", or.lower, or.upper, "\n")
 }
 
#################################################################

##Relative Risk
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
  
  groups<- margin.table(surgery.table, 1)
  
  rr.ci(surgery.table[,1], groups, conf.level = 0.95)
  
##Odds Ratio
  ##Open the survey data
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

  ##Calculate observed odds ratio and confidence interval
  groups<- margin.table(survey.table, 1)  
  
  ##Odds Ratio
  or.ci(survey.table[,1], groups, conf.level = 0.95)
