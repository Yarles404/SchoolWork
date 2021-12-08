library(classdata)
data(cities)

all <- cities[cities$Year == 2016,]
desmoines <- all[all$City == "Des Moines",]
head(desmoines)

hist(all$Burglary / all$Population)
median(all$Burglary / all$Population, na.rm=TRUE)
print(desmoines$Burglary / desmoines$Population)

hist(all$Larceny.theft / all$Population)
median(all$Larceny.theft / all$Population, na.rm=TRUE)
print(desmoines$Larceny.theft / desmoines$Population)

hist(all$Robbery / all$Population)
median(all$Robbery / all$Population, na.rm=TRUE)
print(desmoines$Robbery / desmoines$Population)