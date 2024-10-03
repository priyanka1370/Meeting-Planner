package sample.ChatFunction;

import javafx.scene.layout.VBox;
import sample.ChatFunction.ChatController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket; //opening a socket
    private Socket socket;
    private BufferedReader bufferedReader; // creating a buffer for reading to the socket
    private BufferedWriter bufferedWriter; // creating a buffer for writing to the socket

    public Server(ServerSocket serverSocket) {
        try{
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //opening an input stream to the socket and reading from the stream
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //opening an output stream to the socket and writing to the stream
        }catch(IOException e){
            System.out.println("Error creating Server");
            e.printStackTrace();
            shutDown(socket, bufferedReader, bufferedWriter); // closing the streams and sockets
        }
    }

    public void MessageToClient(String messageForClient){
        try{
            bufferedWriter.write(messageForClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Error sending message");
            shutDown(socket, bufferedReader, bufferedWriter);
        }
    }

    public void MessageFromClient(VBox vBox){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isConnected()){
                    try{
                        String messageFromClient = bufferedReader.readLine();
                        ChatController.addLabel(messageFromClient, vBox);
                    }catch (IOException e){
                        e.printStackTrace();
                        System.out.println("Error receiving message from the Client!");
                        shutDown(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

    public void shutDown(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try{
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}