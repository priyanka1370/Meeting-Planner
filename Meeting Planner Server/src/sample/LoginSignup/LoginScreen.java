package sample.LoginSignup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.ChatFunction.Server;

import java.io.IOException;
import java.sql.*;

public class LoginScreen {
    @FXML
    TextField UsernameField;
    @FXML
    PasswordField PasswordField;
    @FXML
    Button SubmitButton;
    @FXML
    Label LoginMessage;
    @FXML
    Button SignUpButton;

// method that has the application search the database for values that match the entered login details upon clicking
// and opens a new scene if validated
    public void submitClicked(MouseEvent mouseEvent) throws IOException, SQLException {

        String username = UsernameField.getText();
        String password = PasswordField.getText();
        //connecting to the database
        Connection dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Year12finaldb","root","root");
        Statement statement = dbConnection.createStatement();
        //reading from the database
        ResultSet resultSet = statement.executeQuery("select * from user");

        while (resultSet.next()){
            String finalUsername = resultSet.getString("Username");
            String finalPassword = resultSet.getString("Password");


            if (username.equals(finalUsername) && password.equals(finalPassword)){
                LoginMessage.setText("Welcome, admin");
                Parent scheduleScreen = FXMLLoader.load(getClass().getResource("../Tabs/Tabs.fxml"));
                Scene scheduleScreenScene = new Scene(scheduleScreen);
                Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                stage.setScene(scheduleScreenScene);
                stage.show();

            }
            else{
                LoginMessage.setText("The username or password is incorrect please try again");
                LoginMessage.setAlignment(Pos.CENTER);

            }
        }}

    public void signUpClicked(MouseEvent mouseEvent) throws IOException{
        Parent registerScreen = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Scene registerScreenScene = new Scene(registerScreen);
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(registerScreenScene);
        stage.show();

    }
}