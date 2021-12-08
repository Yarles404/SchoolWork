package Java_Scripts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static Java_Scripts.Project1B_Utilities.readScript;

public class ModifyRecords {
    public static void main(String[] args) throws SQLException, IOException {
        Project1B_Utilities util = new Project1B_Utilities();
        Connection localhost = util.getConnection();
        generateModifyRecords();
        System.out.println(executeModifyRecords(localhost));
    }

    public static boolean generateModifyRecords() throws IOException {
        File script = new File("ModifyRecords.sql");
        if (!script.exists()) {
            FileWriter scriptWriter = new FileWriter(script);
            scriptWriter.write("USE project1;\n" +
                    "\n" +
                    "UPDATE students\n" +
                    "\tSET name = 'Scott'\n" +
                    "    WHERE ssn = 746897816;\n" +
                    "    \n" +
                    "UPDATE major m\n" +
                    "\tJOIN students s ON s.snum = m.snum\n" +
                    "    SET m.name = 'Computer Science', m.level = 'MS'\n" +
                    "    WHERE s.ssn = 746897816;\n" +
                    "\n" +
                    "SET SQL_SAFE_UPDATES = 0;\n" +
                    "DELETE FROM register\n" +
                    "\tWHERE regtime = 'Spring2021';\n" +
                    "SET SQL_SAFE_UPDATES = 1;"
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
     * @param conn Connection for which to modify records
     * @return True if execution was successful
     * @throws SQLException
     */
    public static boolean executeModifyRecords(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        for (String s : readScript("ModifyRecords.sql")) {
            stmt.execute(s);
        }

        return true;
    }
}
