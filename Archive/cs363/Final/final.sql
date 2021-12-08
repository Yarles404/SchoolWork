CREATE DATABASE IF NOT EXISTS final;
USE final;

CREATE TABLE IF NOT EXISTS faculty(
	FID int,
    address varchar(80),
    phone int,
    name varchar(42),
    PRIMARY KEY(FID)
);

CREATE TABLE IF NOT EXISTS students(
	SID int,
    name varchar(42),
    degree varchar(20),
    advisor_ID int NOT NULL, # total-uni particpation "workaround"
    FOREIGN KEY(advisor_ID) REFERENCES faculty(FID),
	PRIMARY KEY(SID)
);

CREATE TABLE IF NOT EXISTS labOffice(
	ID int,
    seats int,
    address varchar(50),
    PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS advises(
	FID int,
    SID int UNIQUE, # gurantees only one advisor relationship per student
    PRIMARY KEY(SID),
    FOREIGN KEY(FID) REFERENCES faculty(FID),
    FOREIGN KEY(SID) REFERENCES students(SID)
);

CREATE TABLE IF NOT EXISTS works(
	office_id int,
    sid int,
    since datetime,
	FOREIGN KEY(office_id) REFERENCES labOffice(ID),
    FOREIGN KEY(sid) REFERENCES students(SID)
);

