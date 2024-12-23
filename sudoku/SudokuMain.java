
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
        thmPanel.add(peachThmBtn);
        thmPanel.add(mintThmBtn);

        cp.add(thmPanel, BorderLayout.NORTH);

        // Add action listeners for level selection
        btnEasy.addActionListener(e -> startGame(1));
        btnMedium.addActionListener(e -> startGame(2));
        btnHard.addActionListener(e -> startGame(3));

        // Inside your ActionListener for theme buttons:
        peachThmBtn.addActionListener(e -> {
            System.out.println("Peach Theme Selected");
            board.applyTheme("Peach Theme");
            board.revalidate();   // Force the board to update
            board.repaint();      // Force the board to refresh visually
        });

        mintThmBtn.addActionListener(e -> {
            System.out.println("Mint Theme Selected");
            board.applyTheme("Mint Theme");
            board.revalidate();   // Force the board to update
            board.repaint();      // Force the board to refresh visually
        });
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

        // Add the theme panel to the top again
        JPanel thmPanel = new JPanel();
        thmPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        thmPanel.add(peachThmBtn);
        thmPanel.add(mintThmBtn);
        cp.add(thmPanel, BorderLayout.NORTH); // Keep the theme buttons at the top

        // Add game board
        cp.add(board, BorderLayout.CENTER);

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
