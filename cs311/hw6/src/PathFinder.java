import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PathFinder {
    Vertex[] Intersections;

    int n, m;

    public PathFinder() { }

    public PathFinder(String fileName) throws FileNotFoundException {
        readInput(fileName);
    }

    public void readInput (String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        Scanner ss;

        n = s.nextInt();
        Intersections = new Vertex[n];
        m = s.nextInt();
        s.nextLine();

        int idx, a, b;
        for (int i = 0; i < n; i++) {
            ss = new Scanner(s.nextLine());
            idx = ss.nextInt();
            a = ss.nextInt();
            b = ss.nextInt();
            Intersections[idx] = new Vertex(idx, a, b);
        }

        s.nextLine();

        for (int i = 0; i < m; i++) {
            ss = new Scanner(s.nextLine());
            a = ss.nextInt();
            b = ss.nextInt();
            Intersections[a].neighbors.add(Intersections[b]);
            Intersections[b].neighbors.add(Intersections[a]);
        }
    }

    public double[] shortestPathDistances (int source) {
        double[] distances = new double[n];
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        distances[source] = 0;

        MinHeap heap = new MinHeap();
        for (int i = 0; i < n; i++) {
            heap.add(i, distances[i]);
        }
        while (!heap.isEmpty()) {
            Vertex v = Intersections[heap.extractMin()];
            for (Vertex z : v.neighbors) {
                if (distances[z.idx] > distances[v.idx] + v.distTo(z)) {
                    distances[z.idx] = distances[v.idx] + v.distTo(z);
                    heap.decreaseKey(z.idx, distances[z.idx]);
                }
            }
        }

        for (int i = 0; i < n; i++)
            distances[i] = distances[i] == Double.POSITIVE_INFINITY ? -1 : distances[i];
        return distances;
    }

    public int noOfShortestPaths(int source, int destination) {
        int[] paths = new int[n];
        paths[source] = 1;

        double[] distances = new double[n];
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        distances[source] = 0;
        MinHeap heap = new MinHeap();
        for (int i = 0; i < n; i++) {
            heap.add(i, distances[i]);
        }
        while (!heap.isEmpty()) {
            Vertex v = Intersections[heap.extractMin()];
            for (Vertex z : v.neighbors) {
                double newDist = distances[v.idx] + v.distTo(z);
                if (distances[z.idx] > newDist) {
                    distances[z.idx] = newDist;
                    heap.decreaseKey(z.idx, distances[z.idx]);
                    paths[z.idx] = paths[v.idx];
                }
                else if (distances[z.idx] == newDist) {
                    paths[z.idx] += paths[v.idx];
                }
            }
        }
        return paths[destination];
    }

    public ArrayList<Integer> fromSrcToDest(int source, int destination, int A, int B) {
        ArrayList<Integer>[] paths = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            paths[i] = new ArrayList<>();
        }

        double[] distances = new double[n];
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        distances[source] = 0;
        MinHeap heap = new MinHeap();
        for (int i = 0; i < n; i++) {
            heap.add(i, distances[i]);
        }
        while (!heap.isEmpty()) {
            Vertex v = Intersections[heap.extractMin()];
            for (Vertex z : v.neighbors) {
                double newDist = A*(distances[v.idx] + v.distTo(z)) + B*(z.distTo(Intersections[destination]) - v.distTo(Intersections[destination]));
                if (distances[z.idx] > newDist) {
                    distances[z.idx] = newDist;
                    heap.decreaseKey(z.idx, distances[z.idx]);
                    paths[z.idx].clear();
                    paths[z.idx].addAll(paths[v.idx]);
                    paths[z.idx].add(v.idx);
                }
            }
        }

        if (paths[destination].isEmpty())
            return null;

        paths[destination].add(destination);
        return paths[destination];
    }

    public ArrayList<Integer> fromSrcToDestVia(int source, int destination, ArrayList<Integer> via, int A, int B) {
        via.add(destination);
        ArrayList<Integer> pathsVia = new ArrayList<>();
        for (int d : via) {
            ArrayList<Integer> path = fromSrcToDest(source, d, A, B);
            if (path == null)
                return null;
            path.remove(path.size() - 1);
            pathsVia.addAll(path);
            source = d;
        }
        pathsVia.add(destination);

        return pathsVia;
    }

    public int[] minCostReachabilityFromSrc(int source) {
        int[] parents = new int[n];
        Arrays.fill(parents, -1);
        parents[source] = source;

        boolean[] visited = new boolean[n];

        double[] distances = new double[n];
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        distances[source] = 0;
        MinHeap heap = new MinHeap();
        for (int i = 0; i < n; i++) {
            heap.add(i, distances[i]);
        }
        while (!heap.isEmpty()) {
            Vertex v = Intersections[heap.extractMin()];
            visited[v.idx] = true;
            for (Vertex z : v.neighbors) {
                if (!visited[z.idx] && distances[z.idx] > v.distTo(z)) {
                    distances[z.idx] = v.distTo(z);
                    parents[z.idx] = v.idx;
                    heap.decreaseKey(z.idx, distances[z.idx]);
                }
            }
        }

        return parents;
    }

    public double minCostOfReachabilityFromSrc(int source) {
        boolean[] visited = new boolean[n];
        double[] distances = new double[n];
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        distances[source] = 0;
        MinHeap heap = new MinHeap();
        for (int i = 0; i < n; i++) {
            heap.add(i, distances[i]);
        }
        while (!heap.isEmpty()) {
            Vertex v = Intersections[heap.extractMin()];
            visited[v.idx] = true;
            for (Vertex z : v.neighbors) {
                if (!visited[z.idx] && distances[z.idx] > v.distTo(z)) {
                    distances[z.idx] = v.distTo(z);
                    heap.decreaseKey(z.idx, distances[z.idx]);
                }
            }
        }

        return Arrays.stream(distances).filter(x -> x != Double.POSITIVE_INFINITY).sum();
    }

    public boolean isFullReachableFromSrc(int source) {
        return Arrays.stream(minCostReachabilityFromSrc(source)).noneMatch(a -> a == -1);
    }
}

