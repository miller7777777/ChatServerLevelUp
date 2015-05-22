package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * Created by Student08 on 19.05.2015.
 */
public class Client extends Thread{

    private Socket sock;
    private ChatServer server;
    private BufferedReader reader;
    private PrintWriter writer;
    public String login;

    public Client(Socket sock, ChatServer server) throws IOException {

        this.server = server;
        this.sock = sock;
        reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        writer = new PrintWriter(sock.getOutputStream());
//        sleep(2000);
//        Не помогло.
        writer.println("Input login: ");
        System.out.println("Query login from new client");

//        this.login = login;
//        setDaemon(true);
        start();
    }


    @Override
    public void run() {

//        writer.println("Input login: ");
        /*
        Получается какая-то фигня. Сервер ждет чего-то от  клиента, пока клиент не введет что-то - он не получит приглашения от сервера ввести логин. Введенное ПЕРЕД приглашением что-то и будет логином.

        Оставим пока.
         */

        try {
            login = reader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.println("Login " + login + " accepted.");
        System.out.println("New`s client login is: " + login);
//        super.run();
        while (true){
            try {
                String message = reader.readLine();
                if (message != null){
                    if (message.equals("exit")){
                        server.removeClient(this);
                        writer.close();
                        reader.close();
                        sock.close();
                        break;
                    }
                    if (message.startsWith("@")){
                        server.notifyConcretClients(login, message);
                    }else {
                        server.notifyAllClients(login + ": " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String message){

        writer.println(message);
        writer.flush();
    }

    //TODO:
    /*
        1. Клиенту ввести логин
        2. При подключении сервер устанавливает полученный логин объекту класса Client
        3. При оповещении клиентов message = "Login: message"
        4. Если клиент отправляет сообщение, то он не должен выводить его же, полученное обратно от сервера
        5*) Личная переписка:
                Сообщение пишется в консоли в вде @login:message
                Сервер отправляет message только пользователю login
     */
}
