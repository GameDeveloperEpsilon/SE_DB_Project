import java.sql.*;

public class DBHandler {

    public String selectFromTableSpring(int CRN) {
        UserFile userFile = new UserFile();
        String url = String.format("jdbc:mysql://%s:%d/%s",
                userFile.server, userFile.port, userFile.dbName);
        String username = userFile.username;
        String password = userFile.password;
        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement statement = con.createStatement()) {

            System.out.println("Connection Established");

            String select;
            if (CRN == -1) {
                select = "SELECT * FROM import_test.spring;";
            } else {
                select = "SELECT * FROM import_test.spring WHERE section_number=" + CRN + ';';
            }


            ResultSet resultSet = statement.executeQuery(select);

            StringBuilder stringBuilder = new StringBuilder();
            while(resultSet.next()){
                stringBuilder.append(resultSet.getRow()).append(" ")
                        //.append(resultSet.getString("Record_Code")).append(' ')
                        //.append(resultSet.getString("Institution_Code")).append(' ')
                        .append(resultSet.getString("Subject_Prefix")).append(' ')
                        .append(resultSet.getString("Course_Number")).append(' ')
                        .append(resultSet.getString("Section_Number")).append(' ')
                        .append(resultSet.getString("Type_Instruction")).append(' ')
                        .append(resultSet.getString("Semester_Credit_Hour_Value")).append(' ')
                        //.append(resultSet.getString("Location_Code")).append(' ')
                        //.append(resultSet.getString("Other_Higher_Education_Site")).append(' ')
                        //.append(resultSet.getString("Composite_Classes_Code")).append(' ')
                        //.append(resultSet.getString("Tenure")).append(' ')
                        //.append(resultSet.getString("Off_Campus_Location")).append(' ')
                        //.append(resultSet.getString("Instructor_Code")).append(' ')
                        //.append(resultSet.getString("Responsibility_Factor")).append(' ')
                        .append(resultSet.getString("Students_NOT_Affected_By_State_Funding")).append(' ')
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
            throw new RuntimeException(e);
        }
    }

}
