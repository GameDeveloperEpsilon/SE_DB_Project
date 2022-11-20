import javax.swing.*;

public class Main {
//test
    public static void main(String[] args) {

        GUI gui = new GUI(null);
        DBHandler handler = new DBHandler(gui);
        gui.setDbHandler(handler);

    }
}
