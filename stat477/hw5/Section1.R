##Open the necessary libraries
library(ggplot2) #for ggplot()
library(ggmosaic) #for geom_mosaic()
  
###############################################################################################################
##Read in the data file and set the factor levels of the variable "Class" to show Survival first.
titanic.full<- read.csv(file.choose(), header = T)

Titanic1<-data.frame(titanic.full)
#Reorder the categories for "survived" so we have Yes and No (in this order, rather than alphabetical)
Titanic1$Survived <- factor(Titanic1$Survived, levels=c("Survived", "Died"))


##Obtain contingency table
  Titanic.table<- table(Titanic1$Survived, Titanic1$Class)
  Titanic.table
  
##Obtain marginal distribution for the row variable = Survived
  margin.table(Titanic.table, 1)
  round(margin.table(Titanic.table, 1)/sum(Titanic.table), 4)

##Obtain marginal distribution for the column variable = Class
  margin.table(Titanic.table, 2)
  round(margin.table(Titanic.table, 2)/sum(Titanic.table), 4)

##Obtain conditional distribution for column variable = 
##Class given row variable = Survived
  round(prop.table(Titanic.table, 1), 4)

##Obtain conditional distribution for row variable = 
##Survived given column variable = Class
  round(prop.table(Titanic.table, 2), 4)

##Segmented bar graph of Class by Survival  
  ggplot(Titanic1, aes(x = Survived, fill = Class))+
    geom_bar(position = "fill")+
    theme_bw()+
    theme(axis.title.y = element_text(size = rel(1.4)),
          axis.title.x = element_text(size = rel(1.4)),
          axis.text.x = element_text(size = rel(1.6)),
          axis.text.y = element_text(size = rel(1.6)),
          plot.title = element_text(hjust=0.5, size = rel(2)))+
    labs(y = "Proportion",
         title = "Segmented Bar Graph of Class by Survival")

##Segmented bar graph of Survival by Class
  ggplot(Titanic1, aes(x = Class, fill = Survived))+
    geom_bar(position = "fill")+
    scale_fill_manual(values=c("green", "red"))+
    theme_bw()+
    theme(axis.title.y = element_text(size = rel(1.4)),
          axis.title.x = element_text(size = rel(1.4)),
          axis.text.x = element_text(size = rel(1.6)),
          axis.text.y = element_text(size = rel(1.6)),
          plot.title = element_text(hjust=0.5, size = rel(2)))+
    labs(y = "Proportion",
         x = "Class",
         title = "Segmented Bar Graph of Survival by Class")
  
##Mosaic plot of Class by Survival
  ggplot(data = Titanic1)+
    geom_mosaic(aes(x = product(Class, Survived), fill = Class), 
                na.rm = TRUE, divider =  mosaic("h"))+
    theme_bw()+
    theme(plot.title = element_text(hjust=0.5, size = rel(2)),
          axis.title.y = element_text(size = rel(1.8)),
          axis.title.x = element_text(size = rel(1.8)),
          axis.text.x = element_text(size = rel(1.8)),
          axis.text.y = element_text(size = rel(1.8)),
          strip.text.y = element_text(size = rel(1.8)))+
    labs(x = "Survival", 
         y = "Class", 
         fill = "Class", 
         title = "Mosaic Plot of Class by Survival")

##Mosaic plot of Survival by Class
  ggplot(data = Titanic1)+
    geom_mosaic(aes(x = product(Survived, Class), fill = Survived), 
                na.rm = TRUE, divider =  mosaic("h"))+
    scale_fill_manual(values=c("red", "green"))+
    theme_bw()+
    theme(plot.title = element_text(hjust=0.5, size = rel(2)),
          axis.title.y = element_text(size = rel(1.8)),
          axis.title.x = element_text(size = rel(1.8)),
          axis.text.x = element_text(size = rel(1.8)),
          axis.text.y = element_text(size = rel(1.8)),
          strip.text.y = element_text(size = rel(1.8)))+
    labs(x = "Class", 
         y = "Survival", 
         fill = "Survived", 
         title = "Mosaic Plot of Survival by Class")
