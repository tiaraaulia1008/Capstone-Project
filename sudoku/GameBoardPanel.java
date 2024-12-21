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
    private String currentLevel = "Easy";

    private SudokuMain mainFrame; // Referensi ke frame utama

    /** Constructor */
    public GameBoardPanel(SudokuMain mainFrame) { 
        this.mainFrame = mainFrame; 
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
        CellInputListener listener = new CellInputListener();

        // [TODO 4] Adds this common listener to all editable cells
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0 ; col < SudokuConstants.GRID_SIZE; col++) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);   // For all editable rows and cols
                }
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    public void setLevel(String level) {
        this.currentLevel = level;
    }

    // Method to get the current game level
    public String getLevel() { //add getter
        return currentLevel;
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

    // [TODO 2] Define a Listener Inner Class for all the editable Cells
    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get a reference of the JTextField that triggers this action event
            Cell sourceCell = (Cell)e.getSource();

            // Retrieve the int entered
            int numberIn;
            try {
                numberIn = Integer.parseInt(sourceCell.getText());
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(GameBoardPanel.this, "Please enter a valid number!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if input is invalid
            }

            // For debugging
            System.out.println("You entered " + numberIn);

            /*
             * [TODO 5] Check the numberIn against sourceCell.number.
             * Update the cell status sourceCell.status,
             * and re-paint the cell via sourceCell.paint().
             */
            if (numberIn == sourceCell.number) {
                sourceCell.status = CellStatus.CORRECT_GUESS;
            } else {
                sourceCell.status = CellStatus.WRONG_GUESS;
            }
            sourceCell.paint(); // re-paint this cell based on its status
            // Highlight semua angka yang sama (kecuali sel yang diinput)
            highlightCells(numberIn, sourceCell);

            /*
             * [TODO 6] Check if the player has solved the puzzle after this move,
             *   by calling isSolved(). Put up a congratulation JOptionPane, if so.
             */
            if (isSolved()) {
                mainFrame.checkGameComplete(); // Beri tahu SudokuMain bahwa game selesai
            }
        }
    }

    public void giveHint(){
        for(int row =0 ; row<SudokuConstants.GRID_SIZE; row++){
            for(int col = 0; col<SudokuConstants.GRID_SIZE; col++){
                if(cells[row][col].status == CellStatus.TO_GUESS){
                    cells[row][col].setText(String.valueOf(puzzle.numbers[row][col]));
                    cells[row][col].status = CellStatus.CORRECT_GUESS;
                    cells[row][col].paint();
                    JOptionPane.showMessageDialog(this, "Hint given in row " + (row + 1) + ", column " + (col + 1), "Hint", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(this, "There's no empty cell to give hint to!", "Hint", JOptionPane.WARNING_MESSAGE);
    }

    public void showCheat(){
        for(int row =0; row<SudokuConstants.GRID_SIZE;row++){
            for(int col = 0; col<SudokuConstants.GRID_SIZE;col++){
                cells[row][col].setText(String.valueOf(puzzle.numbers[row][col]));
                cells[row][col].status = CellStatus.CORRECT_GUESS;
                cells[row][col].paint();

            }
        }
        JOptionPane.showMessageDialog(this, "Cheat activated! all answer is shown.", "Cheat", JOptionPane.INFORMATION_MESSAGE);
    }

    public void applyTheme(String theme) {
        Color bgGiven, fgGiven, bgToGuess, fgNotGiven;

        if (theme.equals("Peach Theme")) {
            bgGiven = new Color(244, 227, 233);  // Light peach background for given cells
            fgGiven = Color.BLACK;
            bgToGuess = new Color(244, 176, 186);  // Pinkish background for guess cells
            fgNotGiven = Color.GRAY;
        } else if (theme.equals("Mint Theme")) {
            bgGiven = new Color(211, 241, 211);  // Light mint background for given cells
            fgGiven = Color.BLACK;
            bgToGuess = new Color(149, 216, 183);  // Minty background for guess cells
            fgNotGiven = Color.GRAY;
        } else {
            // Default theme or fallback colors
            bgGiven = Color.WHITE;
            fgGiven = Color.BLACK;
            bgToGuess = Color.YELLOW;
            fgNotGiven = Color.GRAY;
        }

        // Apply theme to all cells in the board
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                cells[row][col].applyTheme(bgGiven, fgGiven, bgToGuess, fgNotGiven);
            }
        }
        System.out.println("Applying theme: " + theme);

        // Force the panel to revalidate and repaint after applying the theme
        revalidate();  // Ensures the layout is updated
        repaint();     // Redraws the panel with the new theme
    } 

    /* @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon imageIcon = new ImageIcon("path_to_image.jpg"); // Ganti dengan path gambar
        Image image = imageIcon.getImage();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    } */

    @Override
    protected void paintComponent(Graphics g) { // Membuat grid 3x3
        super.paintComponent(g);
        super.paintComponent(g);
        if(backgroundImage != null){
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        
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

        //method image background
        public void loadImage(){
        //menggunakan jalur gambar relatif
        try{
            backgroundImage = new ImageIcon("C:/absolute/path/to/night_bg.jpeg").getImage();
        }catch (Exception e){
            System.out.println("Resource URL: " + getClass().getResource("src/night_bg.jpeg"));
            System.err.println("Failed to load image: " + e.getMessage());
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
