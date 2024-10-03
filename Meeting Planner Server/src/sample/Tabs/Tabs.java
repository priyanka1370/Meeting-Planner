package sample.Tabs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.format.DateTimeFormatter;

public class Tabs {
    @FXML
    TextField meetingNameField;
    @FXML
    TextField placeField;
    @FXML
    TextField endTimeField;
    @FXML
    TextField startTimeField;
    @FXML
    Button scheduleButton;
    @FXML
    Button backButton;
    @FXML
    DatePicker dateField;
    @FXML
    Label ErrorMessage;
    @FXML
    TextField enteredDate2;


    private int MeetingID = 0;
    private String enteredName = "";
    private String enteredStartTime = "";
    private String enteredEndTime = "";
    private String enteredPlace = "";
    private String enteredDate = "";
    private boolean done = false;
    private String enteredDateTwo = "";


    public void scheduleClicked(MouseEvent mouseEvent) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Year12finaldb", "root", "root");
        Statement stmt = ((java.sql.Connection) conn).createStatement();
        ResultSet resultSet = stmt.executeQuery("select * from meeting");
        enteredName = meetingNameField.getText();
        enteredStartTime = startTimeField.getText();
        enteredEndTime = endTimeField.getText();
        enteredPlace = placeField.getText();
        //enteredDateTwo = enteredDate2.getText();
        enteredDate = dateField.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));




        try {
            boolean newMeeting = true;
            String query = "INSERT INTO meeting(MeetingID, Name, Start, End, Place, Date) values(?,?,?,?,?,?)";
            PreparedStatement prepstmt = conn.prepareStatement(query);

            while (resultSet.next()) {
                MeetingID = resultSet.getInt("MeetingID");
                newMeeting = true;
            }

            if (newMeeting == true) {
                MeetingID = MeetingID + 1;
                System.out.println(MeetingID);
                prepstmt.setInt(1, MeetingID);
                prepstmt.setString(2, enteredName);
                prepstmt.setString(3, enteredStartTime);
                prepstmt.setString(4, enteredEndTime);
                prepstmt.setString(5, enteredPlace);
                prepstmt.setString(6, enteredDate);
                prepstmt.executeUpdate();
                ErrorMessage.setText("Meeting successfully scheduled!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void backClicked(MouseEvent mouseEvent) throws IOException {
        Parent ParticipantScreen = FXMLLoader.load(getClass().getResource("../Unused/ParticipantScreen.fxml"));
        Scene ParticipantScreenScene = new Scene(ParticipantScreen);
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(ParticipantScreenScene);
        stage.show();

    }

    public void nextClicked(MouseEvent mouseEvent) {

    }
}