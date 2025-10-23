package gui;

// DS and Logic imports
import ds.MyLinkedList;
import exceptions.EmptyQueueException;
import logic.QueueManager;
import model.BankCustomer;
import model.Patient;
import model.Person;
import model.Token;

// Swing and AWT imports for GUI
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

// File I/O imports for Serialization
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The main application window (GUI).
 * This class extends JFrame and is responsible for all user interaction.
 * It talks to the QueueManager to perform all logic.
 */
public class MainFrame extends JFrame {

    // The single instance of the "Brain"
    private QueueManager manager;
    // Stores whether we are in "Bank" or "Hospital" mode
    private String appMode;

    // --- Swing Components ---
    private JLabel nowServingLabel;
    private JLabel nextTokenLabel;
    private JTextArea historyArea;
    private JList<Token> waitingQueueList;
    private DefaultListModel<Token> waitingListModel;
    private JTextField customerNameField, customerIdField, customerDetailField;

    /**
     * Constructor for the MainFrame.
     * @param mode The selected mode ("Bank" or "Hospital").
     */
    public MainFrame(String mode) {
        this.appMode = mode;
        
        // 1. Load data from file *before* building the GUI
        loadData(); 
        
        // 2. Set up the window closing event to save data
        // We use a WindowAdapter for cleaner code
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveData(); // Save on close
                System.exit(0); // Then exit
            }
        });

        // 3. Configure the main window properties
        if (appMode.equals("Bank")) {
            setTitle("Bank Queue Management System");
        } else {
            setTitle("Hospital Queue Management System");
        }
        
        setSize(800, 600);
        // This is crucial: we handle the close operation manually
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout(10, 10));

        // 4. Build the GUI panels
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createInputPanel(), BorderLayout.WEST);
        add(createDataDisplayPanel(), BorderLayout.CENTER);

        // 5. Refresh the display with the loaded data
        updateDisplay(); 
    }

    /**
     * Creates the top panel with "Now Serving" and "Next Token" labels.
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nowServingLabel = new JLabel("--", SwingConstants.CENTER);
        nowServingLabel.setFont(new Font("Arial", Font.BOLD, 48));
        nowServingLabel.setBorder(BorderFactory.createTitledBorder("Now Serving"));
        nowServingLabel.setOpaque(true);
        nowServingLabel.setBackground(Color.WHITE);

        nextTokenLabel = new JLabel("--", SwingConstants.CENTER);
        nextTokenLabel.setFont(new Font("Arial", Font.BOLD, 48));
        nextTokenLabel.setBorder(BorderFactory.createTitledBorder("Next Token"));
        
        headerPanel.add(nowServingLabel);
        headerPanel.add(nextTokenLabel);
        
        return headerPanel;
    }

    /**
     * Creates the left-side panel for user input and actions.
     */
    private JPanel createInputPanel() {
        // Use BorderLayout for the main input panel
        JPanel mainInputPanel = new JPanel(new BorderLayout(10, 10));
        mainInputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // A sub-panel for the text fields
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Generate Token"));
        
        formPanel.add(new JLabel("Name:"));
        customerNameField = new JTextField();
        formPanel.add(customerNameField);

        formPanel.add(new JLabel("ID / Phone:"));
        customerIdField = new JTextField();
        formPanel.add(customerIdField);

        // Dynamically set the label based on the appMode
        String detailLabel = appMode.equals("Bank") ? "Service Type:" : "Ailment:";
        formPanel.add(new JLabel(detailLabel));
        customerDetailField = new JTextField();
        formPanel.add(customerDetailField);

        // Button to generate a new token
        JButton generateButton = new JButton("Generate New Token");
        generateButton.addActionListener(e -> generateToken());

        // Button to serve the next token
        JButton serveNextButton = new JButton("Serve Next Token");
        serveNextButton.setFont(new Font("Arial", Font.BOLD, 18));
        serveNextButton.setBackground(new Color(40, 167, 69)); // Green
        serveNextButton.setForeground(Color.WHITE);
        serveNextButton.addActionListener(e -> serveNext());

        // Button to reset all data
        JButton resetButton = new JButton("Reset Data / New Day");
        resetButton.setBackground(new Color(220, 53, 69)); // Red
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(e -> resetData());

        // A sub-panel for the action buttons at the bottom
        JPanel southPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        southPanel.add(serveNextButton);
        southPanel.add(resetButton);

        // Add sub-panels to the main input panel
        mainInputPanel.add(formPanel, BorderLayout.NORTH);
        mainInputPanel.add(generateButton, BorderLayout.CENTER);
        mainInputPanel.add(southPanel, BorderLayout.SOUTH);

        return mainInputPanel;
    }

    /**
     * Creates the center panel with the "Waiting" and "History" lists.
     * Uses a JSplitPane to make them resizable.
     */
    private JSplitPane createDataDisplayPanel() {
        // The waiting list uses a JList with a DefaultListModel
        waitingListModel = new DefaultListModel<>();
        waitingQueueList = new JList<>(waitingListModel);
        
        JScrollPane waitingScrollPane = new JScrollPane(waitingQueueList);
        waitingScrollPane.setBorder(BorderFactory.createTitledBorder("Waiting in Queue"));

        // The history list just uses a non-editable JTextArea
        historyArea = new JTextArea();
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        historyArea.setEditable(false);
        historyArea.setBorder(BorderFactory.createTitledBorder("Served History"));
        JScrollPane historyScrollPane = new JScrollPane(historyArea);
        
        // JSplitPane divides the panel into two resizable sections
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, waitingScrollPane, historyScrollPane);
        splitPane.setDividerLocation(200); // Set initial split
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        return splitPane;
    }

    /**
     * Action handler for the "Generate New Token" button.
     * Reads form data, tells the manager to create a token, and updates the display.
     */
    private void generateToken() {
        String name = customerNameField.getText();
        String id = customerIdField.getText();
        String detail = customerDetailField.getText();

        // Simple input validation
        if (name.isEmpty() || id.isEmpty() || detail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create the correct Person object based on the appMode
        Person person;
        if (appMode.equals("Bank")) {
            person = new BankCustomer(name, id, detail);
        } else {
            person = new Patient(name, id, detail);
        }

        // Tell the "Brain" to create the token
        Token newToken = manager.generateNewToken(person);
        JOptionPane.showMessageDialog(this, "Token Generated: " + newToken.getTokenNumber(), "Success", JOptionPane.INFORMATION_MESSAGE);
        
        clearInputFields();
        updateDisplay(); // Refresh the screen
    }

    /**
     * Action handler for the "Serve Next Token" button.
     * This is where we use our try-catch block for the custom exception.
     */
    private void serveNext() {
        try {
            // Tell the "Brain" to serve a token
            Token servingToken = manager.serveNextToken();
            nowServingLabel.setText(String.valueOf(servingToken.getTokenNumber()));
            updateDisplay(); // Refresh the screen
        } catch (EmptyQueueException ex) {
            // This code runs *only* if the manager throws our custom exception
            JOptionPane.showMessageDialog(this, "No customers are currently waiting.", "Queue Empty", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Action handler for the "Reset Data" button.
     * Asks for confirmation before wiping all data.
     */
    private void resetData() {
        // Show a confirmation dialog
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to clear all data and start a new day?\nThis cannot be undone.",
                "Confirm Reset",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            // User confirmed. Create a new, empty "Brain".
            manager = new QueueManager();
            // Overwrite the save file with this new empty data.
            saveData();
            // Refresh the screen
            updateDisplay();
            nowServingLabel.setText("--");
            JOptionPane.showMessageDialog(this, "Data has been reset. Ready for a new day.", "Reset Complete", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * This is the "refresh" method. It updates all lists and labels
     * with the current data from the QueueManager.
     */
    private void updateDisplay() {
        // Update the "Next Token" label
        nextTokenLabel.setText(manager.getNextTokenInQueue());

        // Update the "Waiting in Queue" list
        waitingListModel.clear();
        MyLinkedList<Token> waiting = manager.getWaitingList();
        // This for-each loop uses our custom list's iterator (Traversal)
        for (Token token : waiting) {
            waitingListModel.addElement(token);
        }
        
        // Update the "Served History" text area
        historyArea.setText("");
        MyLinkedList<Token> history = manager.getHistoryList();
        // This for-each loop is also a traversal
        for (Token token : history) {
            historyArea.append(token.toString() + "\n");
        }
    }

    /**
     * A helper method to clear the input fields after token generation.
     */
    private void clearInputFields() {
        customerNameField.setText("");
        customerIdField.setText("");
        customerDetailField.setText("");
    }

    /**
     * Saves the *entire* QueueManager object to a file using Serialization.
     *
     */
    private void saveData() {
        // The filename is based on the appMode (e.g., "Bank_queue.dat")
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(appMode + "_queue.dat"))) {
            oos.writeObject(manager);
        } catch (Exception e) {
            e.printStackTrace(); // Print error to console if saving fails
        }
    }

    /**
     * Loads the QueueManager object from a file using Deserialization.
     *
     */
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(appMode + "_queue.dat"))) {
            // Read the object and cast it back to QueueManager
            manager = (QueueManager) ois.readObject();
        } catch (Exception e) {
            // If file not found or any other error, just create a new manager
            manager = new QueueManager(); 
        }
    }
}