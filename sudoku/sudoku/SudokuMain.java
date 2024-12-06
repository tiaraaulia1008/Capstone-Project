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
/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // private variables
   GameBoardPanel board = new GameBoardPanel();
   JButton btnNewGame = new JButton("New Game");
   JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
   JButton pauseButton = new JButton("Pause");
   JButton resumeButton = new JButton("Resume");
   JPanel buttonPanel = new JPanel(new FlowLayout());
   private Timer timer; // Timer untuk menghitung waktu
   private int elapsedSeconds = 0; // Total waktu berjalan
   private boolean isPaused = false; // Status apakah timer sedang berhenti
   private boolean gamePaused = false;
   private JLabel statusBar = new JLabel("Welcome to Sudoku!"); // Status bar
   private JLabel timerLabel = new JLabel("Time: 00:00:00 "); // Timer label

   // Constructor
   public SudokuMain() {
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());

      cp.add(board, BorderLayout.CENTER);

      // Add a button to the south to re-start the game via board.newGame()
      btnNewGame.addActionListener( e -> board.newGame());
      cp.add(btnNewGame, BorderLayout.SOUTH);

      // Add status bar to bottom
      statusBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      cp.add(statusBar, BorderLayout.SOUTH);

      // Panel for timer and controls (timer + pause button)
      JPanel topPanel = new JPanel();
      topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));  // Align components to the left
      topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Optional padding

      timerLabel.setHorizontalAlignment(SwingConstants.LEFT);
      topPanel.add(timerLabel);

      topPanel.add(pauseButton);  // Add Pause button next to timer label

      cp.add(topPanel, BorderLayout.NORTH);  // Add to the top of the frame

      /// Timer logic
        timer = new Timer(1000, e -> {
            if (!isPaused) {
                elapsedSeconds++;
                timerLabel.setText("Time: " + formatTime(elapsedSeconds));
            }
        });
        timer.start();

        // Pause button logic
        pauseButton.addActionListener(e -> handlePause());

      
      // Initialize the game board to start the game
      board.newGame();

      pack();     // Pack the UI components, instead of using setSize()
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
      setTitle("Sudoku");
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
              // When "X" is clicked, call handlePause() again to show the dialog
              handlePause();
          }
      });
  
      // Show the dialog
      dialog.setVisible(true);
  
      // Get the option selected by the user (Yes or No)
      int resumeOption = (int) optionPane.getValue();
      if (resumeOption == JOptionPane.YES_OPTION) {
          // Resume the game
          isPaused = false;
          if(gamePaused){
          statusBar.setText("Game resumed.");}
      } else {
          // Show "Do you want to leave the game?" dialog
          int exitOption = JOptionPane.showConfirmDialog(
                  this,
                  "Do you want to leave the game?",
                  "Exit Confirmation",
                  JOptionPane.YES_NO_OPTION
          );
  
          if (exitOption == JOptionPane.YES_OPTION) {
              // Exit the game if Yes is selected
              System.exit(0);
          } else {
              // If No is selected, return to the "Resume the game?" dialog
              handlePause(); // Recursively call handlePause() to show the "Resume" dialog again
          }
      }
  }  

    /** Format elapsed time into HH:mm:ss */
    private String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

   /** The entry main() entry method */
   public static void main(String[] args) {
      // [TODO 1] Check "Swing program template" on how to run
      //  the constructor of "SudokuMain"
      SwingUtilities.invokeLater(()-> new SudokuMain());
   }
}