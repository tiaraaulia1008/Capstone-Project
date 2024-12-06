/**
* ES234317-Algorithm and Data Structures
* Semester Ganjil, 2024/2025
* Group Capstone Project
* Group #8
* 1 - 5026231023 - Nadya Luthfiyah Rahma
* 2 - 5026231094 - Davina Almeira
* 3 - 5026231148 - Tiara Aulia Azadirachta Indica
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // Define named constants for UI sizes
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
    // Board width/height in pixels

    // Define properties
    /** The game board composes of 9x9 Cells (customized JTextFields) */
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    /** It also contains a Puzzle with array numbers and isGiven */
    private Puzzle puzzle = new Puzzle();
    private String currentLevel = "Easy"; //initialize to set level

    /** Constructor */
    public GameBoardPanel() {
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));  // JPanel

        // Allocate the 2D array of Cell, and added into JPanel.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);   // JPanel
            }
        }

        // [TODO 3] Allocate a common listener as the ActionEvent listener for all the
        //  Cells (JTextFields)
        // [TODO 3]
        CellInputListener listener = new CellInputListener();


        // [TODO 4] Adds this common listener to all editable cells
        // [TODO 4]
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);   // For all editable rows and cols
                }
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    public void setLevel(String level) { //method to set level
        this.currentLevel = level;
    }

    /**
     * Generate a new puzzle; and reset the game board of cells based on the puzzle.
     * You can call this method to start a new game.
     */
    public void newGame() {
        // Generate a new puzzle
        puzzle.newPuzzle(currentLevel);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
                cells[row][col].setHighlighted(false); // Reset highlight
            }
        }
    }

    /**
     * Return true if the puzzle is solved
     * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
     */
    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    // ADD FITUR UNTUK MENYEMPURNAAN GUI GRID 
    @Override
    protected void paintComponent(Graphics g) { // Membuat grid 3x3
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Garis tipis untuk grid utama
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        for (int i = 1; i < SudokuConstants.GRID_SIZE; i++) {
            int linePos = i * CELL_SIZE;
            g2d.drawLine(0, linePos, BOARD_WIDTH, linePos); // Horizontal
            g2d.drawLine(linePos, 0, linePos, BOARD_HEIGHT); // Vertical
        }

        // Garis tebal untuk subgrid 3x3
        g2d.setStroke(new BasicStroke(3));
        for (int i = 0; i <= SudokuConstants.SUBGRID_SIZE; i++) {
            int linePos = i * SudokuConstants.SUBGRID_SIZE * CELL_SIZE;
            g2d.drawLine(0, linePos, BOARD_WIDTH, linePos); // Horizontal
            g2d.drawLine(linePos, 0, linePos, BOARD_HEIGHT); // Vertical
        }
    }

    // [TODO 2] Define a Listener Inner Class for all the editable Cells
   private class CellInputListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
       // Get a reference of the JTextField that triggers this action event
       Cell sourceCell = (Cell)e.getSource();
       try {
        int numberIn = Integer.parseInt(sourceCell.getText()); // Get the number entered

        // Debugging line to print the entered number
        System.out.println("You entered " + numberIn);

        /*
        * [TODO 5] (later - after TODO 3 and 4)
        * Check the numberIn against sourceCell.number.
        * Update the cell status sourceCell.status,
        * and re-paint the cell via sourceCell.paint().
        */
        if (numberIn == sourceCell.number) {
            sourceCell.status = CellStatus.CORRECT_GUESS;
            sourceCell.setBackground(Color.GREEN);  // Set background to green for correct guess
            sourceCell.setForeground(Color.BLACK);  // Set text color to black
        } else {
            sourceCell.status = CellStatus.WRONG_GUESS;
            sourceCell.setBackground(Color.RED);  // Set background to red for wrong guess
            sourceCell.setForeground(Color.WHITE);  // Set text color to white
        }
        sourceCell.paint(); // Re-paint the cell based on its updated status

        // Highlight all cells with the same number (except the input cell itself)
        highlightCells(numberIn, sourceCell);
        
        /*
          * [TODO 6] (later)
          * Check if the player has solved the puzzle after this move,
          *   by calling isSolved(). Put up a congratulation JOptionPane, if so.
          Common colors are defined via constants such as Color.RED, Color.GREEN, Color.BLUE, and etc (But don't use these ugly colors. Design a color theme).
          */
           /* setBackground(Color c)  // Set the background color of the component
            setForeground(Color c)  // Set the text color of the JTextField
            setFont(Font f)         // Set the font used by the JTextField
            setHorizontalAlignment(int align);  // align: JTextField.CENTER, JTextField.LEFT, JTextField.RIGHT
            isEditable():boolean
            setEditable(boolean b) */
            // [TODO 6]
        // Check if the puzzle is solved
        if (isSolved()) {
            JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle.");
        } 
        } catch (NumberFormatException ex) {
            // Handle invalid input (non-numeric input)
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            }          
        }
    }

    // Method untuk memberikan highlight sesuai aturan
    private void highlightCells(int number, Cell excludeCell) {
        // Reset semua highlight
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].setHighlighted(false); // Reset highlight
                cells[row][col].paint(); // Redraw untuk menghapus highlight
            }
        }
    
        // Highlight sel dengan angka yang sama, kecuali sel yang diinput
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                Cell cell = cells[row][col];
                if (!cell.getText().isEmpty() && cell != excludeCell) {
                    try {
                        int cellValue = Integer.parseInt(cell.getText());
                        if (cellValue == number) { // Jika angka cocok
                            cell.setHighlighted(true); // Set highlight
                            cell.paint(); // Highlight sel
                        }
                    } catch (NumberFormatException e) {
                        // Abaikan jika input tidak valid (tidak seharusnya terjadi)
                    }
                }
            }
        }
    }
}