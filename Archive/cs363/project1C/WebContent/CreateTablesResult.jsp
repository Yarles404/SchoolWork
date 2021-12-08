<%@ page import="java.io.*,java.util.*, java.sql.*" %>
<%@ page import="javax.servlet.http.*, javax.servlet.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Tables Result</title>
</head>
<body>
<%
// original script code
String script = "CREATE DATABASE IF NOT EXISTS project1;\n" +
        "USE project1;\n" +
        "\n" +
        "CREATE TABLE IF NOT EXISTS students(\n" +
        "\tsnum int NOT NULL UNIQUE,\n" +
        "    ssn int,\n" +
        "    name varchar(10),\n" +
        "    gender varchar(1),\n" +
        "    dob datetime,\n" +
        "    c_addr varchar(20),\n" +
        "    c_phone varchar(10),\n" +
        "    p_addr varchar(20),\n" +
        "    p_phone varchar(10),\n" +
        "    PRIMARY KEY(ssn)\n" +
        ");\n" +
        "\n" +
        "CREATE TABLE IF NOT EXISTS departments(\n" +
        "\tcode int,\n" +
        "    name varchar(50) NOT NULL UNIQUE,\n" +
        "    phone varchar(10),\n" +
        "    college varchar(20),\n" +
        "    PRIMARY KEY(code)\n" +
        ");\n" +
        "\n" +
        "CREATE TABLE IF NOT EXISTS degrees(\n" +
        "\tname varchar(50),\n" +
        "    level varchar(5),\n" +
        "    department_code int,\n" +
        "    PRIMARY KEY(name, level)\n" +
        ");\n" +
        "\n" +
        "CREATE TABLE IF NOT EXISTS courses(\n" +
        "\tnumber int,\n" +
        "    name varchar(50) NOT NULL UNIQUE,\n" +
        "    description varchar(50),\n" +
        "    credithours int,\n" +
        "    level varchar(20),\n" +
        "    department_code int,\n" +
        "    PRIMARY KEY(number),\n" +
        "    FOREIGN KEY(department_code) REFERENCES departments(code) ON DELETE SET NULL\n" +
        ");\n" +
        "\n" +
        "CREATE TABLE IF NOT EXISTS register(\n" +
        "\tsnum int,\n" +
        "    course_number int,\n" +
        "    regtime varchar(20),\n" +
        "    grade int,\n" +
        "    PRIMARY KEY(snum, course_number),\n" +
        "    FOREIGN KEY(snum) REFERENCES students(snum),\n" +
        "    FOREIGN KEY(course_number) REFERENCES courses(number)\n" +
        ");\n" +
        "\n" +
        "CREATE TABLE IF NOT EXISTS major(\n" +
        "\tsnum int,\n" +
        "    name varchar(50),\n" +
        "    level varchar(5),\n" +
        "    PRIMARY KEY(snum, name, level),\n" +
        "    FOREIGN KEY(snum) REFERENCES students(snum),\n" +
        "    FOREIGN KEY(name, level) REFERENCES degrees(name, level)\n" +
        ");\n" +
        "\n" +
        "CREATE TABLE IF NOT EXISTS minor(\n" +
        "\tsnum int,\n" +
        "    name varchar(50),\n" +
        "    level varchar(5),\n" +
        "    PRIMARY KEY(snum, name, level),\n" +
        "    FOREIGN KEY(snum) REFERENCES students(snum),\n" +
        "    FOREIGN KEY(name, level) REFERENCES degrees(name, level)\n" +
        ");";

// get connection
String connectionURL = "jdbc:mysql://localhost:3306/project1";
Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection conn = DriverManager.getConnection(connectionURL, "COMS363", "PASSWORD");

// sanitize script
ArrayList<String> inputText = new ArrayList<String>();
Scanner s = new Scanner(script);

StringBuilder statementConstructor = new StringBuilder();
String temp = null;
while (s.hasNextLine()) {
    statementConstructor.append(s.nextLine());
    if (statementConstructor.lastIndexOf(";") != -1) {
        inputText.add(statementConstructor.toString().replace(";", "").replaceAll("\\s+", " "));
        statementConstructor = new StringBuilder();
    }
}

Statement stmt = conn.createStatement();
for (String sql : inputText) {
    stmt.execute(sql);
}
%>
<h1> Your tables have successfully been created. </h1>

</body>
</html>