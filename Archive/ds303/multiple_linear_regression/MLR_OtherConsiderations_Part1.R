##############################
### Qualitative predictors ###
##############################

library(ISLR2)
head(Credit)
str(Credit)

summary(lm(Balance~Limit+Region,data=Credit))

#It is estimated that those in the South, on average,  will have $1.38 more average debt than those in the East, and that those in the West, on average, will have $2.83 more debt than those in the East, holding all other predictors constant. 

# What are the fitted regression lines for each category of region? 

Yhat = -30.78 + 0.0178*Limit (East)
Yhat = -30.78 + 0.0178*Limit + 1.381 (South)
Yhat = -30.78 + 0.0178*Limit + 2.835 (west)

summary(lm(Balance~Limit,data=Credit))

## Can change baseline:
Credit$Region <- relevel(Credit$Region, ref = "South")

summary(lm(Balance~Limit+Region,data=Credit))

lm.fit <- lm(Balance~Limit+Region+Student+Married,data=Credit)
lm.fit2 <- lm(Balance~Limit,data=Credit)

# partial F-test 
anova(lm.fit2, lm.fit)


##############################
##### Multicollinearity ######
##############################
library(MASS)
set.seed(6)
n=100;

Covmat = matrix(0.88,3,3);
diag(Covmat)=1;
betas=c(1,2,3);
X=matrix(0,n,3);
y=numeric(n);
for(i in 1:n){
  X[i,]<- mvrnorm(1,rep(0,3),Covmat);
  y[i]<-sum(X[i,]*betas)+8*rnorm(1);
}


X1 = X[,1]
X2 = X[,2]
X3 = X[,3]

lm1 = lm(y~X1+X2+X3)

summary(lm1)

# small changes to the first 5 observations of X1
# everything else stays the same
set.seed(12)
X1new = X1
X1new[1:5] = X1[1:5] + rnorm(5)
summary(lm(y~X1new+X2+X3))

######## More than one way to diagnose multicollinearity ########

##### simulation illustrating perfect correlation 

x1 = rnorm(100, mean=70, sd=15)
x2 = rnorm(100, mean=70, sd=15)

# Add in a linear combination of X1 and X2
x3 = (x1+x2)/2

# X4 is somewhat correlated with X1 but not relevant to Y
x4 = x1+runif(100,min=-100,max=100)

# Y is a linear combination of X1 and X2 plus noise
y = 0.7*x1 + 0.3*x2 + rnorm(100, 0, sqrt(15))

summary(lm(y~x1+x2+x3+x4))
summary(lm(y~x1+x2+x4))

X = cbind(rep(1,100),x1,x2,x3,x4)
# what do we expect the rank to be if X is full rank? 

qr(X)$rank
eigenvaluesX = eigen(t(X)%*%X)$values
eigenvaluesX

#install.packages("car")
library(car)

vif(lm(y~x1+x2+x3+x4))

#######################
#### data example #####
#######################

fit1 = lm(Balance~Age + Limit,data=Credit)
summary(fit1)

fit2 = lm(Balance~Rating+Limit,data=Credit)
summary(fit2)

plot(Rating~Limit,Credit)

## check rank of your design matrix X
n = dim(Credit)[1]
X = cbind(rep(1,n),Credit$Rating,Credit$Limit)
qr(X)$rank

## check eigenvalues of X^TX
eigenvaluesX = eigen(t(X)%*%X)$values
eigenvaluesX

vif(fit2)


