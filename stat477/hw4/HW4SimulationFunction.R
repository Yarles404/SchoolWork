##Homework Assignment 4: additional function for the simulation question

library(plyr) #count() function
library(ggplot2) #ggplot() function

##Function to run the simulation for Problem 2. Run this function
##to make it available to use later.
coverage.ci<- function(n, p, type, conf.level){
  sample<- rbinom(100000, n, p)
  
  phat<- sample/n
  alpha<- 1 - conf.level
  z<- qnorm(1 - alpha/2)
  newy<- sample + z^2/2
  newn<- n + z^2
  newphat<- newy/newn
  
  lowerci<- rep(0, 100000)
  upperci<- rep(0, 100000)
  nocov<- rep(0, 100000)
  
  for (i in 1:100000){
    if (type=="normal") {
      lowerci[i]<- phat[i] - z*sqrt(phat[i]*(1-phat[i])/n)
      upperci[i]<- phat[i] + z*sqrt(phat[i]*(1-phat[i])/n)
    }
    else if (type=="score") {
      lowerci[i]<- (phat[i] + (1/(2*n))*z^2 - z*sqrt(phat[i]*(1-phat[i])/n + z^2/(4*n^2)))/(1 + (1/n)*z^2)
      upperci[i]<- (phat[i] + (1/(2*n))*z^2 + z*sqrt(phat[i]*(1-phat[i])/n + z^2/(4*n^2)))/(1 + (1/n)*z^2)
    }
    else {
      lowerci[i]<- newphat[i] - z*sqrt(newphat[i]*(1-newphat[i])/newn)
      upperci[i]<- newphat[i] + z*sqrt(newphat[i]*(1-newphat[i])/newn)
    }
    nocov[i]<- ifelse(lowerci[i] > p, 1, 0) + ifelse(upperci[i] < p, 1, 0)
  }
  covrate<- 1 - sum(nocov)/100000
  cat(covrate*100, "\n")
  covrate*100
}
