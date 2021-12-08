<%@ page import="java.io.*,java.util.*, java.sql.*" %>
<%@ page import="javax.servlet.http.*, javax.servlet.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Query Result 1</title>
</head>
<body>
<%
//original script code
String query = "SELECT c.number, c.name, d.name FROM courses c\n" +
        "\tJOIN departments d ON d.code = c.department_code\n" +
        "    WHERE d.name = 'Computer Science' or d.name = 'Landscape Architect';\n";

//get connection
String connectionURL = "jdbc:mysql://localhost:3306/project1";
Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection conn = DriverManager.getConnection(connectionURL, "COMS363", "PASSWORD");

//sanitize script
ArrayList<String> inputText = new ArrayList<String>();
Scanner s = new Scanner(query);

StringBuilder statementConstructor = new StringBuilder();
String temp = null;
while (s.hasNextLine()) {
 statementConstructor.append(s.nextLine());
 if (statementConstructor.lastIndexOf(";") != -1) {
     inputText.add(statementConstructor.toString().replace(";", "").replaceAll("\\s+", " "));
     statementConstructor = new StringBuilder();
 }
}

// Run query
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(inputText.get(0));

ResultSetMetaData rsmd = rs.getMetaData();
int columnsNumber = rsmd.getColumnCount();

for (int i = 1; i <= columnsNumber; i++) {
    out.print(rsmd.getColumnName(i) + " | ");
}
out.print("<br/>");

while (rs.next()) {
	for (int i = 1; i <= columnsNumber; i++) {
	    out.print(rs.getString(i) + " | ");
	}
	out.print("<br/>");
}


%>

</body>
</html>