class Vertex {
    // Node information
    int idx;
    int x;
    int y;
    ArrayList<Vertex> neighbors;

    Vertex(int idx, int x, int y) {
        this.x = x;
        this.y = y;
        this.idx = idx;
        neighbors = new ArrayList<>();
    }

    double distTo(Vertex v) {
        return Math.sqrt(Math.pow(x - v.x, 2) + Math.pow(y - v.y, 2));
    }
}

class MinHeap {
    ArrayList<Integer> keys;
    ArrayList<Double> values;
    HashMap<Integer, Integer> idxMap;

    public MinHeap() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
        idxMap = new HashMap<>();
    }

    public boolean isEmpty() {
        return keys.isEmpty();
    }

    public void add(int key, double value) {
        keys.add(key);
        values.add(value);
        idxMap.put(key, keys.size() - 1);
        bubbleUp(keys.size() - 1);
    }

    public void decreaseKey(int key, double value) {
        if (idxMap.containsKey(key)) {
            int i = idxMap.get(key);
            values.set(i, value);
            bubbleUp(i);
        }
    }

    public int extractMin() {
        int min = keys.get(0);
        swap(0, keys.size() - 1);
        keys.remove(keys.size() - 1);
        values.remove(values.size() - 1);
        idxMap.remove(min);
        bubbleDown(0);
        return min;
    }

    private void swap(int i, int j) {
        int temp = keys.get(i);
        keys.set(i, keys.get(j));
        keys.set(j, temp);

        double temp2 = values.get(i);
        values.set(i, values.get(j));
        values.set(j, temp2);

        idxMap.put(keys.get(i), i);
        idxMap.put(keys.get(j), j);
    }

    private void bubbleUp(int i) {
        if (i == 0) return;
        int parent = (i - 1) / 2;
        if (values.get(parent) > values.get(i)) {
            swap(parent, i);
            bubbleUp(parent);
        }
    }

    private void bubbleDown(int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int min = i;
        if (left < keys.size() && values.get(left) < values.get(min)) {
            min = left;
        }
        if (right < keys.size() && values.get(right) < values.get(min)) {
            min = right;
        }
        if (min != i) {
            swap(i, min);
            bubbleDown(min);
        }
    }

    ArrayList<Integer> getHeap() {
        return keys;
    }


}


