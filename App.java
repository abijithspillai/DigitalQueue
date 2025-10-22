import gui.MainFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        
        Object[] options = {"Bank", "Hospital"};
        
        int choice = JOptionPane.showOptionDialog(null,
                "Please select the system mode:",
                "Select Mode",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, 
                options, 
                options[0]);

        String mode;
        if (choice == 0) {
            mode = "Bank";
        } else if (choice == 1) {
            mode = "Hospital";
        } else {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(mode);
            frame.setVisible(true);
        });
    }
}