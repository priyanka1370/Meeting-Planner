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

import java.io.IOException;
import java.sql.*;
import java.sql.DriverManager;

public class Register {

    @FXML
    TextField SignUpUsernameField;
    @FXML
    PasswordField SignUpPasswordField;
    @FXML
    PasswordField ConfirmPasswordField;
    @FXML
    TextField EmailField;
    @FXML
    Label ErrorMessage;
    @FXML
    Button RegisterButton;
    @FXML
    Button LogInButton;

    private int ID = 0;
    private String enteredUsername = "";
    private String enteredPassword = "";
    private String confirmPassword = "";
    private String enteredEmail = "";
    private boolean done = false;

// Connection is established with the database again
    public void registerClicked(MouseEvent mouseEvent) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Year12finaldb", "root", "root");
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery("select * from user");

        enteredEmail = EmailField.getText();
        enteredUsername = SignUpUsernameField.getText();
        enteredPassword = SignUpPasswordField.getText();
        confirmPassword = ConfirmPasswordField.getText();

        boolean newAccount = true;
        // keywords INSERT INTO allow me to enter into the mySQL columns from java
        String query = "INSERT INTO user(ID, Username, Email, Password) values(?,?,?,?)";
        //full use of sql imports i.e. use of keywords like PreparedStatement, ResultSet, etc.
        PreparedStatement prepstmt = conn.prepareStatement(query);

        while (resultSet.next()) {
            String email = resultSet.getString("Email");
            if (enteredEmail.equals(email)) {
                ErrorMessage.setText("This email has already been registered, try logging in");
                ErrorMessage.setAlignment(Pos.CENTER);
                newAccount = false;
            }
            ID = resultSet.getInt("ID");
        }
// Inserting into the database using the PreparedStatement
        if (newAccount == true) {
            ID = ID + 1;
            System.out.println(ID);
            if (confirmPassword.equals(enteredPassword)) {
                prepstmt.setInt(1, ID);
                prepstmt.setString(2, enteredUsername);
                prepstmt.setString(3, enteredEmail);
                prepstmt.setString(4, enteredPassword);
                prepstmt.executeUpdate();
                done = true;
            }
            if (done == true) {
                ErrorMessage.setText("Your account has been created, you can now log in!");
                ErrorMessage.setAlignment(Pos.CENTER);
            }
            if (done == false){
                ErrorMessage.setText("The entered passwords do not match, please retry.");
                ErrorMessage.setAlignment(Pos.CENTER);
            }

        }

    }

    public void LogInClicked(MouseEvent mouseEvent) throws IOException{
        Parent LoginScreen = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Scene LoginScreenScene = new Scene(LoginScreen);
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(LoginScreenScene);
        stage.show();
    }
}

