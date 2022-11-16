import javax.swing.*;
import java.awt.*;

public class GUI {
    DBHandler dbHandler;
    JFrame window;
    JScrollPane scrollPane;
    JTextArea output;

    public GUI(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
        window = new JFrame("PT Database GUI");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(600, 500);
        window.setResizable(false);
        window.setLocationRelativeTo(null);

        createComponents();

        window.setVisible(true);
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
        window.getContentPane().add(BorderLayout.NORTH, attributeNamePane);
    }

    private void createOutputArea() {
        output = new JTextArea();
        output.setEditable(false);

        scrollPane = new JScrollPane(output);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        window.getContentPane().add(BorderLayout.CENTER, scrollPane);
    }

    private void createControlBar() {

        //JComboBox<JButton> actionSelector = new JComboBox<>();
        //actionSelector.add();


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
        inputPanel.add(prompt);
        inputPanel.add(CRNField);
        inputPanel.add(submit);
        inputPanel.add(editConnection);
        window.getContentPane().add(BorderLayout.SOUTH, inputPanel);
    }

}
