#### Multiple Linear Regression ####

# read in data using table read.table()
# make sure you specify the pathway where you saved the data set
patient = read.table("/Users/lchu/Documents/Teaching/Academic2021_2022/Fall2021/DS303_F21/Lectures/2-MLR/patient.txt",header=FALSE)

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
train_index = sample(1:n,n/2,rep=FALSE)

train_patients = patient[train_index,]
test_patients = patient[-train_index,]

model_train = lm(satisf~age+severe+anxiety,data=train_patients)

predicted_values = predict(model_train,test_patients)
MSE_test = mean((test_patients$satisf - predicted_values)^2)

#########################################################
####### Properties of our linear regression model #######
#########################################################

## let's simulate data where we know the true population regresssion line

## suppose we have 1 single predictor (X1)
n = 100
beta_0 = ?
beta_1 = ?
X1 = seq(0,10, length.out=n)

# generate Y based on true population regression line

