/**
* ES234317-Algorithm and Data Structures
* Semester Ganjil, 2024/2025
* Group Capstone Project
* Group #8
* 1 - 5026231023 - Nadya Luthfiyah Rahma
* 2 - 5026231094 - Davina Almeira
* 3 - 5026231148 - Tiara Aulia Azadirachta Indica
*/

package sudoku;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;

/**
 * The Cell class models the cells of the Sudoku puzzle, by customizing (subclass)
 * the javax.swing.JTextField to include row/column, puzzle number, and status.
 */
public class Cell extends JTextField {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // Define named constants for JTextField's colors and fonts
    // to be chosen based on CellStatus
    public static final Color BG_GIVEN = new Color(240, 240, 240); // RGB
    public static final Color FG_GIVEN = Color.BLACK;
    public static final Color FG_NOT_GIVEN = Color.GRAY;
    public static final Color BG_TO_GUESS = Color.YELLOW;
    public static final Color BG_CORRECT_GUESS = new Color(0, 216, 0);
    public static final Color BG_WRONG_GUESS = new Color(216, 0, 0);
    public static final Font FONT_NUMBERS = new Font("OCR A Extended", Font.PLAIN, 28);

    // Define properties (package-visible)
    /** The row and column number [0-8] of this cell */
    int row, col;
    /** The puzzle number [1-9] for this cell */
    int number;
    /** The status of this cell defined in enum CellStatus */
    CellStatus status;

    /** Constructor */
    public Cell(int row, int col) {
        super();   // JTextField
        this.row = row;
        this.col = col;
        // Inherited from JTextField: Beautify all the cells once for all
        super.setHorizontalAlignment(JTextField.CENTER);
        super.setFont(FONT_NUMBERS);
    }

    /** Reset this cell for a new game, given the puzzle number and isGiven */
    public void newGame(int number, boolean isGiven) {
        this.number = number;
        status = isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;
        paint();    // paint itself
    }

    /** This Cell (JTextField) paints itself based on its status */
    public void paint() {
        Color bgColor = BG_GIVEN;  // Default background color
        Color fgColor = FG_GIVEN;  // Default foreground color

        if (status == CellStatus.GIVEN) {
            super.setText(number + "");
            super.setEditable(false);
        } else if (status == CellStatus.TO_GUESS) {
            super.setText("");
            super.setEditable(true);
            bgColor = BG_TO_GUESS;
            fgColor = FG_NOT_GIVEN;
        } else if (status == CellStatus.CORRECT_GUESS) {
            bgColor = BG_CORRECT_GUESS;
        } else if (status == CellStatus.WRONG_GUESS) {
            bgColor = BG_WRONG_GUESS;
        }

        super.setBackground(bgColor);
        super.setForeground(fgColor);
    }


    public void setTheme(Color bgGiven, Color fgGiven, Color bgToGuess, Color fgNotGiven) {
        if (status == CellStatus.GIVEN) {
            super.setBackground(bgGiven);
            super.setForeground(fgGiven);
        } else if (status == CellStatus.TO_GUESS) {
            super.setBackground(bgToGuess);
            super.setForeground(fgNotGiven);
        }
    }
        public void applyTheme(Color bgGiven, Color fgGiven, Color bgToGuess, Color fgNotGiven) {
            if (this.isEditable()) {
                setBackground(bgToGuess);
                setForeground(fgNotGiven);
            } else {
                setBackground(bgGiven);
                setForeground(fgGiven);
            }
        }
}
