##Load library needed for data analysis
  library(ggplot2)

########################################################################
##P(Y = 3) - binomial with n = 5 and p = 0.6
  dbinom(3, 5, 0.6)

##P(Y >=2) - binomial with n = 5 and p = 0.6 greater than or equal to 2
  sum(dbinom(2:5, 5, 0.6))

##P(Y <=3) - binomial with n = 5 and p = 0.6 less than or equal to 3
  sum(dbinom(0:3, 5, 0.6))

##Graph probability distribution of binomial with n = 5 and p = 0.6
  #set the possible values for Y
  y<- c(0:5)
  #calculate the probabilities for each value of Y
  proby<- dbinom(0:5, 5, 0.6)
  #Put the values of y and proby into a data frame
  Bars<- as.data.frame(cbind(y, proby))
  
  #Bar Graph of y and proby values
  ggplot(Bars, aes(x = y, y = proby))+ 
    geom_bar(stat="identity", width = 1, fill = "blue", 
             colour = "black")+
    labs(x = "Number of Successful Nesting Pairs",
         y = "Probability")+
    theme_bw()+
    theme(axis.title.y = element_text(size = rel(1.4)),
          axis.title.x = element_text(size = rel(1.4)),
          axis.text.x = element_text(size = rel(1.6)),
          axis.text.y = element_text(size = rel(1.6)),
          plot.title = element_text(hjust=0.5, size = rel(2)))
    
##Graph general probability distribution for binomial
##Set the values of n and p; rest of code uses these values
  n<- 60
  p<- 1/6
  
  #set the possible values of Y
  y<- c(0:n)
  #calculate the probabilities for each value of Y
  proby<- dbinom(0:n, n, p)
  
  #Put the values of y and proby into a data frame 
  Bars<- as.data.frame(cbind(y, proby))
  
  #Bar Graph of y and proby values
  ggplot(Bars, aes(x = y, y = proby))+ 
    geom_bar(stat="identity", width = 1, fill = "blue", 
             colour = "black")+
    theme_bw()+
    theme(axis.title.y = element_text(size = rel(1.4)),
          axis.title.x = element_text(size = rel(1.4)),
          axis.text.x = element_text(size = rel(1.6)),
          axis.text.y = element_text(size = rel(1.6)),
          plot.title = element_text(hjust=0.5, size = rel(2)))+
    labs(x = "y",
         y = "Probability",
         title = "Binomial Distribution")

##Calculate probability for multinomial distribution for M&Ms
  prob<- c(0.24, 0.2, 0.14, 0.13, 0.16, 0.13)
  y<- c(2,2,1,1,1,1)
  dmultinom(y, sum(y), prob)

