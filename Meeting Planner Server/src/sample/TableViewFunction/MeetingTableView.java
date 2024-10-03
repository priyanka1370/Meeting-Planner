package sample.TableViewFunction;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class MeetingTableView implements Initializable {
    @FXML
    private TableView<Meeting> meetingTableView;
    @FXML
    private TableColumn<Meeting, Integer> col_id;
    @FXML
    private TableColumn<Meeting, String> col_name;
    @FXML
    private TableColumn<Meeting, String> col_start;
    @FXML
    private TableColumn<Meeting, String> col_end;
    @FXML
    private TableColumn<Meeting, String> col_place;
    @FXML
    private TableColumn<Meeting, String> col_date;
    @FXML
    private TableColumn<Meeting, String> col_edit;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Meeting meeting = null;
    int meetingid;
    private boolean update;
    //use of an observable arraylist to define my meeting list
    ObservableList<Meeting> MeetingList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDate();
    }

    @FXML
    private void refresh() {
        try {
            MeetingList.clear();
            query = "SELECT * FROM `meeting`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                MeetingList.add(new Meeting(
                        resultSet.getInt("MeetingID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Start"),
                        resultSet.getString("End"),
                        resultSet.getString("Place"),
                        resultSet.getString("Date")));
                meetingTableView.setItems(MeetingList);

            }

        } catch (SQLException ex) {
            Logger.getLogger(MeetingTableView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void refreshClicked(MouseEvent mouseEvent) {
        refresh();
    }

    private void loadDate(){
        try {
            connection = dbConnection.connect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        refresh();
        col_id.setCellValueFactory(new PropertyValueFactory<>("MeetingID"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col_start.setCellValueFactory(new PropertyValueFactory<>("Start"));
        col_end.setCellValueFactory(new PropertyValueFactory<>("End"));
        col_place.setCellValueFactory(new PropertyValueFactory<>("Place"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("Date"));


        Callback<TableColumn<Meeting, String>, TableCell<Meeting, String>> cellFactory = (TableColumn<Meeting, String> param) -> {
            final TableCell<Meeting, String> cell = new TableCell<Meeting, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        // use of external library
                        FontAwesomeIconView deleteButton = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        deleteButton.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:30px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        //deleting from the database and TableView simultaneously
                        deleteButton.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                meeting = meetingTableView.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `meeting` WHERE MeetingID  =" + meeting.getMeetingID();
                                connection = dbConnection.connect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refresh();
                            } catch (SQLException ex) {
                                Logger.getLogger(MeetingTableView.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        HBox managebtn = new HBox(deleteButton);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteButton, new Insets(2, 2, 0, 3));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }

            };

            return cell;
        };
        col_edit.setCellFactory(cellFactory);
        meetingTableView.setItems(MeetingList);
        refresh();


    }

}








