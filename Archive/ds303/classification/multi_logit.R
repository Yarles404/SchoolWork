library(foreign)
hs <- read.dta("https://stats.idre.ucla.edu/stat/data/hsbdemo.dta")
# Data on 200 students. The outcome variable is prog, program type (1=general, 2=academic, and 3=vocational). The predictor variables are ses, social economic status (1=low, 2=middle, and 3=high), math, mathematics score, and science, science score: both are continuous variables.

str(hs)
table(hs$prog)

library(nnet)

## choose your baseline level
hs$prog <- relevel(hs$prog, ref = "academic")

set.seed(1)
library(caret)
index <- createDataPartition(hs$prog, p = .70, list = FALSE)
trainset <- hs[index,]
testset <- hs[-index,]

multinom_model = multinom(prog ~ ., data = trainset)
summary(multinom_model)

## predict the values on the test set
# prob = predict(multinom_model, newdata = testset,type='probs')

m.pred = predict(multinom_model, newdata = testset, type="class")
tab = table(testset$prog, m.pred)

## misclassification rate
1 - sum(diag(tab))/sum(tab)
