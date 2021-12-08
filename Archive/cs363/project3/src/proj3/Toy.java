package proj3;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A simple table management program
 *
 * @author Charle Yang
 */
public class Toy {
    /**
     * Toy ints are coded as 1
     */
    public static final int INTEGER = 1;

    /**
     * Toy doubles are coded as 2
     */
    public static final int DOUBLE = 2;

    /**
     * Toy bools are coded as 3
     */
    public static final int BOOLEAN = 3;

    /**
     * Toy strings are coded as 4
     */
    public static final int STRING = 4;

    /**
     * Stores the file path of table represented by Toy
     */
    private File tableFile;

    /**
     * Scanner to read the tableFile
     */
    private Scanner tableScanner;

    /**
     * List of attributes
     */
    private ArrayList<Attribute> attributes;

    /**
     * The Header
     */
    String header;

    /**
     * Constructs a Toy object that represents a DBMS for xyz.tb
     */
    public Toy() {
        tableFile = new File("xyz.tb");
        attributes = new ArrayList<>();
        if (tableFile.exists()) {
            header = header();
            String headerReduced = header.substring(4, header.length() - 4);

            Scanner headerScanner = new Scanner(headerReduced);
            headerScanner.useDelimiter("]\\[");
            while (headerScanner.hasNext()) {
                String attr = headerScanner.next();
                attributes.add(new Attribute(attr.substring(0, attr.lastIndexOf(":")), Integer.parseInt(String.valueOf(attr.charAt(attr.length() - 1)))));
            }
        }
    }

    /**
     * Initializes the process to create xyz.tb. Prompts the user to provide attributes and their type, then creates an empty xyz.tb file.
     *
     * @return if the table creation was successful
     */
    public boolean create() {
        if (tableFile.exists()) {
            System.out.println("Table xyz.tb already exists!");
            return false;
        }

        String response = "";
        Scanner in = new Scanner(System.in);
        StringBuilder header = new StringBuilder();
        String tempAttr;
        int numAttr = 0;
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");

        while (!response.equals("n")) {
            getAttr:
            while (true) {
                System.out.println("Attribute Name:");
                response = in.nextLine();

                if (pattern.matcher(response).find() || Character.isDigit(response.charAt(0)) || attributes.contains(response) || !isValidString(response)) {
                    System.out.println("Invalid Name! Try Again.");
                    continue getAttr;
                }
                header.append("[");
                header.append(response);
                tempAttr = response;
                header.append(":");
                numAttr++;
                break getAttr;
            }

            getType:
            while (true) {
                System.out.println("Select a type: 1: integer; 2: double; 3: boolean; 4: string");
                response = in.nextLine();

                if (isInteger(response) && Integer.parseInt(response) < 5 && Integer.parseInt(response) > 0) {
                    header.append(Integer.valueOf(response));
                    attributes.add(new Attribute(tempAttr, Integer.parseInt(response)));
                }
                else {
                    System.out.println("Invalid Type!");
                    continue getType;
                }
                header.append("]");
                break getType;
            }

            System.out.println("More attributes? (y/n)");
            response = in.nextLine();
        }

        try {
            FileWriter tableMaker = new FileWriter(tableFile);
            BufferedWriter bw = new BufferedWriter(tableMaker);
            bw.write("[" + numAttr + "]" + header.toString() + "[0]");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("FileWriter not initializable");
            return false;
        }

        this.header = header();

        return true;
    }

