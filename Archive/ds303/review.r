library(ISLR2)
library(gbm)

# create test set from first 1000 rows of Caravan data
#Caravan$Purchase <- as.numeric(Caravan$Purchase) - 1
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

# logistic regression with glm
caravan_glm <- glm(
    Purchase ~ PPERSAUT,
    data = train,
    family = 'binomial')

median_pper <- median(Caravan$PPERSAUT)
median_test <- data.frame(PPERSAUT = c(median_pper))
prob <- predict(caravan_glm, median_test, type = "response")


pred <- ifelse(prob > 0.5, 1, 0)
table(pred, test$Purchase)


# bootstrap standard error of prediction on median PPERSAUT

B <- 200
probs <- rep(0, B)
for (i in 1:B) {
    #bootstrap_data <- sample(Caravan, nrow(Caravan), replace = TRUE) DOES NOT WORK
    bootstrap_index <- sample(1:nrow(Caravan), nrow(Caravan), replace = TRUE)
    bootstrap_data <- Caravan[bootstrap_index, ]
    bootstrap_glm <- glm(
        Purchase ~ PPERSAUT,
        data = bootstrap_data,
        family = 'binomial')
    bootstrap_prob <- predict(bootstrap_glm, median_test, type = "response")
    probs[i] <- bootstrap_prob
}

# standard error
sqrt(sum((probs-mean(probs))^2)/(B-1))
