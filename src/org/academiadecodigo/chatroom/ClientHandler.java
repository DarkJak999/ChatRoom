package org.academiadecodigo.chatroom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by codecadet on 11/11/16.
 */
public class ClientHandler implements Runnable {

    private Server server;
    private Socket clientSocket;
    private DataOutputStream out;
    private BufferedReader in;
    private String clientName;
    private int id;
    private boolean connected;

    ClientHandler(Socket clientSocket, Server server, int id) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.id = id;
        this.connected = true;
    }

    @Override
    public void run() {


        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.write("Choose your username\n".getBytes());
            out.flush();

            clientName = in.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        while (connected) {
            try {
                String message = in.readLine();
                message = checkMessage(message);
                server.broadcast(clientName + message, id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        closeClient();
    }

    public void closeClient(){
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){

        try {
            out.write(message.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getId(){
        return this.id;
    }

    public String checkMessage(String message){

        if(message.equals(".quit")){
            connected = false;
            return " disconnected";
        }

        return ": " + message;
    }
}
