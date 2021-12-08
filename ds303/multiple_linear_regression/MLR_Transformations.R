library(ISLR2)
head(Auto)

m1 = lm(mpg~horsepower,data=Auto)
summary(m1)
plot(m1)


## non-constant variance
library(MASS)

bc = boxcox(m1)
names(bc)

lambda = bc$x[which.max(bc$y)]
lambda

m2 = lm(mpg^lambda~horsepower,data=Auto)

# extract only the scale-location residual plot 
plot(m2, which=3)

# more of an art than a science
m3 = lm(log(mpg)~horsepower,data=Auto)
plot(m3, which=3)



