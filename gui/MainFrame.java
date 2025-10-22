package gui;

import ds.MyLinkedList;
import exceptions.EmptyQueueException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import logic.QueueManager;
import model.BankCustomer;
import model.Patient;
import model.Person;
import model.Token;

public class MainFrame extends JFrame {

    private QueueManager manager;
    private String appMode;

    private JLabel nowServingLabel;
    private JLabel nextTokenLabel;
    private JTextArea historyArea;
    private JList<Token> waitingQueueList;
    private DefaultListModel<Token> waitingListModel;
    
    private JTextField customerNameField, customerIdField, customerDetailField;
    private JButton serveNextButton;

    public MainFrame(String mode) {
        this.appMode = mode;
        
        loadData(); 
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveData();
                System.exit(0);
            }
        });

        if (appMode.equals("Bank")) {
            setTitle("Bank Queue Management System");
        } else {
            setTitle("Hospital Queue Management System");
        }
        
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createInputPanel(), BorderLayout.WEST);
        add(createDataDisplayPanel(), BorderLayout.CENTER);

        updateDisplay(); 
    }

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

    private JPanel createInputPanel() {
        JPanel mainInputPanel = new JPanel(new BorderLayout(10, 10));
        mainInputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Generate Token"));
        
        formPanel.add(new JLabel("Name:"));
        customerNameField = new JTextField();
        formPanel.add(customerNameField);

        formPanel.add(new JLabel("ID / Phone:"));
        customerIdField = new JTextField();
        formPanel.add(customerIdField);

        String detailLabel = appMode.equals("Bank") ? "Service Type:" : "Ailment:";
        formPanel.add(new JLabel(detailLabel));
        customerDetailField = new JTextField();
        formPanel.add(customerDetailField);

        JButton generateButton = new JButton("Generate New Token");
        generateButton.addActionListener(e -> generateToken());

        serveNextButton = new JButton("Serve Next Token");
        serveNextButton.setFont(new Font("Arial", Font.BOLD, 18));
        serveNextButton.setBackground(new Color(40, 167, 69));
        serveNextButton.setForeground(Color.WHITE);
        serveNextButton.addActionListener(e -> serveNext());

        JButton resetButton = new JButton("Reset Data / New Day");
        resetButton.setBackground(new Color(220, 53, 69));
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(e -> resetData());

        JPanel southPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        southPanel.add(serveNextButton);
        southPanel.add(resetButton);

        mainInputPanel.add(formPanel, BorderLayout.NORTH);
        mainInputPanel.add(generateButton, BorderLayout.CENTER);
        mainInputPanel.add(southPanel, BorderLayout.SOUTH);

        return mainInputPanel;
    }

    private JSplitPane createDataDisplayPanel() {
        waitingListModel = new DefaultListModel<>();
        waitingQueueList = new JList<>(waitingListModel);
        
        JScrollPane waitingScrollPane = new JScrollPane(waitingQueueList);
        waitingScrollPane.setBorder(BorderFactory.createTitledBorder("Waiting in Queue"));

        historyArea = new JTextArea();
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        historyArea.setEditable(false);
        historyArea.setBorder(BorderFactory.createTitledBorder("Served History"));
        JScrollPane historyScrollPane = new JScrollPane(historyArea);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, waitingScrollPane, historyScrollPane);
        splitPane.setDividerLocation(200);
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        return splitPane;
    }

    private void generateToken() {
        String name = customerNameField.getText();
        String id = customerIdField.getText();
        String detail = customerDetailField.getText();

        if (name.isEmpty() || id.isEmpty() || detail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Person person;
        if (appMode.equals("Bank")) {
            person = new BankCustomer(name, id, detail);
        } else {
            person = new Patient(name, id, detail);
        }

        Token newToken = manager.generateNewToken(person);
        JOptionPane.showMessageDialog(this, "Token Generated: " + newToken.getTokenNumber(), "Success", JOptionPane.INFORMATION_MESSAGE);
        
        clearInputFields();
        updateDisplay();
    }

    private void serveNext() {
        try {
            Token servingToken = manager.serveNextToken();
            nowServingLabel.setText(String.valueOf(servingToken.getTokenNumber()));
            updateDisplay();
        } catch (EmptyQueueException ex) {
            JOptionPane.showMessageDialog(this, "No customers are currently waiting.", "Queue Empty", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void resetData() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to clear all data and start a new day?\nThis cannot be undone.",
                "Confirm Reset",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            manager = new QueueManager();
            saveData();
            updateDisplay();
            nowServingLabel.setText("--");
            JOptionPane.showMessageDialog(this, "Data has been reset. Ready for a new day.", "Reset Complete", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateDisplay() {
        nextTokenLabel.setText(manager.getNextTokenInQueue());

        waitingListModel.clear();
        MyLinkedList<Token> waiting = manager.getWaitingList();
        for (Token token : waiting) {
            waitingListModel.addElement(token);
        }
        
        historyArea.setText("");
        MyLinkedList<Token> history = manager.getHistoryList();
        for (Token token : history) {
            historyArea.append(token.toString() + "\n");
        }
    }

    private void clearInputFields() {
        customerNameField.setText("");
        customerIdField.setText("");
        customerDetailField.setText("");
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(appMode + "_queue.dat"))) {
            oos.writeObject(manager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(appMode + "_queue.dat"))) {
            manager = (QueueManager) ois.readObject();
        } catch (Exception e) {
            manager = new QueueManager(); 
        }
    }
}