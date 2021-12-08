library(classdata)
library(ggplot2)

str(fbi)

iowa <- fbi[fbi$State == "Iowa" & fbi$Year >= 2008,]


ggplot(data=iowa, aes(Year, Count)) + geom_line() + geom_point() + facet_wrap(~Type, scales="free_y")