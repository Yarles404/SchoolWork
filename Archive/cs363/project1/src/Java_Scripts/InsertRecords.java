package Java_Scripts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static Java_Scripts.Project1B_Utilities.readScript;

public class InsertRecords {
    public static void main(String[] args) throws SQLException, IOException {
        Project1B_Utilities util = new Project1B_Utilities();
        Connection localhost = util.getConnection();
        generateInsertRecords();
        System.out.println(executeInsertRecords(localhost));
    }

    public static boolean generateInsertRecords() throws IOException {
        File script = new File("InsertRecords.sql");
        if (!script.exists()) {
            FileWriter scriptWriter = new FileWriter(script);
            scriptWriter.write("USE project1;\n" +
                    "\n" +
                    "INSERT INTO students(snum, ssn, name, gender, dob, c_addr, c_phone, p_addr, p_phone) VALUES\n" +
                    "(1001, 654651234, 'Randy', 'M', '2000/12/01', '301 E Hall', '5152700988', '121 Main', '7083066321'),\n" +
                    "(1002, 123097834, 'Victor', 'M', '2000/05/06', '270 W Hall', '5151234578', '702 Walnut', '7080366333'),\n" +
                    "(1003, 978012431, 'John', 'M', '1999/07/08', '201 W Hall', '5154132805', '888 University', '5152012011'),\n" +
                    "(1004, 746897816, 'Seth', 'M', '1998/12/30', '199 N Hall', '5158891504', '21 Green', '5154132907'),\n" +
                    "(1005, 186032894, 'Nicole', 'F', '2001/04/01', '178 S Hall', '5158891155', '13 Gray', '5157162071'),\n" +
                    "(1006, 534218752, 'Becky', 'F', '2001/05/16', '12 N Hall', '5157083698', '189 Clark', '2034367632'),\n" +
                    "(1007, 432609519, 'Kevin', 'M', '2000/08/12', '75 E Hall', '5157082497', '89 National', '7182340772');\n" +
                    "\n" +
                    "INSERT INTO departments(code, name, phone, college) VALUES\n" +
                    "(401, 'Computer Science', '5152982801', 'LAS'),\n" +
                    "(402, 'Mathematics', '5152982802', 'LAS'),\n" +
                    "(403, 'Chemical Engineering', '5152982803', 'Engineering'),\n" +
                    "(404, 'Landscape Architect', '5152982804', 'Design');\n" +
                    "\n" +
                    "INSERT INTO degrees VALUES\n" +
                    "('Computer Science', 'BS', 401),\n" +
                    "('Software Engineering', 'BS', 401),\n" +
                    "('Computer Science', 'MS', 401),\n" +
                    "('Computer Science', 'PhD', 401),\n" +
                    "('Applied Mathematics', 'MS', 402),\n" +
                    "('Chemical Engineering', 'BS', 403),\n" +
                    "('Landscape Architect', 'BS', 404);\n" +
                    "\n" +
                    "INSERT INTO major VALUES\n" +
                    "(1001, 'Computer Science', 'BS'),\n" +
                    "(1002, 'Software Engineering', 'BS'),\n" +
                    "(1003, 'Chemical Engineering', 'BS'),\n" +
                    "(1004, 'Landscape Architect', 'BS'),\n" +
                    "(1005, 'Computer Science', 'MS'),\n" +
                    "(1006, 'Applied Mathematics', 'MS'),\n" +
                    "(1007, 'Computer Science', 'PhD');\n" +
                    "\n" +
                    "INSERT INTO minor VALUES\n" +
                    "(1007, 'Applied Mathematics', 'MS'),\n" +
                    "(1005, 'Applied Mathematics', 'MS'),\n" +
                    "(1001, 'Software Engineering', 'BS');\n" +
                    "\n" +
                    "INSERT INTO courses VALUES\n" +
                    "(113, 'Spreadsheet', 'Microsoft Excel and Access', 3, 'Undergraduate', 401),\n" +
                    "(311, 'Algorithm', 'Design and Analysis', 3, 'Undergraduate', 401),\n" +
                    "(531, 'Theory of Computation', 'Theorem and Probability', 3, 'Graduate', 401),\n" +
                    "(363, 'Database', 'Design Principle', 3, 'Undergraduate', 401),\n" +
                    "(412, 'Water Management', 'Water Management', 3, 'Undergraduate', 404),\n" +
                    "(228, 'Special Topics', 'Interesting Topics about CE', 3, 'Undergraduate', 403),\n" +
                    "(101, 'Calculus', 'Limit and Derivative', 4, 'Undergraduate', 402);\n" +
                    "\n" +
                    "INSERT INTO register VALUES\n" +
                    "(1001, 363, 'Fall2020', 3),\n" +
                    "(1002, 311, 'Fall2020', 4),\n" +
                    "(1003, 228, 'Fall2020', 4),\n" +
                    "(1004, 363, 'Spring2021', 3),\n" +
                    "(1005, 531, 'Spring2021', 4),\n" +
                    "(1006, 363, 'Fall2020', 3),\n" +
                    "(1007, 531, 'Spring2021', 4);\n"
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
     * @param conn Connection for which to InsertRecords
     * @return True if execution was successful
     * @throws SQLException
     */
    public static boolean executeInsertRecords(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        for (String s : readScript("InsertRecords.sql")) {
            stmt.execute(s);
        }

        return true;
    }
}
