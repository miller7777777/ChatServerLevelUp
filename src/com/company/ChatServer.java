package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Student08 on 19.05.2015.
 */
public class ChatServer {


    private static final int PORT = 7000;
    private ServerSocket serverSocket;
    ArrayList<Client> clients;

    public void acceptClients(){

        clients = new ArrayList<Client>();

        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Client client = new Client(clientSocket, this);
                clients.add(client);
                System.out.println("Client added");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void notifyAllClients(String message){

        System.out.println("Notify all clients with message: " + message);


        for (int i = 0; i < clients.size(); i++) {

            clients.get(i).send(message);

        }

    }

    public void removeClient(Client c){
        if (clients.remove(c)){
            System.out.println("Clent has disconnected.");
        }
    }


}
