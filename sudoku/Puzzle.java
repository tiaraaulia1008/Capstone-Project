//designed to model the number puzzle, which holds the numbers and clues in 9x9 
//int array numbers and boolean array isGiven. The method newPuzzle() can be used 
//to generate a new puzzle for a new game.

/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {
    // All variables have package access
    // The numbers on the puzzle
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    // The clues - isGiven (no need to guess) or need to guess
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    // Constructor
    public Puzzle() {
        super();
    }

    // Generate a new puzzle given the number of cells to be guessed, which can be used
    //  to control the difficulty level.
    // This method shall set (or update) the arrays numbers and isGiven
    public void newPuzzle(String level) {
        int cellsToGuess;
        switch (level.toLowerCase()) {
            case "easy":
                cellsToGuess = 75;
                break;
            case "intermediate":
                cellsToGuess = 55;
                break;
            case "difficult":
                cellsToGuess = 30;
                break;
            default:
                cellsToGuess = 30;
        }
        // I hardcode a puzzle here for illustration and testing.
        int[][] hardcodedNumbers =
                {{5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
            };

        // Copy from hardcodedNumbers into the array "numbers"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = hardcodedNumbers[row][col];
            }
        }

        // Need to use input parameter cellsToGuess!
        // Hardcoded for testing, only 2 cells of "8" is NOT GIVEN
        boolean[][] newIsGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
        int count = 0;
        while (count < cellsToGuess) {
            int row = (int) (Math.random() * SudokuConstants.GRID_SIZE);
            int col = (int) (Math.random() * SudokuConstants.GRID_SIZE);
            if (!newIsGiven[row][col]) {
                newIsGiven[row][col] = true;
                count++;
            }
        }
        isGiven = newIsGiven;
    }

    //(For advanced students) use singleton design pattern for this class
}