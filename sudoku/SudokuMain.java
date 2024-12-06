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
import javax.swing.*;
/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // private variables
   GameBoardPanel board = new GameBoardPanel(this); // 'this' adalah referensi ke SudokuMain
   JButton btnNewGame = new JButton("New Game");
   JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
   JButton pauseButton = new JButton("Pause");
   JButton resumeButton = new JButton("Resume");
   JPanel buttonPanel = new JPanel(new FlowLayout());
   JComboBox<String> levelSelector = new JComboBox<>(new String[]{"Easy", "Intermediate", "Difficult"}); //set level
   private Timer timer; // Timer untuk menghitung waktu
   private int elapsedSeconds = 0; // Total waktu berjalan
   private boolean isPaused = false; // Status apakah timer sedang berhenti
   private boolean gamePaused = false;
   private String playerName = "Player"; // Default player name
    private int highScore = Integer.MAX_VALUE; // Default high score
   private JLabel statusBar = new JLabel("Welcome to Sudoku!"); // Status bar
   private JLabel timerLabel = new JLabel("Time: 00:00:00 "); // Timer label
   private JLabel highScoreLabel = new JLabel("Highscore: --:--:--");

   public SudokuMain() {
    Container cp = getContentPane();
    cp.setLayout(new BorderLayout());

    // Show Welcome Screen
    showWelcomeScreen();

    // Game Board in center
    cp.add(board, BorderLayout.CENTER);

    // Menu Bar setup
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem newGameItem = new JMenuItem("New Game");
    JMenuItem resetGameItem = new JMenuItem("Reset Game");
    fileMenu.add(newGameItem);
    fileMenu.add(resetGameItem);
    menuBar.add(fileMenu);

    JMenu optionsMenu = new JMenu("Options");
    JMenuItem difficultyItem = new JMenuItem("Set Difficulty");
    optionsMenu.add(difficultyItem);
    menuBar.add(optionsMenu);

    JMenu helpMenu = new JMenu("Help");
    JMenuItem aboutItem = new JMenuItem("About");
    helpMenu.add(aboutItem);
    menuBar.add(helpMenu);

    setJMenuBar(menuBar);

    // Timer panel setup
    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    timerLabel.setHorizontalAlignment(SwingConstants.LEFT);
    topPanel.add(timerLabel);
    topPanel.add(pauseButton);  // Add Pause button next to timer label
    cp.add(topPanel, BorderLayout.NORTH);

    // Control panel setup
    JPanel controlPanel = new JPanel(new FlowLayout());
    levelSelector.addActionListener(e -> {
        String selectedLevel = (String) levelSelector.getSelectedItem();
        board.setLevel(selectedLevel);
        board.newGame();
        updateStatusBar();
    });

    btnNewGame.addActionListener(e -> {
        board.newGame();
        updateStatusBar();
        resetTimer();
    });

    controlPanel.add(new JLabel("Select Level:"));
    controlPanel.add(levelSelector);
    controlPanel.add(btnNewGame);

    // Highscore panel
    JPanel highScorePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    highScorePanel.add(highScoreLabel);
    cp.add(highScorePanel, BorderLayout.EAST);

    // Combine status bar and control panel
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(controlPanel, BorderLayout.CENTER);

    statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    bottomPanel.add(statusBar, BorderLayout.SOUTH);

    cp.add(bottomPanel, BorderLayout.SOUTH);

    // Timer logic
    timer = new Timer(1000, e -> {
        if (!isPaused) {
            elapsedSeconds++;
            timerLabel.setText("Time: " + formatTime(elapsedSeconds));
        }
    });
    timer.start();

    // Pause button logic
    pauseButton.addActionListener(e -> handlePause());

    // Initialize the game board
    board.newGame();

    pack();
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            int confirm = JOptionPane.showOptionDialog(SudokuMain.this, 
                    "Are you sure you want to exit?", 
                    "Exit Confirmation", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    new Object[]{"Yes", "No"}, 
                    "No");
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    });

    setTitle("Sudoku " + playerName);
    setVisible(true);
}

