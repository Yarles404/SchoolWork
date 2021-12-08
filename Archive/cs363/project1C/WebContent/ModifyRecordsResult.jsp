<%@ page import="java.io.*,java.util.*, java.sql.*" %>
<%@ page import="javax.servlet.http.*, javax.servlet.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modify Records Result</title>
</head>
<body>
<%
// original script code
String script = "USE project1;\n" +
        "\n" +
        "UPDATE students\n" +
        "\tSET name = 'Scott'\n" +
        "    WHERE ssn = 746897816;\n";

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

<h1>Record Modification Success</h1>
</body>
</html>