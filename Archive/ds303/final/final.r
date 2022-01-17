library(caret)

# 2a
library(MASS)

test_a <- heart[1:100, ]
train_a <- heart[-(1:100), ]

a_lda <- lda(chd ~ ., data = train_a)

a_prob <- predict(a_lda, test_a)

# using 0.5 as threshold
a_pred <- ifelse(a_prob$posterior[, 2] > 0.5, 1, 0)
a_pred

table(a_pred, test_a$chd)
mean(a_pred != test_a$chd)


# 2b
library(randomForest)
set.seed(1)

k <- 5
folds <- createFolds(train_a$chd, k = k, list = TRUE, returnTrain = FALSE)

cvError <- matrix(0, nrow = k, ncol = 4)
for (i in 1:k) {
    test_index <- folds[[i]]
    train <- train_a[-test_index, ]
    test <- train_a[test_index, ]

    rf2 <- randomForest(chd ~ ., data = train, ntree = 500, mtry = 2)
    rf3 <- randomForest(chd ~ ., data = train, ntree = 500, mtry = 3)
    rf4 <- randomForest(chd ~ ., data = train, ntree = 500, mtry = 4)
    rf5 <- randomForest(chd ~ ., data = train, ntree = 500, mtry = 5)

    pred2 <- predict(rf2, test, type = "class")
    pred3 <- predict(rf3, test, type = "class")
    pred4 <- predict(rf4, test, type = "class")
    pred5 <- predict(rf5, test, type = "class")

    pred2 <- ifelse(pred2 > 0.5, 1, 0)
    pred3 <- ifelse(pred3 > 0.5, 1, 0)
    pred4 <- ifelse(pred4 > 0.5, 1, 0)
    pred5 <- ifelse(pred5 > 0.5, 1, 0)

    cvError[i, 1] <- mean(test$chd != pred2)
    cvError[i, 2] <- mean(test$chd != pred3)
    cvError[i, 3] <- mean(test$chd != pred4)
    cvError[i, 4] <- mean(test$chd != pred5)
}
cvError

mean(cvError[, 1]) # mtry = 2 best with 0.298554
mean(cvError[, 2]) # mtry = 3 with error 0.3150685
mean(cvError[, 3]) # mtry = 4 with error 0.3094749
mean(cvError[, 4]) # mtry = 5 with error 0.3011035

# 2c
heart_rf <- randomForest(chd ~ ., data = train_a, ntree = 500, mtry = 2, keep.inbag = TRUE)
prob <- predict(heart_rf, test_a, type = "class")
pred <- ifelse(prob > 0.5, 1, 0)
# misclassification
mean(pred != test_a$chd)



# OOB TODO
allpred <- predict(heart_rf, newdata = train_a, predict.all = TRUE)$individual

yhat <- rep(NA, nrow(train_a))
for(i in 1:nrow(train_a)) {
    stuff <- allpred[i, heart_rf$inbag[i, ] == 0]
    yesses <- which(stuff == 1)
    proportion = length(yesses) / length(stuff)
    if (proportion > 0.5) {
        yhat[i] <- 1
    } else {
        yhat[i] <- 0
    }
}
mean(yhat != train_a$chd)

allpred <- predict(heart_rf, newdata = test_a, predict.all = TRUE)$individual
stuff2 <- allpred[2, heart_rf$inbag[2, ] == 0]
yesses2 <- which(stuff2 == 1)
proportion2 <- length(yesses2) / length(stuff2)
proportion2

# bootstrap standard error
B = 500
boot_pred2 <- rep(0, B)
n = nrow(heart)
for(i in 1:B) {
	boot_index <- sample(1:n, n, replace = TRUE)
    boot_data <- heart[boot_index, ]

    boot_rf <- randomForest(chd ~ ., data = boot_data, ntree = 25, mtry = 2, keep.inbag = TRUE)
    allpred <- predict(boot_rf, newdata = boot_data, predict.all = TRUE, type = "class")$individual
    stuff2 <- allpred[2, boot_rf$inbag[2, ] == 0]
    yesses2 <- which(stuff2 == 1)
    boot_pred2[i] <- length(yesses2) / length(stuff2)
}
sqrt( sum(boot_pred2 - mean(boot_pred2))^2 / (B - 1))

# 3b
data <- heart[, -10]
data <- data[, -5]
for (i in 1:ncol(data)) { # check variances
    print(var(data[, i]))
}

for (i in 1:ncol(data)) { # scale numeric columns
    data[, i] <- scale(data[, i])
}

# 3c
k2means <- kmeans(data, 2)
# table with cluster labels and actual labels
table(k2means$cluster - 1, heart$chd)
