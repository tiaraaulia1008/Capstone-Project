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

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L; // to prevent serial warning

    // Panels
    private GameBoardPanel board = new GameBoardPanel();
    private JPanel levelSelectionPanel = new JPanel();

    // Buttons
    private JButton btnNewGame = new JButton("New Game");
    private JButton btnHint = new JButton("Hint");
    private JButton btnCheat = new JButton("Cheat");
    private JButton darkThmBtn = new JButton("Dark Theme");
    private JButton pastelThmBtn = new JButton("Pastel Theme");
    private JButton peachThmBtn = new JButton("Peach Theme");
    private JButton mintThmBtn = new JButton("Mint Theme");

    // Constructor
    public SudokuMain() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        // Set up level selection panel
        levelSelectionPanel.setLayout(new GridLayout(3, 1, 10, 10));
        levelSelectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnEasy = new JButton("Easy");
        JButton btnMedium = new JButton("Medium");
        JButton btnHard = new JButton("Hard");

        levelSelectionPanel.add(btnEasy);
        levelSelectionPanel.add(btnMedium);
        levelSelectionPanel.add(btnHard);

        cp.add(levelSelectionPanel, BorderLayout.CENTER);

        JPanel thmPanel = new JPanel();
        thmPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        thmPanel.add(darkThmBtn);
        thmPanel.add(pastelThmBtn);
        thmPanel.add(peachThmBtn);
        thmPanel.add(mintThmBtn);

        cp.add(thmPanel, BorderLayout.NORTH);

        // Add action listeners for level selection
        btnEasy.addActionListener(e -> startGame(1));
        btnMedium.addActionListener(e -> startGame(2));
        btnHard.addActionListener(e -> startGame(3));

        // Set theme icons for buttons
        darkThmBtn.setIcon(new ImageIcon("path_to_dark_theme_icon.png"));
        pastelThmBtn.setIcon(new ImageIcon("path_to_pastel_theme_icon.png"));
        peachThmBtn.setIcon(new ImageIcon("path_to_peach_theme_icon.png"));
        mintThmBtn.setIcon(new ImageIcon("path_to_mint_theme_icon.png"));

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }

    /**
     * Start the game based on the selected difficulty level
     */
    private void startGame(int difficultyLevel) {
        // Remove level selection panel and add game board
        getContentPane().remove(levelSelectionPanel);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.add(btnNewGame);
        southPanel.add(btnHint);
        southPanel.add(btnCheat);

        cp.add(board, BorderLayout.CENTER);
        cp.add(southPanel, BorderLayout.SOUTH);

        // Add action listeners for game controls
        btnNewGame.addActionListener(e -> resetToLevelSelection());
        btnHint.addActionListener(e -> board.giveHint());
        btnCheat.addActionListener(e -> board.showCheat());

        // Start the game with the selected difficulty level
        board.newGame(difficultyLevel);

        revalidate();
        repaint();
    }

    /**
     * Reset to level selection
     */
    private void resetToLevelSelection() {
        getContentPane().removeAll();
        getContentPane().add(levelSelectionPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /** The entry main() entry method */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SudokuMain());
    }
}
