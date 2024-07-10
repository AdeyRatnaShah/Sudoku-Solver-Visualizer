
import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class SudokuSolver {

    final static int N = 9;  // Size of the Sudoku grid
    static JLabel[][] jLabel = new JLabel[N][N];

    static int board[][] = {
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };

    static int board2[][] = {
        {0, 0, 0, 6, 0, 0, 4, 0, 0},
        {7, 0, 0, 0, 0, 3, 6, 0, 0},
        {0, 0, 0, 0, 9, 1, 0, 8, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 5, 0, 1, 8, 0, 0, 0, 3},
        {0, 0, 0, 3, 0, 6, 0, 4, 5},
        {0, 4, 0, 2, 0, 0, 0, 6, 0},
        {9, 0, 3, 0, 0, 0, 0, 0, 0},
        {0, 2, 0, 0, 0, 0, 1, 0, 0}
    };
    
    static void printSolution() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                System.out.printf("%d ", board[i][j]);
            }
            System.out.printf("\n");
        }
    }

    static boolean isSafe(int row, int col, int num) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check the row
        for (int x = 0; x < N; x++) {
            if (board[row][x] == num) {
                return false;
            }
        }

        // Check the column
        for (int x = 0; x < N; x++) {
            if (board[x][col] == num) {
                return false;
            }
        }

        // Check the 3x3 subgrid
        int startRow = row - row % 3, startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    static boolean findSolution(int row, int col) {
        if (row == N - 1 && col == N) {
            return true;
        }

        if (col == N) {
            row++;
            col = 0;
        }

        if (board[row][col] != 0) {
            return findSolution(row, col + 1);
        }

        for (int num = 1; num <= N; num++) {
            if (isSafe(row, col, num)) {
                board[row][col] = num;
                jLabel[row][col].setText(String.valueOf(num));
                jLabel[row][col].setBackground(Color.CYAN);

                if (findSolution(row, col + 1)) {
                    return true;
                }

                board[row][col] = 0;
                jLabel[row][col].setText("0");
                jLabel[row][col].setBackground(Color.RED);
            }
        }

        return false;
    }

    static void solveSudoku() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                jLabel[i][j].setText(board[i][j] == 0 ? "0" : String.valueOf(board[i][j]));
                jLabel[i][j].setBackground(Color.LIGHT_GRAY);
            }
        }

        if (!findSolution(0, 0)) {
            System.out.println("No Solution.\n");
        } else {
            printSolution();
        }
    }

    SudokuSolver() {
        JFrame jFrame = new JFrame("Sudoku Solver Visualizer.");
        jFrame.setLayout(new GridLayout(N, N));
        jFrame.setSize(500, 500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                jLabel[i][j] = new JLabel("0");
                jLabel[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                jLabel[i][j].setSize(50, 50);
                jLabel[i][j].setOpaque(true);
                
                int top = (i % 3 == 0) ? 3 : 1;
                int left = (j % 3 == 0) ? 3 : 1;
                int bottom = ((i + 1) % 3 == 0) ? 3 : 1;
                int right = ((j + 1) % 3 == 0) ? 3 : 1;
        
                jLabel[i][j].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                
                jFrame.add(jLabel[i][j]);
            }
        }

        jFrame.setVisible(true);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuSolver();
            }
        });
        printSolution();
        System.out.println();
        solveSudoku();

        printSolution();
    }
}

