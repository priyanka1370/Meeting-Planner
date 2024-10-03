package sample.Unused;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.*;

public class ParticipantManageScreen {
    @FXML
    Button AddParticipantsButton;
    @FXML
    Button RemoveParticipantsButton;
    @FXML
    TextField EmailAddField;
    @FXML
    ListView <String> ParticipantList;
    @FXML
    Label errorMessage;
    @FXML
    Button doneButton;
    private Parent root;


    public void AddClicked(MouseEvent mouseEvent) throws IOException, SQLException, Exception {
        String email = EmailAddField.getText();
        Connection dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Year12finaldb","root","root");
        Statement statement = dbConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user");

        while (resultSet.next()){
            String finalEmail = resultSet.getString("Email");
            if (email.equals(finalEmail)) {
                ParticipantList.getItems().add(EmailAddField.getText());

            }
            else {
                errorMessage.setText("This user doesn't exist");
            }
    }}

    public void RemoveClicked(MouseEvent mouseEvent) throws IOException {
        int selectedParticipant = ParticipantList.getSelectionModel().getSelectedIndex();
        ParticipantList.getItems().remove(selectedParticipant);
    }

    public void DoneClicked(MouseEvent mouseEvent) throws IOException {
        ObservableList <String> participants = ParticipantList.getItems();
        System.out.println(participants);
        Parent OrganizerScreen = FXMLLoader.load(getClass().getResource("../Tabs/Tabs.fxml"));

        Scene OrganizerScreenScene = new Scene(OrganizerScreen);
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(OrganizerScreenScene);
        stage.show();

                }
            }




