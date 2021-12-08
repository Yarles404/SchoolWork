CREATE DATABASE IF NOT EXISTS project1;
USE project1;

CREATE TABLE IF NOT EXISTS students(
	snum int NOT NULL UNIQUE,
    ssn int,
    name varchar(10),
    gender varchar(1),
    dob datetime,
    c_addr varchar(20),
    c_phone varchar(10),
    p_addr varchar(20),
    p_phone varchar(10),
    PRIMARY KEY(ssn)
);

CREATE TABLE IF NOT EXISTS departments(
	code int,
    name varchar(50) NOT NULL UNIQUE,
    phone varchar(10),
    college varchar(20),
    PRIMARY KEY(code)
);

CREATE TABLE IF NOT EXISTS degrees(
	name varchar(50),
    level varchar(5),
    department_code int,
    PRIMARY KEY(name, level)
);

CREATE TABLE IF NOT EXISTS courses(
	number int,
    name varchar(50) NOT NULL UNIQUE,
    description varchar(50),
    credithours int,
    level varchar(20),
    department_code int,
    PRIMARY KEY(number),
    FOREIGN KEY(department_code) REFERENCES departments(code) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS register(
	snum int,
    course_number int,
    regtime varchar(20),
    grade int,
    PRIMARY KEY(snum, course_number),
    FOREIGN KEY(snum) REFERENCES students(snum),
    FOREIGN KEY(course_number) REFERENCES courses(number)
);

CREATE TABLE IF NOT EXISTS major(
	snum int,
    name varchar(50),
    level varchar(5),
    PRIMARY KEY(snum, name, level),
    FOREIGN KEY(snum) REFERENCES students(snum),
    FOREIGN KEY(name, level) REFERENCES degrees(name, level)
);

CREATE TABLE IF NOT EXISTS minor(
	snum int,
    name varchar(50),
    level varchar(5),
    PRIMARY KEY(snum, name, level),
    FOREIGN KEY(snum) REFERENCES students(snum),
    FOREIGN KEY(name, level) REFERENCES degrees(name, level)
);



