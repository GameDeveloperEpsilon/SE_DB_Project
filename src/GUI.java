import javax.swing.*;
import java.awt.*;

public class GUI {
    DBHandler dbHandler;
    JFrame window;
    JPanel inputPanel;
    JTextField first_name, last_name, age;
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
        output = new JTextArea();
        output.setEditable(false);

        scrollPane = new JScrollPane(output);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        window.getContentPane().add(BorderLayout.CENTER, scrollPane);

        JPanel inputPanel = new JPanel();
        JLabel prompt = new JLabel("CRN ");
        JTextField CRNField = new JTextField(10);
        JButton submit = new JButton("Get Entries");
        submit.addActionListener(e -> {
            System.out.println("Testing...");

            try {
                int CRN = Integer.parseInt(CRNField.getText());
                output.setText(dbHandler.selectFromTableSpring(CRN));
            } catch (NumberFormatException ex) {
                output.setText(dbHandler.selectFromTableSpring(-1));
            }
        });
        inputPanel.add(prompt);
        inputPanel.add(CRNField);
        inputPanel.add(submit);
        window.getContentPane().add(BorderLayout.SOUTH, inputPanel);
    }
}