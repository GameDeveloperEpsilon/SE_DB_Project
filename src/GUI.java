import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    DBHandler dbHandler;
    JScrollPane scrollPane;
    JTextArea output;
    ActionSelector actionSelector;

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

        actionSelector = new ActionSelector();

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
