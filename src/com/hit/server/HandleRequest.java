package com.hit.server;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hit.controller.RestaurantController;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Scanner;

public class HandleRequest implements Runnable{


    Gson gson = new GsonBuilder().create();
    Socket socket;
    Scanner in;
    PrintWriter out;
    RestaurantController controller;

    public HandleRequest(Socket _socket) throws IOException {
        socket = _socket;
        in = new Scanner(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void run() {

        try {
            Type type = new TypeToken <Request>(){}.getType();
            Request request = gson.fromJson(in.next(), type);    // need to get the request from the client as Json an then switch case on it.
            Response response = null;
            System.out.println("Got " + gson.toJson(request));

             switch(request.getHeaders().get("action")){
                 case "GetName" : {
                     response = new Response(controller.GetByName(request.getBody()));
                     System.out.println("case 1");
                     break;
                 }
                 case "GetCategory" : {
                    response = new Response(controller.GetByCategory(request.getBody()));
                    System.out.println("case 2");
                    break;
                 }

                 case "Create" : {

                    System.out.println("case 3");
                     break;
                 }
                 case "Update" : {
                     System.out.println("case 4");
                     out.println("");
                     out.flush();
                     break;
                 }
                 case  "Delete": {
                     System.out.println("case 5");

                     break;
                 }
                 default: System.out.println("default");


             }
            // Parse the Request from in then run the //relevant methods in the specific controller and return //response to out at the end
//             out.println(response.json);
//             out.flush();
//             System.out.println(response.json);
             out.close();
             in.close();
             socket.close();
        } catch (IOException e) {
            System.out.println("Server error");
        }
    }
}
