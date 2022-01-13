package com.hit.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.Socket;

public class HandleRequest implements Runnable{


    Gson gson = new GsonBuilder().create();
    Socket socket;

    public HandleRequest(Socket _socket) {
        socket = _socket;
    }

    @Override
    public void run() {
        ObjectInputStream in;
        ObjectOutputStream out;
        try {
             in = new ObjectInputStream(socket.getInputStream());
             out = new ObjectOutputStream(socket.getOutputStream());

            // Parse the Request from in then run the //relevant methods in the specific controller and return //response to out at the end

            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Server error");
        }

    }
}
