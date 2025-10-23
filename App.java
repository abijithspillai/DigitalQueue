import gui.MainFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Main entry point for the application.
 * This class asks the user to select a mode (Bank or Hospital)
 * and then launches the main GUI.
 */
public class App {
    
    public static void main(String[] args) {
        
        // 1. Create options for the mode selection dialog
        Object[] options = {"Bank", "Hospital"};
        
        // 2. Show the pop-up dialog
        int choice = JOptionPane.showOptionDialog(null,
                "Please select the system mode:",
                "Select Mode",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, 
                options, 
                options[0]);

        // 3. Determine the mode from the user's choice
        String mode;
        if (choice == 0) {
            mode = "Bank";
        } else if (choice == 1) {
            mode = "Hospital";
        } else {
            // User closed the dialog, so exit the program
            return;
        }

        // 4. Launch the main GUI.
        // SwingUtilities.invokeLater ensures that all UI code runs on the
        // Event Dispatch Thread, which is the standard practice for Swing.
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(mode); // Pass the selected mode to the frame
            frame.setVisible(true);
        });
    }
}