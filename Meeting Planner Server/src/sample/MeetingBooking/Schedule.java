package sample.MeetingBooking;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.ChatFunction.Server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.*;
import java.time.format.DateTimeFormatter;


public class Schedule {
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
    Button logOutButton;
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



    public void logOutClicked (MouseEvent mouseEvent) throws IOException {
        Parent LoginScreen = FXMLLoader.load(getClass().getResource("LoginSignup/LoginScreen.fxml"));
        Scene LoginScreenScene = new Scene(LoginScreen);
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(LoginScreenScene);
        stage.show();
    }


    public void scheduleClicked(MouseEvent mouseEvent) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Year12finaldb", "root", "root");
        Statement stmt = conn.createStatement();
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
                if (dateField != null){
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
                ErrorMessage.setAlignment(Pos.CENTER);
                meetingNameField.clear();
                startTimeField.clear();
                endTimeField.clear();
                placeField.clear();
            }
            else{
                ErrorMessage.setText("Please choose a date");
                ErrorMessage.setAlignment(Pos.CENTER);

            }}
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

