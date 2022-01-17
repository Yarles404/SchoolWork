#### Multiple Linear Regression ####

# read in data using table read.table()
# make sure you specify the pathway where you saved the data set
patient = read.table("/Users/brandonrouse/Desktop/DS 303/patient.txt",header=FALSE)

head(patient) #look at the first few rows of the data, make sure it's been loaded into R correctly

# give each column its variable name
names(patient) = c("satisf","age","severe","anxiety")

head(patient)
str(patient)

pairs(patient) #pairwise scatterplot

# What is the response vector? 
Y = patient$satisf

# number of observations in the data set
n = dim(patient)[1]

# construct our design matrix 
# first column needs to be column of 1's

X = cbind(rep(1,n),patient$age, patient$severe, patient$anxiety) 

# least square estimators 
XtX = t(X)%*%X
b = solve(XtX)%*%t(X)%*%Y

# What is my predicted value for Y? 

Yhat = X%*%b

# use the lm function 
model=lm(satisf~age+severe+anxiety,data=patient)
summary(model)
names(model)

#########################################################
## MSE? ################################################
## divide our data into a training set and a test set
set.seed(10)
train_index = sample(1:n,n/2,rep=FALSE)

train_patients = patient[train_index,]
test_patients = patient[-train_index,]

model_train = lm(satisf~age+severe+anxiety,data=train_patients)
MSE_train = mean((train_patients$satisf - model_train$fitted.values)^2)

predicted_values = predict(model_train,test_patients)
MSE_test = mean((test_patients$satisf - predicted_values)^2)

#########################################################
####### Properties of our linear regression model #######
#########################################################

## let's simulate data where we know the true population regression line

# simple linear regression
## suppose we have 1 single predictor (X1)
n = 100
beta_0 = 13
beta_1 = 7
X1 = seq(0,10, length.out=n)

# generate Y based on true population regression line

## True regression line will never change. It represents the population and is the 'truth'.
## We use our sample to estimate the least square line
error = rnorm(n,0,1)
Y = beta_0 + beta_1*X1 + error

# So if we were to take a different sample, we would get a different least square line
# and different estimates for beta0 and beta1
lm(Y~X1)

# we hope that over many samples, the average of all the estimates
# would exactly equal the truth. This is the idea of an unbiased estimator.

B = 5000
beta0hat = beta1hat = rep(NA,B)
for(i in 1:B){
  error = rnorm(n,0,1)
  Y = beta_0 + beta_1*X1 + error
  fit = lm(Y~X1)
  beta0hat[i] = fit$coefficients[[1]]
  beta1hat[i] = fit$coefficients[[2]]
}
hist(beta0hat)
hist(beta1hat)
mean(beta0hat)
mean(beta1hat)
