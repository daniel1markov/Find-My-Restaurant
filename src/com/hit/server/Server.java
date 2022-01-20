package com.hit.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static boolean serverUp = true;

    public static void main(String[] args) throws Exception {

        ServerSocket server =new ServerSocket(12345);
        Socket someClient;
        while(serverUp){
            try {
                someClient = server.accept();
                System.out.println("We are Connected now!");
                new Thread(new HandleRequest(someClient)).start();
            }
            catch (Exception e) {
                System.out.println("And..... it's gone");
            }
        }
        server.close();
    }
}

