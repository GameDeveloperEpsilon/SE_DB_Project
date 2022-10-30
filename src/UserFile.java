import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class UserFile {
    public String server;
    int port;
    public String dbName;
    public String username;
    public String password;
    private String filePath = "./src/user.json";

    public UserFile() {
        try (FileReader fileReader = new FileReader(filePath)) {
            Object p = new JSONParser().parse(fileReader);
            JSONObject jo = (JSONObject) p;
            server = (String) jo.get("server");
            port = Integer.parseInt((String) jo.get("port"));
            dbName = (String) jo.get("databaseName");
            username = (String) jo.get("username");
            password = (String) jo.get("password");

        } catch (FileNotFoundException e) {
            System.out.println("Missing user.json");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error reading user.json");
            throw new RuntimeException(e);
        } catch (ParseException e) {
            System.out.println("Error parsing user.json");
            throw new RuntimeException(e);
        }
    }
}
