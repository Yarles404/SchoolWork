import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Charles Yang
 */
public class RobotPath {
    private static final char EMPTY = '0';
    private static final char START = 'S';
    private static final char OBSTACLE = '*';
    private static final char DESTINATION = 'D';
    private final South SOUTH = new South();
    private final North NORTH = new North();
    private final West WEST = new West();
    private final East EAST = new East();
    private Location[][] Grid;
    private Location Start;
    private Location Destination;
    private int Rows;
    private int Cols;

    public RobotPath() {
    }

    public RobotPath(String FileName) throws IOException {
        this.readInput(FileName);
    }

    public void readInput(String FileName) throws IOException {
        File f = new File(FileName);
        Scanner s = new Scanner(f);
        Scanner ss;

        // Get dimensions
        String dim = s.nextLine();
        ss = new Scanner(dim);
        ss.next();
        Rows = ss.nextInt();
        ss.next();
        Cols = ss.nextInt();

        Grid = new Location[Rows][Cols];

        // Initialize EMPTY Grid
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {
                Grid[i][j] = new Location(EMPTY, i, j);
            }
        }

        // Get start
        String start = s.nextLine();
        ss = new Scanner(start);
        ss.next();
        Start = new Location(START, ss.nextInt(), ss.nextInt());
        Grid[Start.R][Start.C] = Start;

        // Get destination
        String dest = s.nextLine();
        ss = new Scanner(dest);
        ss.next();
        Destination = new Location(DESTINATION, ss.nextInt(), ss.nextInt());
        Grid[Destination.R][Destination.C] = Destination;

        // Skip "obstacles"
        s.nextLine();

        // Add all obstacles
        while (s.hasNextLine()) {
            ss = new Scanner(s.nextLine());
            Grid[ss.nextInt()][ss.nextInt()].Type = OBSTACLE;
        }

