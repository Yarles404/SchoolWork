#################################################################################################
##Open the necessary libraries
  library(ggplot2)
  library(ggmosaic)
#################################################################################################
  
##Read in the data file
  smoking.data<- read.csv(file.choose(), header = T)

##Order the categories for each variable
  smoking.data$Student<- factor(smoking.data$Student, 
             levels = c("Non-Smoker", "Smoker"))
  smoking.data$Parent<- factor(smoking.data$Parent, 
             levels = c("Neither", "One", "Both"))

##Calculate the contingency table for the two variables
  smoking.table<- table(smoking.data$Parent, 
                        smoking.data$Student)
  smoking.table

##Graph the mosaic plot
  ggplot(data = smoking.data)+
    geom_mosaic(aes(x = product(Student, Parent), fill = Student), 
                na.rm = TRUE, divider =  mosaic("h"))+
    theme_bw()+
    theme(plot.title = element_text(hjust=0.5, size = rel(2)),
          axis.title.y = element_text(size = rel(1.8)),
          axis.title.x = element_text(size = rel(1.8)),
          axis.text.x = element_text(size = rel(1.8)),
          axis.text.y = element_text(size = rel(1.8)),
          strip.text.y = element_text(size = rel(1.8)))+
    labs(x = "Parent Smoking Status",
         y = "Student Smoking Status",
         fill = "Student Smoking Status",
         title = "Mosaic Plot of Smoking Data")

##Obtain test of independence
  smoking.test<- chisq.test(smoking.table)
  smoking.test

##Expected Values
  smoking.test$expected

##Contribution to test statistic
  (smoking.test$residuals)^2

