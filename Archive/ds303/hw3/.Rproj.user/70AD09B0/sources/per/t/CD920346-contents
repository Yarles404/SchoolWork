##################################
##### Polynomial Regression ######
##################################
# scatterplot
library(ISLR2)
head(Auto)
plot(Auto$mpg, Auto$horsepower)

plot(m1)

m_new = lm(mpg~horsepower + I(horsepower^2), data=Auto)
summary(m_new)
plot(m_new)

#Is the quadratic term a significant improvement over the original model? 
anova(m1,m_new)

## Use the poly function for higher order terms

m_new2 = lm(mpg~poly(horsepower,2),data=Auto) 

AutoNew = na.omit(Auto)
m_new2 = lm(mpg~poly(horsepower,2),data=AutoNew) 
summary(m_new2)

#poly() function orthogonalizes the predictors (reduces correlation between predictors)
# orthogonalizing the predictors will not affect your predictions but it does affect
# your ability to directly interpret the coefficients.

###### Wage data set #########
head(Wage)
fit = lm(wage ~ poly(age, 4), data = Wage)
summary(fit)

fit2 = lm(wage ~ poly(age, 4, raw=TRUE), data = Wage)
summary(fit2)

# create a grid of age values for which we want to obtain predictions from
agelims = range(Wage$age)
age.grid = seq(from = agelims[1], to = agelims[2])
preds = predict(fit, newdata = list(age = age.grid), interval='confidence', se = TRUE)
# note that se.fit gives the standard error associated with the estimated mean value of the response variable 
# it is different from the standard error associated with prediction

se.bands = cbind(preds$fit[,2], preds$fit[,3])

plot(Wage$age, Wage$wage, xlim = agelims, cex = .5, col = "darkgrey") 
title("Degree-4 Polynomial", outer = T)
lines(age.grid, preds$fit[,1], lwd = 2, col = "blue")
matlines(age.grid, se.bands, lwd = 1, col = "blue", lty = 3)

## how to decide on polynomial degree
## Option 1: use hypothesis tests

fit.1 = lm(wage ~ age, data = Wage)
fit.2 = lm(wage ~ poly(age,2), data = Wage)
fit.3 = lm(wage ~ poly(age,3), data = Wage)
fit.4 = lm(wage ~ poly(age,4), data = Wage)
fit.5 = lm(wage ~ poly(age,5), data = Wage)

anova(fit.1, fit.2, fit.3, fit.4, fit.5)
# Ether a cubic or a quadratic polynomial appear to provide a reasonable fit to the data, but lower- or higher-order models are not justified.

### Option 2: compare training and test MSE 
set.seed(100)
n = dim(Wage)[1]
train_index = sample(1:n,n/2,rep=FALSE)

train_wage = Wage[train_index,]
test_wage = Wage[-train_index,]

fit.1 = lm(wage ~ age, data = train_wage)
fit.2 = lm(wage ~ poly(age,2), data = train_wage)
fit.3 = lm(wage ~ poly(age,3), data = train_wage)
fit.4 = lm(wage ~ poly(age,4), data = train_wage)
fit.5 = lm(wage ~ poly(age,5), data = train_wage)

MSE_train1 = mean((train_wage$wage - fit.1$fitted.values)^2) 
MSE_train2 = mean((train_wage$wage - fit.2$fitted.values)^2) 
MSE_train3 = mean((train_wage$wage - fit.3$fitted.values)^2) 
MSE_train4 = mean((train_wage$wage - fit.4$fitted.values)^2) 
MSE_train5 = mean((train_wage$wage - fit.5$fitted.values)^2) 

plot(c(1:5),c(MSE_train1,MSE_train2,MSE_train3,MSE_train4,MSE_train5))

## test MSE

####################
##### Splines ######
####################
library(splines)
fit <- lm(wage ~ bs(age, knots = c(25, 40, 60)), data = Wage) #cubic splines are default
# pre-specified knots at ages 25, 40, and 60
summary(fit)

pred <- predict(fit, newdata = list(age = age.grid), se = T)
plot(Wage$age, Wage$wage, col = "gray")

lines(age.grid, pred$fit, lwd = 2)
lines(age.grid, pred$fit + qt(0.975,2993) * pred$se, lty = "dashed")
lines(age.grid, pred$fit - qt(0.975,2993) * pred$se, lty = "dashed")
###
dim(bs(Wage$age, knots = c(25, 40, 60))) 

## Cubic spline has 4+K degrees of freedom 
# df option will produce a spline with knots at uniform quantiles of the data.

dim(bs(Wage$age, df = 6)) # subtract 1 for the intercept 
attr(bs(Wage$age, df = 6), "knots")
###

lm(wage ~ bs(age, df = 6, degree = 4), data = Wage) # you can change the degree of the piecewise polynomial. 

## Natural splines

## Natural cubic spline with K knots has K - 1 degrees of freedom 
fit2 <- lm(wage ~ ns(age, df = 4), data = Wage)

attr(ns(Wage$age, df = 4), "knots")

pred2 <- predict(fit2, newdata = list(age = age.grid),
                 se = T)
lines(age.grid, pred2$fit, col = "red", lwd = 2)

# As with the bs() function, we could instead specify the knots directly using the knots option.


## How to choose number of knots (or degrees of freedom) ? 
## You can again compare the test MSE for different dfs and see which one gives you the smallest test MSE. 

################
##### GAM ######
################


## Up until now we've discussed non-linear models with a single predictor. 
## The beauty of GAMs is that we can use these methods as building blocks for fitting an additive model
# GAMs (generalized additive models) allow us to fit a non-linear functions to each Xj, so that we can model non-linear relationships that standard linear regression would have missed.

gam1 <- lm(wage ~ ns(year, df = 4) + ns(age, df = 5) + education, data = Wage)
summary(gam1)


