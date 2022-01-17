library(dendextend)
library(ggplot2)
library(dplyr)
library(ISLR2)

# 2
x1 <- c(1, 1, 0, 5, 6, 4)
x2 <- c(4, 3, 4, 1, 2, 0)
data = data.frame(x1 = x1, x2 = x2)

# 2a
ggplot(data, aes(x1, x2)) +
    geom_point() +
    theme_minimal()

set.seed(123)
labels <- sample(2, nrow(data), replace = TRUE)
labels[labels == 1] <- "A"
labels[labels == 2] <- "B"
# 2b
labels
data$labels <- labels


# calculate the centroids by labels
A_centroid <- c(mean(data[data$labels == "A", 1]), mean(data[data$labels == "A", 2]))
B_centroid <- c(mean(data[data$labels == "B", 1]), mean(data[data$labels == "B", 2]))
# 2c
A_centroid
B_centroid

# assign data to closest centroids
for (i in 1:nrow(data)) {
    if (euclidean(data[i, 1:2], A_centroid) > euclidean(data[i, 1:2], B_centroid)) {
        data$labels[i] <- "B"
    }
    if (euclidean(data[i, 1:2], B_centroid) > euclidean(data[i, 1:2], A_centroid)) {
        data$labels[i] <- "A"
    }
}
# 2d
data$labels

ggplot(data, aes(x1, x2)) +
    geom_point(aes(color = factor(labels))) +
    theme_minimal() +
    geom_point(aes(x = A_centroid[1], y = A_centroid[2]), color = "red") +
    geom_point(aes(x = B_centroid[1], y = B_centroid[2]), , color = "blue")

euclidean <- function(a, b) sqrt(sum((a - b)^2))

# 2e
changed = TRUE
while (changed) {
    changed = FALSE
    for (i in 1:nrow(data)) {
        if (data$labels[i] == "A") {
            if (euclidean(data[i, 1:2], A_centroid) > euclidean(data[i, 1:2], B_centroid)) {
                data$labels[i] <- "B"
                changed = TRUE
            }
        } else {
            if (euclidean(data[i, 1:2], B_centroid) > euclidean(data[i, 1:2], A_centroid)) {
                data$labels[i] <- "A"
                changed = TRUE
            }
        }
    }

    A_centroid <- c(mean(data[data$labels == "A", 1]), mean(data[data$labels == "A", 2]))
    B_centroid <- c(mean(data[data$labels == "B", 1]), mean(data[data$labels == "B", 2]))
}

A_centroid
B_centroid

# 2f
ggplot(data, aes(x1, x2)) +
    geom_point(aes(color = factor(labels))) +
    theme_minimal() +
    geom_point(aes(x = A_centroid[1], y = A_centroid[2]), color = "red") +
    geom_point(aes(x = B_centroid[1], y = B_centroid[2]), , color = "blue")


# 3
# cluster USArrests with complete linkage
USA_dist <- dist(USArrests)
# 3a
USA_clust <- hclust(USA_dist, method = "complete")
temp <- cutree(USA_clust, 3)
usa_3cut <- data.frame(cluster = unname(temp), state = names(temp))
# 3b
usa_3cut %>%
    group_by(cluster) %>%
    summarise(states = paste(sort(state), collapse = ", ")) -> out

USA_scaled <- scale(USArrests)
USA_scaled_dist <- dist(USA_scaled)
USA_scaled_clust <- hclust(USA_dist, method = "complete")
temp <- cutree(USA_clust, 3)
# 3c
usa_scaled_3cut <- data.frame(cluster = unname(temp), state = names(temp))
usa_scaled_3cut %>% group_by(cluster) %>% summarise(states = paste(sort(state), collapse = ", "))

# 3d
var(USArrests$Murder)
var(USArrests$Assault)
var(USArrests$UrbanPop)
var(USArrests$Rape)

nci_labs <- NCI60$labs
nci_data <- NCI60$data
# don't scale because the variables don't seem to have varied variances
# 4a
nci_2means <- kmeans(nci_data, 2)
nci_4means <- kmeans(nci_data, 4)

# within cluster sum of squares
# 4b
sum(nci_2means$withinss)
sum(nci_4means$withinss)

# hierarchical clustering with complete linkage
nci_dist <- dist(nci_data)
nci_clust <- hclust(nci_dist, method = "complete")
nci_2cut <- cutree(nci_clust, 2)

# confusion matrix hiearchical clustering vs kmeans
# 4c
table(nci_2means$cluster, nci_2cut)

# for 4 means
# 4d
nci_4cut <- cutree(nci_clust, 4)
table(nci_4means$cluster, nci_4cut)

# 4e
nci_4means$cluster
nci_4cut
