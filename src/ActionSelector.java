import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;

public class ActionSelector extends JComboBox<String> implements ItemListener {

    public ActionSelector() {
        addItem("SELECT");
        addItem("INSERT");
        addItem("UPDATE");
        addItem("DELETE");
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // if the state combobox is changed

        if (e.getSource() == this)
            System.out.println("I am changing");

        System.out.println(getSelectedItem());

        if (Objects.equals(getSelectedItem(), "SELECTED"))
            JOptionPane.showMessageDialog(this, "Selecting");
        else if (Objects.equals(getSelectedItem(), "INSERT"))
            JOptionPane.showMessageDialog(this, "Inserting");
        else if (Objects.equals(getSelectedItem(), "UPDATE"))
            JOptionPane.showMessageDialog(this, "Updating");
        else if (Objects.equals(getSelectedItem(), "DELETE"))
            JOptionPane.showMessageDialog(this, "Deleting");
        else
            JOptionPane.showMessageDialog(this, "Unknown Action");
    }

}
