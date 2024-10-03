package sample.TableViewFunction;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

//class for the meeting database connection
public class dbConnection {
    //encapsulation
    private static int PORT = 8889;
    private static String HOST = "127.0.0.1";
    private static String DB_NAME = "Year12finaldb";
    private static String USERNAME = "root";
    private static String PASSWORD = "root";
    private static Connection connection;

    //connecting to the database
    public static Connection connect() throws SQLException {
        connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s",HOST, PORT, DB_NAME), USERNAME, PASSWORD);
        return connection;
    }


    }




