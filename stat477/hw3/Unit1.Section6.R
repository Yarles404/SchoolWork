##Load the libraries needed for data analysis
  library(plyr) 
  library(ggplot2) 

############################################################################
##Read in the data file
  mmdata<- read.table(file.choose(), header = T, sep = ",")

##Set up the order for the categories of color for the M&Ms
  mmdata$Color<- factor(mmdata$Color, 
                        levels = c("Blue", "Orange", "Yellow", 
                                 "Red", "Green", "Brown"))

##Summary Table for M&M color
mm.summary<- count(mmdata, var = 'Color')
mm.summary$rel<- round(prop.table(mm.summary[2]), 4)
mm.summary
sum(mm.summary[2])

##Bar Graph for M&M color
  ggplot(mmdata, aes(x=Color))+ 
    geom_bar(fill = "blue", colour = "black")+
    labs(x = "Color",
         y = "Number of M&Ms",
         title = "Bar Graph of M&M Colors")+
    theme_bw()+
    theme(axis.title.y = element_text(size = rel(1.4)),
          axis.title.x = element_text(size = rel(1.4)),
          axis.text.x = element_text(size = rel(1.6)),
          axis.text.y = element_text(size = rel(1.6)),
          plot.title = element_text(hjust=0.5, size = rel(2)))

##Conduct goodness of fit test
  #Set the probability values from the model in the same 
  #color order as the data
  modelp<- c(0.24, 0.2, 0.14, 0.13, 0.16, 0.13)
  
  #Run goodness of fit test on M&M color data
  mm.goodtest<- chisq.test(mm.summary[2], p = modelp)
  
  #Look at test output
  mm.goodtest
  
  #Get expected values for each category
  mm.goodtest$expected
  
  #Contribution from each category to test statistic
  (mm.goodtest$residuals)^2  
  