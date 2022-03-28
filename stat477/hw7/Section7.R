###############################################################
##load the necessary library
###############################################################
  library(irr)

  ##read in the data file
  teacher<- read.csv(file.choose(), header = T)

  ##label the categories for the two variables
  teacher$Judge.1<- factor(teacher$Judge.1, 
          levels = c("Authoritarian", "Democratic", "Permissive"))
  teacher$Judge.2<- factor(teacher$Judge.2, 
          levels = c("Authoritarian", "Democratic", "Permissive"))

  ##calculate Cohen's kappa estimate
  kappa2(teacher)

  ##calculate weighted Cohen's kappa estimate using the 
  ##squared weights from lecture
  kappa2(teacher, weight = c("squared"))
