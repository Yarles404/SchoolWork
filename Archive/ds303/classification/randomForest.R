## Bagging and Random Forests

#install.packages("randomForest")
library(randomForest)
library(ISLR2)

## Even though I set seed, your results may differ from mine depending on your version of R and your version of randomForest package. 
set.seed(1)

## Bagging is a special case of a random forest with m = p. 
## Therefore, randomForest() function can implement both random forests and bagging. 

## Bagging

train = sample(1:nrow(Boston),nrow(Boston)/2)
dim(Boston)

bag.boston = randomForest(medv~.,data=Boston, subset = train, mtry = 12, importance = TRUE, ntree=500)

importance(bag.boston)
# two measures of variable importance are report
# (1) %IncMSE: increase in the MSE of predictions as a result of the listed predicted being permuted. This is based on out-of-bag estimation. 
## Does a larger value mean a predictor is more important? 

# (2): IncNodePurity: for classification trees, this is the total decrease in node impurity (or increase in node purity) from splitting on a predictor, averaged over all trees. Measured by Gini index. 
#For regression trees, the node impurity is measured by the training RSS. 

?importance

varImpPlot(bag.boston)

## test MSE
yhat.bag =  predict(bag.boston, newdata = Boston[-train, ]) 
boston.test = Boston[-train,"medv"]
plot(yhat.bag, boston.test)
abline(0, 1)

mean((yhat.bag - boston.test)^2)

#### Random Forest ####
## By default, randomForest() uses p/3 variables when building a random forest of regression trees, and sqrt(p) variables when building a random forest of classification trees. 

set.seed(1)
rf.boston = randomForest(medv ~., data = Boston, subset = train, mtry = 6, importance = TRUE, ntree = 500)

yhat.rf = predict(rf.boston, newdata = Boston[-train, ]) 
mean((yhat.rf - boston.test)^2)

importance(rf.boston)
varImpPlot(rf.boston)

############ OOB error estimation ################
rf.boston2 = randomForest(medv ~., data = Boston, mtry = 6, importance = TRUE, ntree = 500, keep.inbag=TRUE)
table(rf.boston2$inbag[1,]==0)

allpred = predict(rf.boston2,newdata=Boston,predict.all=TRUE)$individual

yhat = rep(NA,506)
for(i in 1:506){
  yhat[i] = mean(allpred[i,rf.boston2$inbag[i,]==0])

  # for classficaction
  # yhat[i] = probs[which trees obs i did not show up], do majority vote
}

mean((yhat-Boston$medv)^2)

rf.boston2$mse[500]