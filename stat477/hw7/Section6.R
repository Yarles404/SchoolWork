##########################################################
##Load necessary libraries
##########################################################
  library(irr)
  
##Function to calculate confidence interval for difference
##in marginal probabilities
  mcnemar.ci<- function(table, conf.level = 0.95){
    alpha<- 1 - conf.level
    z<- qnorm(1 - alpha/2)
  
    n<- sum(table)
    y1.<- margin.table(table, 1)[1]
    y.1<- margin.table(table, 2)[1]
    diff12<- table[1,2]-table[2,1]
    add12<- table[1,2]+table[2,1]
    se<- sqrt(add12 - diff12^2/n)/n
    lowerci<- y1./n - y.1/n - z*se
    upperci<- y1./n - y.1/n + z*se
    cat("Confidence Interval = ", lowerci, upperci, "\n")
  }
##########################################################
##McNemar's Test of Marginal Homogeneity
##########################################################
  
  ##Read in the histogram question data
  hist.data<- read.csv(file.choose(), header = T)
  ##Calculate the Contingency Table
  hist.table<- table(hist.data$Question1, 
                     hist.data$Question2)
  ##Obtain the hypothesis test
  mcnemar.test(hist.table, correct = F)
  
  ##Obtain confidence interval for difference
  mcnemar.ci(hist.table, conf.level = 0.95)

##########################################################
##Extension of McNemar's Test of Marginal Homogeneity 
##to larger tables
##########################################################  
  
  ##Read in the histogram question data
  baby.data<- read.csv(file.choose(), header = T)
  ##Calculate the Contingency Table
  baby.table<- table(baby.data$Question1, 
                     baby.data$Question2)
  ##Obtain the hypothesis test
  stuart.maxwell.mh(baby.table)
  