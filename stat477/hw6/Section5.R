########################################################
##open the necessary libraries and run needed functions
########################################################
  library(vcdExtra) #for GKgamma

  ##Run the functions r.phi and phi.c
  r.phi<- function(table){
    chisqstat<- chisq.test(table, correct = F)$statistic
    n<- sum(table)
    sgn<- sign(table[1,1]*table[2,2]-table[1,2]*table[2,1])
    sgn*sqrt(chisqstat/n)
  }

  phi.c<- function(table){
    chisqstat<- chisq.test(table, 
                           correct = F)$statistic
    min.dim<- min(dim(table))
    numobs<- sum(table)
    sqrt((chisqstat/numobs)/(min.dim - 1))
  }

######################################################
##Correlation Coefficient for smoking data
######################################################
  
  ##Read the data file
  smokingc.data<- read.csv(file.choose(), header = T)
  smokingc.data$Student<- factor(smokingc.data$Student,
                  levels = c("Non-Smoker", "Smoker"))
  smokingc.data$Parent<- factor(smokingc.data$Parent,
                  levels = c("Neither", "One or Both"))
  smokingc.table<- table(smokingc.data$Parent, 
                         smokingc.data$Student)
  r.phi(smokingc.table)

######################################################
##Cramer V for smoking data
######################################################
  
  smoking.data<- read.csv(file.choose(), header = T)
  smoking.data$Student<- factor(smoking.data$Student,
                levels = c("Non-Smoker", "Smoker"))
  smoking.data$Parent<- factor(smoking.data$Parent,
                levels = c("Neither", "One", "Both"))
  smoking.table<- table(smoking.data$Parent, 
                        smoking.data$Student)
  phi.c(smoking.table)

######################################################
##Goodman-Kruskal gamma for job satisfaction data
######################################################
  
  jobs.data<- read.csv(file.choose(), header = T)
  jobs.data$Physically<- factor(jobs.data$Physically,
            levels = c("Seldom", "Sometimes", "Usually"))
  jobs.data$Psychologically<- factor(jobs.data$Psychologically,
            levels = c("Seldom", "Sometimes", "Usually"))
  jobs.table<- table(jobs.data$Physically, 
                     jobs.data$Psychologically)
  GKgamma(jobs.table)
