package sample.ChatFunction;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    //use of keyword 'private' to implement encapsulation
    @FXML
    private Button sendButton;
    @FXML
    private TextField textmessage;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vboxes;

    private Server server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            //full implementation of Socket programming language, i.e. use of terms Server, ServerSocket, etc..
            server = new Server(new ServerSocket(1234)); //creation of server
            System.out.println("Connected"); //message if server creation is successful
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Error creating Server"); //error message thrown if server creation fails
        }
        vboxes.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPane.setVvalue((Double) newValue);
            }
        });

        server.MessageFromClient(vboxes);

        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String messageToSend = textmessage.getText();
                if (!messageToSend.isEmpty()) {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);

                    textFlow.setStyle(
                            "-fx-color: rgb(238, 240, 256);" +
                                    "-fx-background-color: rgb(20, 125, 245);" +
                                    "-fx-background-radius: 20px;");

                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.color(0.934, 0.925, 0.996));

                    hBox.getChildren().add(textFlow);
                    vboxes.getChildren().add(hBox);
                    server.MessageToClient(messageToSend);
                    textmessage.clear();
                }
            }
        });
    }

    public static void addLabel(String messageFromClient, VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle(
                "-fx-background-color: rgb(233, 233, 235);" +
                        "-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }

}