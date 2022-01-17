public class ConwayGameOfLife {
    private int[][] board;
    private int rows;
    private int cols;
    private int[][] next;

    public ConwayGameOfLife(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new int[rows][cols];
        next = new int[rows][cols];
    }

    public void setCell(int row, int col, int value) {
        board[row][col] = value;
    }

    public int getCell(int row, int col) {
        return board[row][col];
    }

    public void next() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                next[row][col] = getNextCell(row, col);
            }
        }
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = next[row][col];
            }
        }
    }

    private int getNextCell(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < cols) {
                    if (board[i][j] == 1) {
                        count++;
                    }
                }
            }
        }
        if (board[row][col] == 1) {
            if (count < 2 || count > 3) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (count == 3) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void print() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print(board[row][col]);
            }
            System.out.println();
        }
    }
}
