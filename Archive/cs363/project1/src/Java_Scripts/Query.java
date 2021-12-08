package Java_Scripts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import static Java_Scripts.Project1B_Utilities.readScript;

public class Query {
    public static void main(String[] args) throws SQLException, IOException {
        Project1B_Utilities util = new Project1B_Utilities();
        Connection localhost = util.getConnection();
        generateQuery();
        System.out.println(executeQuery(localhost));
    }

    public static boolean generateQuery() throws IOException {
        File script = new File("Query.sql");
        if (!script.exists()) {
            FileWriter scriptWriter = new FileWriter(script);
            scriptWriter.write("USE project1;\n" +
                    "\n" +
                    "SELECT name, snum, ssn FROM students\n" +
                    "\tWHERE name = 'Becky';\n" +
                    "    \n" +
                    "SELECT m.name, m.level, s.ssn FROM students s\n" +
                    "    JOIN major m ON m.snum = s.snum\n" +
                    "    WHERE s.ssn = 123097834;\n" +
                    "    \n" +
                    "SELECT c.name, d.name FROM courses c\n" +
                    "\tJOIN departments d ON d.code = c.department_code\n" +
                    "    WHERE d.name = 'Computer Science';\n" +
                    "    \n" +
                    "SELECT dg.name, dg.level, d.name FROM degrees dg\n" +
                    "\tJOIN departments d ON d.code = dg.department_code\n" +
                    "    WHERE d.name = 'Computer Science';\n" +
                    "    \n" +
                    "SELECT s.name, m.name FROM students s\n" +
                    "\tJOIN minor m ON m.snum = s.snum;\n" +
                    "    \n" +
                    "SELECT COUNT(*) FROM students s\n" +
                    "\tJOIN minor m ON m.snum = s.snum;\n" +
                    "    \n" +
                    "SELECT s.name, s.snum FROM students s\n" +
                    "\tJOIN register r ON r.snum = s.snum\n" +
                    "    JOIN courses c ON r.course_number = c.number\n" +
                    "    WHERE c.name = 'Algorithm';\n" +
                    "    \n" +
                    "SELECT name, snum, dob FROM students\n" +
                    "\tWHERE dob = (SELECT MIN(dob) FROM students);\n" +
                    "\n" +
                    "SELECT name, snum, dob FROM students\n" +
                    "\tWHERE dob = (SELECT MAX(dob) FROM students);\n" +
                    "    \n" +
                    "SELECT name, snum, ssn FROM students\n" +
                    "\tWHERE name LIKE '%n%' or name LIKE '%N%';    \n" +
                    "\n" +
                    "SELECT name, snum, ssn FROM students\n" +
                    "\tWHERE name NOT LIKE '%n%' or name NOT LIKE '%N%';\n" +
                    "\n" +
                    "SELECT c.number, c.name, COUNT(r.snum)\n" +
                    "\tFROM courses c\n" +
                    "\tLEFT OUTER JOIN register r on c.number = r.course_number\n" +
                    "\tgroup by c.number;\n" +
                    "\n" +
                    "SELECT s.name, r.regtime FROM students s\n" +
                    "\tJOIN register r ON r.snum = s.snum\n" +
                    "    WHERE r.regtime = 'Fall2020';\n" +
                    "    \n" +
                    "SELECT c.number, c.name, d.name FROM courses c\n" +
                    "\tJOIN departments d ON d.code = c.department_code\n" +
                    "    WHERE d.name = 'Computer Science';\n" +
                    "    \n" +
                    "SELECT c.number, c.name, d.name FROM courses c\n" +
                    "\tJOIN departments d ON d.code = c.department_code\n" +
                    "    WHERE d.name = 'Computer Science' or d.name = 'Landscape Architect';\n" +
                    "    \n"
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
     * @param conn Connection for which to Query
     * @return True if execution was successful
     * @throws SQLException
     */
    public static boolean executeQuery(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ArrayList<ResultSet> out = new ArrayList<>();
        ArrayList<String> script = readScript("src/MySQL_Scripts/Query.sql");

        stmt.execute(script.get(0));
        script.remove(0);
        for (String s : script) {
            stmt = conn.createStatement();
            out.add(stmt.executeQuery(s));
        }

        for (ResultSet rs : out) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                }
                System.out.println();
            }
            System.out.println();
        }

        return true;
    }
}