private void handlePause() {
    isPaused = true;
    gamePaused = true;
    statusBar.setText("Game paused.");

    // Create the option pane for "Resume the game?"
    JOptionPane optionPane = new JOptionPane(
        "Resume the game?", 
        JOptionPane.QUESTION_MESSAGE, 
        JOptionPane.YES_NO_OPTION
    );

    // Create a JDialog for "Resume the game?"
    JDialog dialog = optionPane.createDialog(this, "Game Paused");

    // Add a WindowListener to handle when the dialog is closed via the "X"
    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            handlePause();
        }
    });

    dialog.setVisible(true);

    int resumeOption = (int) optionPane.getValue();
    if (resumeOption == JOptionPane.YES_OPTION) {
        isPaused = false;
        if (gamePaused) {
            statusBar.setText("Game resumed.");
        }
    } else {
        int exitOption = JOptionPane.showConfirmDialog(
                this,
                "Do you want to leave the game?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (exitOption == JOptionPane.YES_OPTION) {
            System.exit(0);
        } else {
            handlePause();
        }
    }
}

private void startNewGame() {
    board.newGame();
    elapsedSeconds = 0;
    timerLabel.setText("Time: 00:00:00");
    timer.restart();
    updateStatusBar();
}

private void updateStatusBar() {
    statusBar.setText(playerName + "'s Sudoku - Level: " + board.getLevel());
}

private void resetTimer() {
    elapsedSeconds = 0;
    timerLabel.setText("Time: 00:00:00");
}

private String formatTime(int totalSeconds) {
    int hours = totalSeconds / 3600;
    int minutes = (totalSeconds % 3600) / 60;
    int seconds = totalSeconds % 60;
    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
}

private void showDifficultyDialog() {
    String[] options = {"Easy", "Intermediate", "Difficult"};
    int choice = JOptionPane.showOptionDialog(this, 
        "Select Difficulty", 
        "Difficulty", 
        JOptionPane.DEFAULT_OPTION, 
        JOptionPane.INFORMATION_MESSAGE, 
        null, 
        options, 
        options[0]);

    if (choice != -1) {
        String selectedLevel = options[choice];
        board.setLevel(selectedLevel);
        board.newGame();
        updateStatusBar();
    }
}

private void updateHighScore() {
    if (elapsedSeconds < highScore) {
        highScore = elapsedSeconds;
        highScoreLabel.setText("Highscore: " + formatTime(highScore));
        JOptionPane.showMessageDialog(this, 
            "Congratulations!\nYou set a new high score: " + formatTime(highScore), 
            "New High Score", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}

public void checkGameComplete() {
    if (board.isSolved()) {
        timer.stop();
        JOptionPane.showMessageDialog(this, 
            "Congratulations! You solved the puzzle in " + formatTime(elapsedSeconds), 
            "Puzzle Solved", 
            JOptionPane.INFORMATION_MESSAGE);
        updateHighScore();

        int choice = JOptionPane.showConfirmDialog(this, 
            "Would you like to start a new game?", 
            "Start New Game?", 
            JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            startNewGame();
        }
    }
}

private void showAboutDialog() {
    JOptionPane.showMessageDialog(this, 
        "Sudoku Game\nCreated by [Your Name]", 
        "About", 
        JOptionPane.INFORMATION_MESSAGE);
}

private void showWelcomeScreen() {
    JDialog welcomeDialog = new JDialog(this, "Welcome to Sudoku!", true);
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JLabel labelName = new JLabel("Enter your name: ");
    JTextField nameField = new JTextField(20);
    JButton startButton = new JButton("Start Game");

    startButton.addActionListener(e -> {
        playerName = nameField.getText().trim().isEmpty() ? "Player" : nameField.getText();
        statusBar.setText(playerName + "'s Sudoku - Level: " + board.getLevel());
        welcomeDialog.dispose();
    });

    panel.add(labelName);
    panel.add(nameField);
    panel.add(startButton);

    welcomeDialog.add(panel);
    welcomeDialog.pack();
    welcomeDialog.setLocationRelativeTo(this);
    welcomeDialog.setVisible(true);
}

public static void main(String[] args) {
    SwingUtilities.invokeLater(()-> new SudokuMain());
}
}