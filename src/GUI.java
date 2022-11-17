import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GUI extends JFrame  {
    DBHandler dbHandler;
    JScrollPane scrollPane;
    JTextArea output;
    JComboBox<String> actionSelector;

    public GUI(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
        setTitle("PT Database GUI");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 500);
        setResizable(false);
        setLocationRelativeTo(null);

        createComponents();

        setVisible(true);
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
                    JOptionPane.showMessageDialog(this, "Selecting");
                }
                else if (Objects.equals(actionSelector.getSelectedItem(), "INSERT")) {
                    JOptionPane.showMessageDialog(this, "Inserting");
                }
                else if (Objects.equals(actionSelector.getSelectedItem(), "UPDATE")) {
                    JOptionPane.showMessageDialog(this, "Updating");
                }
                else if (Objects.equals(actionSelector.getSelectedItem(), "DELETE")) {
                    JOptionPane.showMessageDialog(this, "Deleting");
                }
                else {
                    JOptionPane.showMessageDialog(this, "Unknown Action");
                }

            }
        });

        JPanel inputPanel = new JPanel();
        JLabel prompt = new JLabel("Enter CRN: ");
        JTextField CRNField = new JTextField(10);
        JButton submit = new JButton("Get Entries");
        submit.addActionListener(e -> {
            try {
                int CRN = Integer.parseInt(CRNField.getText());
                output.setText(dbHandler.selectFromTableSpring(submit, CRN));
            } catch (NumberFormatException ex) {
                output.setText(dbHandler.selectFromTableSpring(submit, -1));  // Provide Default Value
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
