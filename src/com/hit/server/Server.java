package com.hit.server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static boolean serverUp = true;

    public static void main(String[] args) {
        try {
            ServerSocket server =new ServerSocket(12345);
            while(serverUp){
                Socket someClient = server.accept();
                new Thread(new HandleRequest(someClient)).start();
            }
            server.close();
        } catch (Exception e) {
            System.out.println("And..... it's gone");
        }

    }
}
