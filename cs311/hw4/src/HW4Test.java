import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class HW4Test {
    public static void main(String[] args) throws IOException {
        RobotPath robotPath;

        /*robotPath = new RobotPath("Grid1.txt");

        robotPath.quickPlan();
        robotPath.output();

        System.out.println();

        robotPath.planShortest();
        robotPath.output();

        System.out.println();*/
        for (int i = 0; i < 1000; i++)
        {
            System.out.println(i);
            robotPath = new RobotPath(GenerateRandomGrid(25, 40, 100, new Random(i)));

            /*robotPath.quickPlan();
            robotPath.output();*/

            robotPath.planShortest();
            robotPath.output();
            System.out.println();
        }
    }

    /**
     * Writes a .txt file with the definition of a randomly generated grid that is parsable by RobotPath
     * @author Charles Yang
     * @param nRows
     * @param nCols
     * @param maxObstacles Maximum number of obstacles in the grid. Doing nObstacles would be a pain in the butt, so I ain't doing it.
     * @return File name of outputted file.
     * @throws IOException
     */
    public static String GenerateRandomGrid(int nRows, int nCols, int maxObstacles, Random random) throws IOException {
        // Setup
        File f = new File("Grid" + nRows + "R" + nCols + "C" + ".txt");
        FileWriter writer = new FileWriter(f);
        StringBuilder out = new StringBuilder();
        //Random random = new Random();

        out.append("nrows "); out.append(nRows);
        out.append(' ');
        out.append("ncols "); out.append(nCols);
        out.append('\n');

        // Define start and dest
        int startR = random.nextInt(nRows);
        int startC = random.nextInt(nCols);
        int destR = random.nextInt(nRows);
        int destC = random.nextInt(nCols);
        while (destR == startR && destC == startC) {
            destR = random.nextInt();
            destC = random.nextInt();
        }

        // Define obstacles
        ArrayList<int[]> obstacles = new ArrayList<>();
        int r, c;
        for (int i = 0; i < maxObstacles; i++) {
            r = random.nextInt(nRows);
            c = random.nextInt(nCols);
            while ((r == startR && c == startC) || (r == destR && c == destC)) {
                r = random.nextInt(nRows);
                c = random.nextInt(nCols);
            }
            obstacles.add(new int[]{r, c});
        }

        // Compile file text
        out.append("start "); out.append(startR); out.append(' '); out.append(startC);
        out.append('\n');

        out.append("dest "); out.append(destR); out.append(' '); out.append(destC);
        out.append('\n');

        out.append("obstacles");

        for (int[] o : obstacles) {
            out.append('\n');
            out.append(o[0]); out.append(' '); out.append(o[1]);
        }

        // System.out.println(out);
        // Output to file
        writer.write(out.toString());
        writer.close();

        return f.getName();
    }
}
