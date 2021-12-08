CREATE DATABASE IF NOT EXISTS project1;
USE exam1;

CREATE TABLE IF NOT EXISTS Customer(
	cust_id int NOT NULL UNIQUE,
    name varchar(20),
    address varchar(50),
    amount double,
    PRIMARY KEY(cust_id)
);
    
# name of Group changed to ArtGroup because Group is a reserved MySQL keyword
CREATE TABLE IF NOT EXISTS ArtGroup(
	name varchar(10) NOT NULL UNIQUE,
    PRIMARY KEY(name)
);

CREATE TABLE IF NOT EXISTS Like_Group(
	groupName varchar(10),
    cust_id int,
    FOREIGN KEY(cust_id) REFERENCES Customer(cust_id),
    FOREIGN KEY(groupName) REFERENCES ArtGroup(name)
);

CREATE TABLE Artist(
	name varchar(20) NOT NULL,
    birthplace varchar(20),
    style varchar(10),
    age int,
    PRIMARY KEY(name)
);

CREATE TABLE IF NOT EXISTS Like_Artist(
	artistName varchar(20),
    cust_id int,
    FOREIGN KEY(cust_id) REFERENCES Customer(cust_id),
    FOREIGN KEY(artistName) REFERENCES Artist(name)
);

CREATE TABLE IF NOT EXISTS Artwork(
	title varchar(50) NOT NULL,
    price double,
    type varchar(10) NOT NULL,
    year int,
    creator varchar(20) NOT NULL,
    PRIMARY KEY(title),
    FOREIGN KEY(creator) REFERENCES Artist(name)
);

CREATE TABLE IF NOT EXISTS Paints(
	work varchar(50) NOT NULL UNIQUE,
	artist varchar(20) NOT NULL,
    FOREIGN KEY(artist) REFERENCES Artist(name),
    FOREIGN KEY(work) REFERENCES Artowrk(title)
);

CREATE TABLE IF NOT EXISTS Classify(
	classification varchar(10),
    work varchar(50) NOT NULL,
    FOREIGN KEY(classification) REFERENCES ArtGroup(name),
    FOREIGN KEY(work) REFERENCES Artwork(title)
);