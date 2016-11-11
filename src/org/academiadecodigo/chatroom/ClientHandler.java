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

    ClientHandler(Socket clientSocket, Server server) {
        this.server = server;
        this.clientSocket = clientSocket;
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

        while (true) {
            try {
                server.broadcast(clientName + ": " + in.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
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
}
