package sample.Unused;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ParticipantScreen {
    public void logOutClicked(MouseEvent mouseEvent) throws IOException {
        Parent LoginScreen = FXMLLoader.load(getClass().getResource("LoginSignup/LoginScreen.fxml"));
        Scene LoginScreenScene = new Scene(LoginScreen);
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(LoginScreenScene);
        stage.show();

    }

    public void ScheduleClicked(MouseEvent mouseEvent) throws IOException{
        Parent ParticipantManageScreen = FXMLLoader.load(getClass().getResource("ParticipantManageScreen.fxml"));
        Scene ParticipantManageScreenScene = new Scene(ParticipantManageScreen);
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(ParticipantManageScreenScene);
        stage.show();
    }
}
