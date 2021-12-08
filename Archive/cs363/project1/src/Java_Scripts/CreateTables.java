package Java_Scripts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import static Java_Scripts.Project1B_Utilities.readScript;

public class CreateTables {
    protected static final String userName = "COMS363";
    protected static final String password = "PASSWORD";
    protected static final String dbms = "mysql";
    protected static final String serverName = "localhost";
    protected static final String portNumber = "3306";
    protected static final String dbName = "";

    public static void main(String[] args) throws SQLException, IOException {
        Project1B_Utilities util = new Project1B_Utilities();
        Connection localhost = util.getConnection();
        generateCreateTables();
        System.out.println(executeCreateTables(localhost));

    }

    public static boolean generateCreateTables() throws IOException {
        File script = new File("CreateTables.sql");
        if (!script.exists()) {
            FileWriter scriptWriter = new FileWriter(script);
            scriptWriter.write("CREATE DATABASE IF NOT EXISTS project1;\n" +
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
                    ");\n" +
                    "\n" +
                    "\n" +
                    "\n"
            );
            scriptWriter.close();
            System.out.println("Script Created");
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @param conn Connection for which to create schema and tables
     * @return True if execution was successful
     * @throws SQLException
     */
    public static boolean executeCreateTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        for (String s : readScript("CreateTables.sql")) {
            stmt.execute(s);
        }

        return true;
    }
}
