import javax.swing.*;
import java.sql.*;

public class DBHandler {

    public UserFile userFile;

    public DBHandler(JFrame context) {
        userFile = new UserFile(context);
    }

    public String insertRecordIntoTableSpring(JFrame trigger, int CRN) {

        if (isCRNPresentInTableSpring(trigger, CRN)) {
            JOptionPane.showMessageDialog(trigger, "Not Inserted");
            return "CRN Already Present";
        }

        String url = String.format("jdbc:mysql://%s:%d/%s",
                userFile.getServer(), userFile.getPort(), userFile.getDbName());
        String username = userFile.getUsername();
        String password = userFile.getPassword();
        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement statement = con.createStatement()) {

            String prefix = JOptionPane.showInputDialog(trigger, "Enter Subject Prefix");
            int courseNum = Integer.parseInt(JOptionPane.showInputDialog(trigger, "Enter Course Number"));
            //int sectionNum = JOptionPane.showInputDialog(trigger, "Enter Section Number");
            char typeOfInst = JOptionPane.showInputDialog(trigger, "Enter Type of Institution").charAt(0);
            String creditHours = JOptionPane.showInputDialog(trigger,
                    "Enter Credit Hour Value as a four digit number without a decimal point");

            String INSERT = String.format("INSERT INTO %s.spring (Subject_Prefix, Course_Number, Section_Number, Type_Instruction, Semester_Credit_Hour_Value, Record_Code, Institution_Code, Location_Code, Other_Higher_Education_Site, Unused, Composite_Classes_Code, Unused2, Tenure, Off_Campus_Location, Instructor_Code, Responsibility_Factor, Students_NOT_affected_by_state_funding, Semester, Year, Students_Who_Exceed_State_Funding, Students_Whose_Developmental_SCH, Lower_Level_Affected_by_UG_Limit, Upper_Level_Affected_by_UG_Limit, Instruction_Mode, Inter_institutional_Identifier, Teaching_Load_Credit)\n" +
                            "VALUES ('%s', '%d', '%d', '%s', '%s', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');",
                    userFile.getDbName(), prefix, courseNum, CRN, typeOfInst, creditHours);
            System.out.println(INSERT);
            ResultSet resultSet = statement.executeQuery(INSERT);

            return "Inserted : " + resultSet.toString();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(trigger, "Could not connect to the database. " +
                    "Try changing the connection settings.");
            return "Not Inserted";
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(trigger, "One or More Fields Were Not Integers");
            return "Not Inserted";
        }

    }

    public String updateRecordInTableSpring(JFrame trigger, int CRN) {
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

            return "Updated";

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(trigger, "Could not connect to the database. " +
                    "Try changing the connection settings.");
            return "Not Updated";
        }
        //JOptionPane.showMessageDialog(trigger, "Updated");
    }

    public String deleteRecordInTableSpring(JFrame trigger, int CRN) {
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

            return "Deleted";

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(trigger, "Could not connect to the database. " +
                    "Try changing the connection settings.");
            return "Not Deleted";
        }
        //JOptionPane.showMessageDialog(trigger, "Deleted");
    }

    public String selectFromTableSpring(JFrame trigger, int CRN) {

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
            JOptionPane.showMessageDialog(trigger, "Could not connect to the database. " +
                    "Try changing the connection settings.");
            return "";
        }
    }

    public boolean isCRNPresentInTableSpring(JFrame trigger, int CRN) {
        if (CRN <= 0)
            return false;
        else return selectFromTableSpring(trigger, CRN).length() > 0;
    }

}
