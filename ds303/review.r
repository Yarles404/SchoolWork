library(ISLR2)
library(gbm)

# create test set from first 1000 rows of Caravan data
Caravan$Purchase <- as.numeric(Caravan$Purchase) - 1
test <- Caravan[1:1000, ]
train <- Caravan[-(1:1000), ]

# Boosting parameteres: number of trees, shrinkage, and max depth
# fit boosted tree
caravan_boost <- gbm(
    Purchase ~ .,
    data = train,
    n.trees = 1000,
    shrinkage = c(0.01))
summary(caravan_boost)

caravan_boost_prob <- predict(caravan_boost, test, n.trees = 1000, type = "response")
pred <- rep(0, nrow(test))
pred[caravan_boost_prob > 0.2] <- 1
table(pred, test$Purchase)
