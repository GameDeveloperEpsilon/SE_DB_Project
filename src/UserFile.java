import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.*;

public class UserFile {
    private final String filePath = "C:\\ProgramData\\DB_Project\\";
    private final String fileLocation = filePath + "user.json";

    private String server;
    private int port;
    private String dbName;
    private String username;
    private String password;

    public UserFile() {
        try (FileReader fileReader = new FileReader(fileLocation)) {
            Object p = new JSONParser().parse(fileReader);
            JSONObject jo = (JSONObject) p;
            loadProperties(jo);

        } catch (FileNotFoundException e) {
            System.out.println("Missing user.json");
            try {
                System.out.println("Making Directory");
                File directory = new File(filePath);
                boolean made = directory.mkdirs();
                if (made) {
                    System.out.println("I have been made");
                }
                if (directory.exists()) {
                    System.out.println("I exist");
                } else {
                    System.out.println("I do not exist");
                }

                System.out.println("Making File");
                File file = new File(fileLocation);
                file.createNewFile();

                populateUserFile();
                System.out.println("File Made");

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            System.out.println("Error reading user.json");
            //throw new RuntimeException(e);
        } catch (ParseException e) {
            System.out.println("Error parsing user.json  The file is not in JSON format.");
            //throw new RuntimeException(e);
        }
    }

    private void populateUserFile() {
        try (FileWriter writer = new FileWriter(fileLocation)) {
            // Get Fields from user
            server = JOptionPane.showInputDialog("Enter server name ");
            port = Integer.parseInt(JOptionPane.showInputDialog("Enter port number "));
            dbName = JOptionPane.showInputDialog("Enter database name ");
            username = JOptionPane.showInputDialog("Enter username ");
            password = JOptionPane.showInputDialog("Enter password ");

            // Build String
            JSONObject fileContents = new JSONObject();
            fileContents.put("server",server);
            fileContents.put("port",String.valueOf(port));
            fileContents.put("databaseName",dbName);
            fileContents.put("username",username);
            fileContents.put("password",password);

            // Write to user.json
            writer.write(fileContents.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProperties(JSONObject loginProperties) {
        server = (String) loginProperties.get("server");
        port = Integer.parseInt((String) loginProperties.get("port"));
        dbName = (String) loginProperties.get("databaseName");
        username = (String) loginProperties.get("username");
        password = (String) loginProperties.get("password");
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }

    public String getDbName() {
        return dbName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
