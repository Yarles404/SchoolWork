package Java_Scripts;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import static Java_Scripts.CreateTables.dbms;
import static Java_Scripts.CreateTables.portNumber;


public class Project1B_Utilities {
    public static void main(String[] args) {
        System.out.println(readScript("src/MySQL_Scripts/CreateTables.sql"));
    }

    /**
     * A utility method for sanitizing whitespace in SQL files for use with JDBC
     *
     * @param filePath File to sanitize
     * @return A string for which all whitespace in the original file has been replaced with a single space
     */
    public static ArrayList<String> readScript(String filePath) {
        File f = new File(filePath);
        ArrayList<String> inputText = new ArrayList<String>();
        Scanner s = new Scanner("");
        try {
            s = new Scanner(f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder statementConstructor = new StringBuilder();
        String temp = null;
        while (s.hasNextLine()) {
            statementConstructor.append(s.nextLine());
            if (statementConstructor.lastIndexOf(";") != -1) {
                inputText.add(statementConstructor.toString().replace(";", "").replaceAll("\\s+", " "));
                statementConstructor = new StringBuilder();
            }
        }

        return inputText;
    }

    /**
     * @return A Connection object for localhost, using user as specified in the class fields.
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", CreateTables.userName);
        connectionProps.put("password", CreateTables.password);

        conn = DriverManager.getConnection(
                "jdbc:" + dbms + "://" +
                        CreateTables.serverName +
                        ":" + portNumber + "/",
                connectionProps);
        System.out.println(connectionProps);
        System.out.println("Connected to database");
        return conn;
    }
}
