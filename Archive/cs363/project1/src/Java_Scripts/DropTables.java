package Java_Scripts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static Java_Scripts.Project1B_Utilities.readScript;

public class DropTables {
    public static void main(String[] args) throws SQLException, IOException {
        Project1B_Utilities util = new Project1B_Utilities();
        Connection localhost = util.getConnection();
        generateDropTables();
        System.out.println(executeDropTables(localhost));
    }

    public static boolean generateDropTables() throws IOException {
        File script = new File("DropTables.sql");
        if (!script.exists()) {
            FileWriter scriptWriter = new FileWriter(script);
            scriptWriter.write("USE project1;\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS `register`;\n" +
                    "DROP TABLE IF EXISTS `courses`;\n" +
                    "DROP TABLE IF EXISTS `minor`;\n" +
                    "DROP TABLE IF EXISTS `major`;\n" +
                    "DROP TABLE IF EXISTS `degrees`;\n" +
                    "DROP TABLE IF EXISTS `departments`;\n" +
                    "DROP TABLE IF EXISTS `students`;"
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
     * @param conn Connection for which to drop tables
     * @return True if execution was successful
     * @throws SQLException
     */
    public static boolean executeDropTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        for (String s : readScript("DropTables.sql")) {
            stmt.execute(s);
        }

        return true;
    }
}
