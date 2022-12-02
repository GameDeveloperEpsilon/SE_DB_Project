import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GUI extends JFrame {
    DBHandler dbHandler;
    JScrollPane scrollPane;
    JTextArea output;
    JComboBox<String> actionSelector;
    String actionToExecute = "SELECT";

    public GUI(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
        windowSetup();
        createComponents();
        setVisible(true);
    }

    private void windowSetup() {
        setTitle("PT Database GUI");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 500);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void createComponents() {
        createColumnLabels();
        createOutputArea();
        createControlBar();
    }

    private void createColumnLabels() {
        JLabel attributeNames = new JLabel("Row | Subject Prefix | Course Number | Section Number |" +
                " Type of Instruction | Semester Credit Hours | Total Enrollment");
        JScrollPane attributeNamePane = new JScrollPane(attributeNames);
        attributeNamePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        getContentPane().add(BorderLayout.NORTH, attributeNamePane);
    }

    private void createOutputArea() {
        output = new JTextArea();
        output.setEditable(false);

        scrollPane = new JScrollPane(output);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        getContentPane().add(BorderLayout.CENTER, scrollPane);
    }

    private void createControlBar() {

        String[] actions = {"SELECT", "INSERT", "UPDATE", "DELETE"};
        actionSelector = new JComboBox<>(actions);
        actionSelector.addItemListener(e -> {

            // if the state combobox is changed
            if (e.getSource() == actionSelector) {
                if (Objects.equals(actionSelector.getSelectedItem(), "SELECT")) {
                    //JOptionPane.showMessageDialog(this, "Selecting");
                    actionToExecute = "SELECT";
                }
                else if (Objects.equals(actionSelector.getSelectedItem(), "INSERT")) {
                    //JOptionPane.showMessageDialog(this, "Inserting");
                    actionToExecute = "INSERT";
                }
                else if (Objects.equals(actionSelector.getSelectedItem(), "UPDATE")) {
                    //JOptionPane.showMessageDialog(this, "Updating");
                    actionToExecute = "UPDATE";
                }
                else if (Objects.equals(actionSelector.getSelectedItem(), "DELETE")) {
                    //JOptionPane.showMessageDialog(this, "Deleting");
                    actionToExecute = "DELETE";
                }
                else {
                    JOptionPane.showMessageDialog(this, "Unknown Action");
                    actionToExecute = "SELECT";
                }
            }
        });

        JPanel inputPanel = new JPanel();
        JLabel prompt = new JLabel("Enter CRN: ");
        JTextField CRNField = new JTextField(10);
        JButton submit = new JButton("Execute");
        submit.addActionListener(e -> {
            int CRN = -1;  // Default Value
            try {
                 CRN = Integer.parseInt(CRNField.getText());
                 if (String.valueOf(CRN).length() != 5) {
                     return;
                 }
            } catch (NumberFormatException ignored) {}

            switch (actionToExecute) {
                case "SELECT" -> output.setText(dbHandler.selectFromTableSpring(this, CRN));
                case "INSERT" -> output.setText(dbHandler.insertRecordIntoTableSpring(this, CRN));
                case "UPDATE" -> output.setText(dbHandler.updateRecordInTableSpring(this, CRN));
                case "DELETE" -> output.setText(dbHandler.deleteRecordInTableSpring(this, CRN));
            }

        });
        JButton editConnection = new JButton("Edit Connection");
        editConnection.addActionListener(e -> dbHandler.userFile.populateUserFile());
        inputPanel.add(actionSelector);
        inputPanel.add(prompt);
        inputPanel.add(CRNField);
        inputPanel.add(submit);
        inputPanel.add(editConnection);
        getContentPane().add(BorderLayout.SOUTH, inputPanel);
    }

    public void setDbHandler(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

}
