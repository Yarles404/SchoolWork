library(tidyverse)
library(ggplot2)
library(caret)
library(tree)
library(ISLR2)
library(randomForest)
library(gbm)

set.seed(1)
#2
#a process & split Carseats
Carseats$High <- factor(ifelse(Carseats$Sales <= 8, "No", "Yes"))
train_index <- sample(1:nrow(Carseats), floor(0.8 * nrow(Carseats)))
train <- Carseats[train_index, ]
test <- Carseats[-train_index, ]

#b train/plot tree
carseats_tree <- tree(High ~ . - Sales, split = c("gini"), data = train)
summary(carseats_tree)
plot(carseats_tree)
text(carseats_tree, pretty = 1)

prob <- predict(carseats_tree, newdata = test)
pred <- ifelse(prob[, 2] > 0.5, "Yes", "No")
table(pred, test$High)
mean(pred != test$High)

#c cross-validation
cv_carseats_tree <- cv.tree(carseats_tree, FUN = prune.misclass)
plot(cv_carseats_tree$size, cv_carseats_tree$dev, type = "b")
cv_carseats_tree$size[which.min(cv_carseats_tree$dev)]

pruned_carseats_tree <- prune.misclass(
    carseats_tree,
    best = cv_carseats_tree$size[which.min(cv_carseats_tree$dev)])

pred <- predict(pruned_carseats_tree, test, type = "class")
table(pred, test$High)
mean(pred != test$High)

#d bagging B = 500
carseats_bagged <- randomForest(
    High ~ . - Sales,
    data = train,
    mtry = ncol(train) - 2,
    importance = TRUE,
    ntree = 500)
pred <- predict(carseats_bagged, test, type = "class")
table(pred, test$High)
mean(pred != test$High)

importance(carseats_bagged)
varImpPlot(carseats_bagged)

# randomForest
low <- as.integer(sqrt(ncol(train) - 2))
high <- as.integer(ncol(train) - 2)
mCv <- matrix(NA, high - low + 1, 2)
for (m in low:high) {
    carseats_rf <- randomForest(
    High ~ . - Sales,
    data = train,
    mtry = m,
    importance = TRUE,
    ntree = 500)

    pred <- predict(carseats_rf, test, type = "class")
    mCv[m - low + 1, 2] <- mean(pred != test$High)
    mCv[m - low + 1, 1] <- m
}
mCv

# OOB B = 500, m = 6
carseats_rf2 <- randomForest(
    High ~ . - Sales,
    data = Carseats,
    mtry = 6,
    importance = TRUE,
    ntree = 500,
    keep.inbag = TRUE)

table(carseats_rf2$inbag[4, ] == 0)

allpred <- predict(carseats_rf2, newdata = Carseats, predict.all = TRUE, type = "class")$individual

yhat <- rep(NA, 400)
for(i in 1:400) {
  yhat[i] <- names(which.max(table(allpred[i, carseats_rf2$inbag[i, ] == 0])))
}

yhat[10]
table(allpred[10, carseats_rf2$inbag[10, ] == 0])

mean(yhat != as.character(Carseats$High))

carseats_rf2$err.rate[400]

# Cleaning
data <- Hitters[!is.na(Hitters$Salary), ]
data$Salary <- log(data$Salary)

# Data split
train <- data[1:200, ]
test <- data[201:nrow(data), ]

# Grid search
formula <- Salary~.

mygrid <- expand.grid(interaction.depth = c(3, 4, 5),
                        n.trees = c(100, 200, 300),
                        n.minobsinnode = 5,
                        shrinkage = c(0.001, 0.01, 0.1))

gbm_model <- train(formula,
                   data = train,
                   method = "gbm",
                   tuneGrid = mygrid,
                   trControl = trainControl(method = "cv", number = 10))

# Boosting hitters
boost_hitters <- gbm(
    formula,
    data = train,
    distribution = "gaussian",
    n.trees = 300,
    interaction.depth = 5,
    shrinkage = c(0.01)) #default for shrinkage is 0.001
summary(boost_hitters)
# provides a relative influence plot and outputs relative influence statistics

pred <- predict(
    boost_hitters,
    newdata = test,
    n.trees = 300)

mean((pred - test$Salary)^2)

# Bagging hitters
bagged_hitters <- randomForest(
    formula,
    data = train,
    mtry = ncol(train) - 1,
    importance = TRUE,
    ntree = 300)
pred <- predict(bagged_hitters, test)
mean((pred - test$Salary)^2)

# MNIST Random Forest
setwd("C:/Users/15155/Desktop/ds303/hw8/mnist")
train <- NULL
test <- NULL
load_mnist()

numberTrainIndex = sample(1:train$n, 3000)
numberTestIndex = sample(1:test$n, 100)

Xtrain <- train$x[numberTrainIndex, ]
Ytrain <- as.factor(train$y[numberTrainIndex])
Xtest <- test$x[numberTestIndex, ]
Ytest <- as.factor(test$y[numberTestIndex])
mnist_rf <- randomForest(
    x =  Xtrain,
    y = Ytrain,
    mtry = sqrt(ncol(Xtrain)),
    ntree = 100,
    importance = TRUE)

pred <- predict(mnist_rf, Xtest, type = "class")
# Confusion matrix
tab <- table(pred, Ytest)
tab

# Per digit misclassification
digitMis <- rep(NA, 10)
for (i in 1:10) {
    correct <- tab[i, i]
    sum <- 0
    for (j in 1:10) {
        sum <- sum + tab[i, j]
    }
    digitMis[i] <- 1 - correct / sum
}
digitMis

# Overall misclassification rate
mean(pred != Ytest)
