##Load the libraries necessary for the data analysis
  library(ggplot2)
  library(plyr)
  
##Enter the function powerprop.test necessary to calculate the 
##power of a score test
  powerprop.test<- function(n, p0, pa, alternative, alpha){
    
    diff<- p0-pa
    sep0<- sqrt(p0*(1-p0)/n)
    sepa<- sqrt(pa*(1-pa)/n)
    probone<- 1 - alpha
    probtwo<- 1 - alpha/2
    
    switch(alternative,
           two.sided = 2*(1 - pnorm((abs(diff) + qnorm(probtwo)*sep0)/sepa)),
           greater = 1 - pnorm((diff + qnorm(probone)*sep0)/sepa),
           less = pnorm((diff - qnorm(probone)*sep0)/sepa))
  }
  
##Enter the function npowerprop.test necessary to calculate the sample size
##necessary to obtain a certain power.
  npowerprop.test<- function(p0, pa, alternative, alpha, power){
    
    diff<- p0-pa
    sep0<- sqrt(p0*(1-p0))
    sepa<- sqrt(pa*(1-pa))
    probone<- 1 - alpha
    probtwo<- 1 - alpha/2
    powerone<- power
    powertwo<- power/2
    
    switch(alternative,
           two.sided = ceiling((qnorm(powertwo)*sepa + qnorm(probtwo)*sep0)^2/diff^2),
           greater = ceiling((qnorm(powerone)*sepa + qnorm(probone)*sep0)^2/diff^2),
           less = ceiling((qnorm(powerone)*sepa + qnorm(probone)*sep0)^2/diff^2))
  }
  
#############################################################################
  
##Rejection region for binomial exact test

  sum(dbinom(13:20, 20, 0.5))
  sum(dbinom(14:20, 20, 0.5))
  sum(dbinom(15:20, 20, 0.5))
  sum(dbinom(16:20, 20, 0.5))
  sum(dbinom(17:20, 20, 0.5))
  
##Power for binomial exact test for ESP Hypothesis Test
  #When pa=0.6
  sum(dbinom(15:20, 20, 0.6))
  #When pa=0.75
  sum(dbinom(15:20, 20, 0.75))
  #When pa=0.9
  sum(dbinom(15:20, 20, 0.9)) 

##Graph power curve for binomial exact test for ESP Hypothesis Test
  #set pa to be values from 0.5 to 1 at 0.01 increments
  pa<- c(50:100)/100
  #initialize the vector powers with value 99
  powers<- rep(99, length(pa))
  #calculate the power for each of the values in the vector pa
  for (i in 1:length(pa)) powers[i]<- sum(dbinom(15:20, 20, pa[i]))

  powerplot<- as.data.frame(cbind(pa, powers))
  ggplot(powerplot, aes(x = pa, y = powers))+
    geom_line()+
    labs(x="True Population Proportion",
         y="Probability of Rejecting the Null Hypothesis",
         title="Power Curve for ESP Test")+
    theme_bw()+
    theme(axis.title.y = element_text(size = rel(1.4)),
          axis.title.x = element_text(size = rel(1.4)),
          axis.text.x = element_text(size = rel(1.6)),
          axis.text.y = element_text(size = rel(1.6)),
          plot.title = element_text(hjust=0.5, size = rel(2)))

##Power for Score Test
  
##Calculate the power for the score test for the Ingots Hypothesis Test
  #When pa=0.14
  powerprop.test(400, 0.2, 0.14, alternative = "less", 0.05)
  #When pa=0.18
  powerprop.test(400, 0.2, 0.18, alternative = "less", 0.05)
  
##Graph power curve for the score test for the Ingots Hypothesis Test
  #set pa to be values between 0 and 0.2 at 0.01 increments
  pa<- c(0:20)/100
  #initialize the vector powers with value 99
  powers<- rep(99, length(pa))
  #calculate the power for each of the 21 values in the vector pa
  for (i in 1:length(pa)) powers[i]<- powerprop.test(400, 0.2, pa[i], 
                                             alternative = "less", 0.05)
  
  #Plot the curve
  powerplot2<- as.data.frame(cbind(pa, powers))
  ggplot(powerplot2, aes(x = pa, y = powers))+
    geom_line()+
    ylim(0,1)+
    labs(x = "True Population Proportion",
         y = "Probability of Rejecting the Null Hypothesis",
         title = "Power Curve for Ingots Test")+
    theme_bw()+
    theme(axis.title.y = element_text(size = rel(1.4)),
          axis.title.x = element_text(size = rel(1.4)),
          axis.text.x = element_text(size = rel(1.6)),
          axis.text.y = element_text(size = rel(1.6)),
          plot.title = element_text(hjust=0.5, size = rel(2)))

##Sample Size Calculation for New Ingots Hypothesis Test
  npowerprop.test(0.2, 0.15, alternative = "less", 0.05, 0.95)
  
  npowerprop.test(0.2, 0.17, alternative = "less", 0.05, 0.7)
  