    /**
     * Deletes the entry at row rid
     *
     * @param rid record id
     * @return if the deletion was successful
     */
    public boolean delete(int rid) {
        ArrayList<String> lines = readAllLines();
        lines.remove(rid);
        readTable();

        try {
            FileWriter writer = new FileWriter(tableFile);
            BufferedWriter bw = new BufferedWriter(writer);
            writer.append(header + "\n");
            for (String s : lines) {
                writer.append(s + "\n");
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("File Error");
            return false;
        }

        updateRecordCount(-1);

        return true;
    }

    public boolean insert() {
        StringBuilder newRecord = new StringBuilder();
        Scanner in = new Scanner(System.in);
        String response;

        newRecord.append("{");

        for (int i = 0; i < attributes.size(); i++) {
            Attribute attribute = attributes.get(i);
            System.out.println(attributes.get(i) + ": ");
            response = in.nextLine();
            if (attributes.get(i).isValidValue(response)) {
                newRecord.append(response);
                newRecord.append("|");
            }
            else {
                System.out.println("Invalid input for " + attribute);
                --i;
                continue;
            }
        }
        newRecord.deleteCharAt(newRecord.length() - 1);
        newRecord.append("}");

        try {
            FileWriter writer = new FileWriter(tableFile, true);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.append(newRecord.toString());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("File Error");
            return false;
        }

        updateRecordCount(1);

        return true;
    }

    public void display(int rid) {
        ArrayList<String> records = readAllLines();
        if (rid >= 0 && rid < records.size()) {
            String record = records.get(rid);
            record = record.substring(1, record.lastIndexOf('}'));
            Scanner recordScanner = new Scanner(record);
            recordScanner.useDelimiter("\\|");
            for (Attribute attr : attributes) {
                System.out.println(attr + ": " + recordScanner.next());
            }
        }
    }

    /**
     * Searched for and display all records matching a condition
     *
     * @param condition condition to search by in the format "attr = val"
     */
    public void search(String condition) {
        //condition.replaceAll(" ", "");
        String searchAttr = condition.substring(0, condition.indexOf("=") - 1);
        String searchVal = condition.substring(condition.indexOf("=") + 2);

        int attrNum = -1;
        for (int i = 0; i < attributes.size(); i++) {
            if (attributes.get(i).name.equals(searchAttr)) {
                attrNum = i;
                break;
            }
        }
        if (attrNum == -1) return;

        ArrayList<String> entries = readAllLines();
        for (String s : entries) {
            s = s.substring(1, s.length() - 1);
            String searchString = s;
            for (int i = 0; i < attrNum; i++) {
                searchString = searchString.substring(searchString.indexOf("|") + 1);
            }
            if (searchString.substring(0, searchString.indexOf("|") == -1 ? searchString.length() : searchString.indexOf("|")).equals(searchVal)) {
                Scanner entryParser = new Scanner(s);
                entryParser.useDelimiter("\\|");
                for (Attribute a : attributes) {
                    System.out.println(a.toString() + ": " + entryParser.next());
                }
            }

        }
    }

    public String header() {
        readTable();
        return tableScanner.nextLine();
    }

    private boolean readTable() {
        try {
            tableScanner = new Scanner(tableFile);
        } catch (FileNotFoundException e) {
            System.out.println("The table xyz.tb does not exist!");
            return false;
        }
        return true;
    }

    private boolean isInteger(String s) {
        return s.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean isDouble(String s) {
        return s.matches("/^\\d+$/");
    }

    private boolean isBool(String s) {
        return s.equals("T") || s.equals("F");
    }

    private boolean isValidString(String s) {
        return !(s.contains("{") || s.contains("}") || s.contains("|") || s.contains("[") || s.contains("]"));
    }

    private ArrayList<String> readAllLines() {
        readTable();

        ArrayList<String> lines = new ArrayList<>();
        while (tableScanner.hasNextLine()) {
            lines.add(tableScanner.nextLine());
        }
        // remove header
        lines.remove(0);

        return lines;
    }

    private void updateRecordCount(int change) {
        StringBuilder newHeader = new StringBuilder(header);
        newHeader.setCharAt(header.length() - 2, String.valueOf(Integer.parseInt(String.valueOf(header.charAt(header.length() - 2))) + change).charAt(0));
        header = newHeader.toString();
        ArrayList<String> fileText = new ArrayList<>();
        fileText.add(header);
        fileText.addAll(readAllLines());
        try {
            FileWriter writer = new FileWriter(tableFile, false);
            BufferedWriter bw = new BufferedWriter(writer);
            for (String s : fileText) {
                bw.append(s);
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("File Error");
        }

    }

    class Attribute {
        protected String name;
        protected int type;

        protected Attribute(String name, int type) {
            this.name = name;
            this.type = type;
        }

        protected boolean isValidValue(String val) {
            return isValidString(val) && ((type == INTEGER && isInteger(val)) ||
                    (type == DOUBLE && isDouble(val)) ||
                    (type == BOOLEAN && isBool(val)) ||
                    (type == STRING));
        }

        @Override
        public String toString() {
            return name;
        }
    }
}

