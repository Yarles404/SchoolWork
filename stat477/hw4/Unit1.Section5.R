##Enter the function prop.ci used to calculate the 
##confidence interval for a population proportion using either 
##the Wilson score method or normal approximation method
  prop.ci<- function(y, n, type, conf.level){
    phat<- y/n
    alpha<- 1 - conf.level
    z<- qnorm(1 - alpha/2)
    newy<- y + z^2/2
    newn<- n + z^2
    newphat<- newy/newn
  
    if (type=="normal") {
      lowerci<- phat - z*sqrt(phat*(1-phat)/n)
      upperci<- phat + z*sqrt(phat*(1-phat)/n)
    }
  
    else if (type=="score") {
      lowerci<- (phat + (1/(2*n))*z^2 - z*sqrt(phat*(1-phat)/n + z^2/(4*n^2)))/(1 + (1/n)*z^2)
      upperci<- (phat + (1/(2*n))*z^2 + z*sqrt(phat*(1-phat)/n + z^2/(4*n^2)))/(1 + (1/n)*z^2)
    }
  
    else {
      lowerci<- newphat - z*sqrt(newphat*(1-newphat)/newn)
      upperci<- newphat + z*sqrt(newphat*(1-newphat)/newn)
    }
    cat(lowerci, upperci, "\n")
  }

##Enter the function nprop.ci used to calculate the sample size
##necessary for a confidence interval with a given margin of 
##error and confidence level
  nprop.ci<- function(p, m, conf.level) {
    alpha<- 1 - conf.level
    z<- qnorm(1 - alpha/2)
    ceiling((z/m)^2*p*(1-p))
  }

#############################################################################################################

#confidence interval for ingots data
  prop.ci(64,400,type = "normal", 0.95)
  prop.ci(64,400,type = "score", 0.95)

#sample size calculations
  #using worst case scenario
  nprop.ci(0.5, 0.02, 0.95)
  #using a reasonable guess for population proportion
  nprop.ci(0.2, 0.02, 0.95)
