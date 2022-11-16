import javax.swing.*;
import java.sql.*;

public class DBHandler {

    public UserFile userFile;

    public DBHandler(JFrame context) {
        userFile = new UserFile(context);
    }

    public void insertRecordIntoTableSpring(JButton trigger) {
        JOptionPane.showMessageDialog(trigger, "Inserted");
    }

    public void updateRecordInTableSpring(JButton trigger) {
        JOptionPane.showMessageDialog(trigger, "Updated");
    }

    public void deleteRecordInTableSpring(JButton trigger) {
        JOptionPane.showMessageDialog(trigger, "Deleted");
    }

    public String selectFromTableSpring(JButton submitButton, int CRN) {

        String url = String.format("jdbc:mysql://%s:%d/%s",
                userFile.getServer(), userFile.getPort(), userFile.getDbName());
        String username = userFile.getUsername();
        String password = userFile.getPassword();
        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement statement = con.createStatement()) {

            String select;
            if (CRN == -1) {
                select = "SELECT * FROM " + userFile.getDbName() +".spring;";
            } else {
                select = "SELECT * FROM " + userFile.getDbName() +".spring WHERE section_number=" + CRN + ';';
            }


            ResultSet resultSet = statement.executeQuery(select);

            StringBuilder stringBuilder = new StringBuilder();
            while(resultSet.next()){

                String creditHours = resultSet.getString("Semester_Credit_Hour_Value");
                if (creditHours.length() > 2) {
                    creditHours = creditHours.substring(0, 2);
                }
                String totalEnrollment = resultSet.getString("Students_NOT_Affected_By_State_Funding");
                if (totalEnrollment.length() == 15) {
                    int sum = Integer.parseInt(totalEnrollment.substring(0, 3));
                    sum += Integer.parseInt(totalEnrollment.substring(3, 6));
                    sum += Integer.parseInt(totalEnrollment.substring(6, 9));
                    sum += Integer.parseInt(totalEnrollment.substring(9, 12));
                    sum += Integer.parseInt(totalEnrollment.substring(12, 15));
                    totalEnrollment = String.valueOf(sum);
                }

                stringBuilder.append(resultSet.getRow()).append(" ")
                        //.append(resultSet.getString("Record_Code")).append(' ')
                        //.append(resultSet.getString("Institution_Code")).append(' ')
                        .append(resultSet.getString("Subject_Prefix")).append(' ')
                        .append(resultSet.getString("Course_Number")).append(' ')
                        .append(resultSet.getString("Section_Number")).append(' ')
                        .append(resultSet.getString("Type_Instruction")).append(' ')
                        .append(creditHours).append(' ')
                        //.append(resultSet.getString("Location_Code")).append(' ')
                        //.append(resultSet.getString("Other_Higher_Education_Site")).append(' ')
                        //.append(resultSet.getString("Composite_Classes_Code")).append(' ')
                        //.append(resultSet.getString("Tenure")).append(' ')
                        //.append(resultSet.getString("Off_Campus_Location")).append(' ')
                        //.append(resultSet.getString("Instructor_Code")).append(' ')
                        //.append(resultSet.getString("Responsibility_Factor")).append(' ')
                        .append(totalEnrollment).append(' ')
                        //.append(resultSet.getString("Semester")).append(' ')
                        //.append(resultSet.getString("Year")).append(' ')
                        //.append(resultSet.getString("Students_Who_Exceed_State_Funding")).append(' ')
                        //.append(resultSet.getString("Students_Whose_Developmental_SCH")).append(' ')
                        //.append(resultSet.getString("Lower_Level_Affected_by_UG_Limit")).append(' ')
                        //.append(resultSet.getString("Upper_Level_Affected_by_UG_Limit")).append(' ')
                        //.append(resultSet.getString("Instruction_Mode")).append(' ')
                        //.append(resultSet.getString("Inter_institutional_Identifier")).append(' ')
                        //.append(resultSet.getString("Teaching_Load_Credit"))
                        .append('\n');
            }
            return stringBuilder.toString();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(submitButton, "Could not connect to the database. " +
                    "Try changing the connection settings.");
            return "";
        }
    }

}