        s.close();
    }

    public void planShortest() {
        // Initialize parent sets.
        HashMap<Location, HashSet<Move>> parents = new HashMap<>();
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {
                parents.put(Grid[i][j], new HashSet<>());
            }
        }

        // Do BFS and assign parents as appropriate.
        Queue<Location> toVisit = new LinkedList<>();
        Start.level = 0;
        Location current;
        toVisit.add(Start);
        while (!toVisit.isEmpty()) {
            current = toVisit.poll();
            if (SOUTH.ValidTransform(current)) {
                computeParents(parents, toVisit, current, SOUTH);
            }
            if (NORTH.ValidTransform(current)) {
                computeParents(parents, toVisit, current, NORTH);
            }
            if (WEST.ValidTransform(current)) {
                computeParents(parents, toVisit, current, WEST);
            }
            if (EAST.ValidTransform(current)) {
                computeParents(parents, toVisit, current, EAST);
            }
        }

        // Retrace paths starting at destination
        markPaths(Destination, parents);
    }

    private void computeParents(HashMap<Location, HashSet<Move>> parents, Queue<Location> toVisit, Location current, Directive directive) {
        // Shortest path traversal and parents logic
        Move next = new Move(current, directive);
        if (next.to.level > current.level + 1) {
            next.to.level = current.level + 1;
            toVisit.offer(next.to);
            //parents.get(next.to).clear(); // Not necessary? because with a BFS on a grid of nodes, there will never be a shorter path once you reach a node once.
            parents.get(next.to).add(next);
        }
        else if (next.to.level == current.level + 1) {
            parents.get(next.to).add(next);
        }
    }

    private void markPaths(Location destination, HashMap<Location, HashSet<Move>> parentsSets) {
        HashSet<Move> parents = parentsSets.get(destination);
        for (Move move : parents) {
            move.from.IsPath = true;
            move.from.Path[move.directive.order] = move.directive.ch;
            markPaths(move.from, parentsSets);
        }
    }

    public void quickPlan() {
        quickPlanRec(Start);
    }

    private boolean quickPlanRec(Location loc) {
        // Quick terminate
        if (loc == Destination) return true;
        loc.Visited = true;

        ArrayList<Move> moves = new ArrayList<>();

        // Find viable moves
        if (SOUTH.ValidTransform(loc) && !Grid[loc.R + SOUTH.dr][loc.C + SOUTH.dc].Visited) {
            moves.add(new Move(loc, SOUTH));
        }
        if (NORTH.ValidTransform(loc) && !Grid[loc.R + NORTH.dr][loc.C + NORTH.dc].Visited) {
            moves.add(new Move(loc, NORTH));
        }
        if (WEST.ValidTransform(loc) && !Grid[loc.R + WEST.dr][loc.C + WEST.dc].Visited) {
            moves.add(new Move(loc, WEST));
        }
        if (EAST.ValidTransform(loc) && !Grid[loc.R + EAST.dr][loc.C + EAST.dc].Visited) {
            moves.add(new Move(loc, EAST));
        }

        // Sort moves by EuclideanToDest
        int j;
        for (int i = 1; i < moves.size(); i++) {
            j = i;
            while (j - 1 >= 0 && moves.get(j).to.compareTo(moves.get(j - 1).to) < 0) {
                Move temp = moves.get(j - 1);
                moves.set(j - 1, moves.get(j));
                moves.set(j, temp);
                j--;
            }
        }

        // Do moves in order
        for (Move m : moves) {
            if (quickPlanRec(m.to)) {
                loc.Path[m.directive.order] = m.directive.ch;
                loc.IsPath = true;
                return true;
            }
        }

        return false;
    }

    public void output() {
        Start.IsPath = false;
        Destination.IsPath = false;
        for (int r = 0; r < Rows; r++) {
            for (int c = 0; c < Cols; c++) {
                Location location = Grid[r][c];
                StringBuilder sb = new StringBuilder();
                if (location.IsPath) { // If Location is part of path, print directives
                    for (int i = 0; i < 4; i++) {
                        // Ensures snwe order is preserved
                        if (location.Path[i] != '\u0000') {
                            sb.append(location.Path[i]);
                        }
                    }
                    System.out.printf("%5s", sb);
                }
                else { // Location is not part of path
                    System.out.printf("%5s", location.Type);
                }

            }
            System.out.println();
        }

        ResetGrid();
    }

    private void ResetGrid() {
        for (int r = 0; r < Rows; r++) {
            for (int c = 0; c < Cols; c++) {
                Grid[r][c].Reset();
            }
        }
    }

    private double EuclideanToDest(Location loc) {
        return loc == null ? Integer.MAX_VALUE : Math.sqrt(Math.pow(loc.C - Destination.C, 2) + Math.pow(loc.R - Destination.R, 2));
    }

    // Helps keep track of directions and parents
    private class Move {
        Location from;
        Location to;
        Directive directive;

        Move(Location from, Directive directive) {
            this.from = from;
            this.to = Grid[from.R + directive.dr][from.C + directive.dc];
            this.directive = directive;
        }
    }

    // Grid items
    private class Location implements Comparable<Location> {
        boolean Visited;
        boolean IsPath;
        int level = Integer.MAX_VALUE;
        char Type;
        int R;
        int C;
        char[] Path = new char[4];

        Location(char type, int r, int c) {
            this.Type = type;
            R = r;
            C = c;
        }

        void Reset() {
            Visited = false;
            IsPath = false;
            Path = new char[4];
            level = Integer.MAX_VALUE;
        }

        @Override
        public int compareTo(Location o) {
            double diff = EuclideanToDest(this) - EuclideanToDest(o);
            if (diff < 0) {
                return -1;
            }
            else if (diff > 0) {
                return 1;
            }
            else {
                return this.R - o.R;
            }
        }
    }

    // Helper classes for directives
    private abstract class Directive {
        char ch;
        int dr;
        int dc;
        int order;

        Directive(char ch, int dr, int dc, int order) {
            this.ch = ch;
            this.dr = dr;
            this.dc = dc;
            this.order = order;
        }

        boolean ValidTransform(Location loc) {
            return loc.C + dc >= 0 &&
                    loc.C + dc < Cols &&
                    loc.R + dr >= 0 &&
                    loc.R + dr < Rows &&
                    Grid[loc.R + dr][loc.C + dc].Type != OBSTACLE;
        }
    }

    private class South extends Directive {
        South() {
            super('s', 1, 0, 0);
        }
    }

    private class North extends Directive {
        North() {
            super('n', -1, 0, 1);
        }
    }

    private class West extends Directive {
        West() {
            super('w', 0, -1, 2);
        }
    }

    private class East extends Directive {
        East() {
            super('e', 0, 1, 3);
        }
    }
}
