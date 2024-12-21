//designed to model the number puzzle, which holds the numbers and clues in 9x9 
//int array numbers and boolean array isGiven. The method newPuzzle() can be used 
//to generate a new puzzle for a new game.

/**
 * The Sudoku number puzzle to be solved
 */

package sudoku;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Random;

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
    // to control the difficulty level.
    public void newPuzzle(int difficultyLevel) {
        int cellsToGuess;
        switch (difficultyLevel) {
            case 1: // Easy
                cellsToGuess = 63; // // cells to guess (9x9 grid = 81, 81-63 so 18 are empty)
                break;
            case 2: // Normal-Medium
                cellsToGuess = 45; // Example: 36 cells to guess
                break;
            case 3: // Hard
                cellsToGuess = 27; // Example: 54 cells to guess
                break;
            default:
                cellsToGuess = 45; // Default to medium/normal
        }

        // Hardcoded puzzle for illustration
        int[][] hardcodedNumbers = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        // Copy hardcodedNumbers into the array "numbers"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = hardcodedNumbers[row][col];
            }
        }

        // Generate "isGiven" based on cellsToGuess
        boolean[][] newIsGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
        Random random = new Random();
        int count = 0;

        while (count < cellsToGuess) {
            int row = random.nextInt(SudokuConstants.GRID_SIZE);
            int col = random.nextInt(SudokuConstants.GRID_SIZE);
            if (!newIsGiven[row][col]) {
                newIsGiven[row][col] = true;
                count++;
            }
        }

        // Update the isGiven array
        isGiven = newIsGiven;
    }
}