##Load the libraries necessary for the data analysis
  library(ggplot2)
  library(plyr)
  
##################################################################
##Example - Binomial Exact Test
  
##Enter ESP Data and set levels
  espdata<- read.csv(file.choose(), header = T)
  espdata$Response<- factor(espdata$Response, 
                            levels = c("Correct", "Incorrect"))

##Summary Table
  esp.summary<- count(espdata, var = 'Response')
  esp.summary$rel<- round(prop.table(esp.summary[2]), 4)
  esp.summary
  sum(esp.summary[2])

##Bar Graph
  ggplot(espdata, aes(x=Response))+ 
    geom_bar(fill = "blue", colour = "black")+
    theme_bw()+
    theme(axis.title.y = element_text(size = rel(1.4)),
          axis.title.x = element_text(size = rel(1.4)),
          axis.text.x = element_text(size = rel(1.6)),
          axis.text.y = element_text(size = rel(1.6)),
          plot.title = element_text(hjust=0.5, size = rel(2)))+
    labs(x = "Response",
         y = "Number of Responses",
         title = "Bar Graph of ESP Test Responses")

##Binomial Exact Test for ESP Hypothesis Test
  binom.test(15, 20, 0.5, alternative = "greater")

##Binomial Exact Test for Dice Data
  binom.test(6, 60, 1/6, alternative = "two.sided")

##Example - Score Test
  
##Enter Ingots Data and set levels
  ingotsdata<- read.csv(file.choose(), header = T)

  ingotsdata$Status<- factor(ingotsdata$Status,
                             levels = c("Cracked", "Not Cracked"))

##Summary Table
  ingots.summary<- count(ingotsdata, var = 'Status')
  ingots.summary$rel<- round(prop.table(ingots.summary[2]), 4)
  ingots.summary
  sum(ingots.summary[2])

##Bar Graph
  ggplot(ingotsdata, aes(x=Status))+ 
    geom_bar(fill = "blue", colour = "black")+
    theme_bw()+
    theme(axis.title.y = element_text(size = rel(1.4)),
          axis.title.x = element_text(size = rel(1.4)),
          axis.text.x = element_text(size = rel(1.6)),
          axis.text.y = element_text(size = rel(1.6)),
          plot.title = element_text(hjust=0.5, size = rel(2)))+
    labs(x = "Status",
         y = "Number of Ingots",
         title = "Bar Graph of Ingots Status")

##Score Test for Ingots Hypothesis Test
  prop.test(64, 400, p = 0.2, alternative = "less", correct = F)

