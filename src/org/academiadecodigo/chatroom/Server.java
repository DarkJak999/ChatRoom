package org.academiadecodigo.chatroom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by codecadet on 11/11/16.
 */
public class Server {

    private ServerSocket server;
    private Socket clientSocket;
    private DataOutputStream out;
    private BufferedReader in;
    String message;
    LinkedList<ClientHandler> clients = new LinkedList<>();

    //in and out streams

    public void startServer(){


        System.out.println("Opening Server...");

        try {
            server = new ServerSocket(8000);

            System.out.println("\nServer online");

            while (true) {

                clientSocket = server.accept();
                System.out.println("Connecting client");
                ClientHandler handler = new ClientHandler(clientSocket, this);
                Thread thread = new Thread(handler);
                thread.start();

                clients.add(handler);

                //add client to client array in order to broadcast
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void broadcast(String message){

        System.out.println(message);

        for (ClientHandler currentClient: clients) {
            currentClient.sendMessage(message + "\n");
        }
    }



}
