###########
### knn ###
###########

library(ISLR2)

#install.packages('class')
library(class)

head(Caravan)
#The Insurance Company (TIC) Benchmark
#The data contains 5822 real customer records. 
# Each record consists of 86 variables, containing sociodemographic data (variables 1-43) 
# and product ownership (variables 44-86). 
# The sociodemographic data is derived from zip codes. 
# All customers living in areas with the same zip code have the same sociodemographic attributes. 
# Variable 86 (Purchase) indicates whether the customer purchased a caravan insurance policy. 
# Further information on the individual variables can be obtained at http://www.liacs.nl/~putten/library/cc2000/data.html

# KNN classifier predicts the class of a given test observation by identifying the observations that are
# nearest to it

# That said: does scale of the variables matter? 
var(Caravan[,1])
var(Caravan[,2])

standardized.X = scale(Caravan[,-86])
var(standardized.X[,1])
var(standardized.X[,2])


test = 1:1000
train.X = standardized.X[-test,]
test.X = standardized.X[test,]
train.Y = Caravan$Purchase[-test]
test.Y = Caravan$Purchase[test]

set.seed(1)
knn.pred = knn(train.X,test.X,train.Y,k=1)
head(knn.pred)

mean(test.Y!=knn.pred)
table(test.Y)
mean(test.Y=="Yes") # only 6% of customers purchased insurance. We could reduce our misclassification rate to 6%
# just by using a naive classifier. 

table(knn.pred,test.Y)

#Suppose that there is some non-trivial cost to trying to sell insurance to a given individual. For instance, perhaps a salesperson must visit each potential customer.

# Instead, the company would like to try to sell insurance only to customers who are likely to buy it.
# So the overall error rate is not of interest.
# How many did we correctly predict to buy insurance?
# In other words, among those that we predicted 'Yes', how many are actually a 'Yes'? 

###### How to choose K? ##########
###### Cross-validation ##########

# You can write your own loop. 
# 5-fold CV

library(caret)

Caravan2 = Caravan[-test,]
standardized.X2 = scale(Caravan2[,-86])

flds <- createFolds(Caravan2$Purchase, k = 5, list = TRUE, returnTrain = FALSE)
names(flds)


