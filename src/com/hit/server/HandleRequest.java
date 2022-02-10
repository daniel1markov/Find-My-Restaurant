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

    public HandleRequest(Socket client) throws IOException {
        socket = client;
        in = new Scanner(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        controller = new RestaurantController();
    }

    @Override
    public void run() {

        try {
            Type type = new TypeToken <Request>(){}.getType();
            Request request = gson.fromJson(in.next(), type);    // need to get the request from the client as Json an then switch case on it.
            Response response = null;

            switch(request.getHeaders().get("action")){

                 case "GetName" : {
                     response = new Response(controller.getByName(request.getBody()));
                     break;
                 }
                 case "GetCategory" : {
                    response = new Response(controller.getByCategory(request.getBody()));
                    break;
                 }

                 case "Add/Update" : {
                    String[] args = {"Category","Name","Address","City","PhoneNumber","Rating"};
                    for(int i=0; i<args.length;i++)
                    {
                        args[i] = request.getHeaders().get(args[i]);
                    }
                    controller.saveUpdateRestaurant(args);
                    response = new Response("OK");
                     break;
                 }

                 case  "Delete": {

                     String restName = request.getBody();
                     if(controller.deleteRest(restName))
                     {
                         response = new Response("OK");
                     }
                     else
                     {
                         response = new Response("NOT");
                     }
                     break;
                 }

                case "GetAll":{

                    response = new Response(controller.getAll());
                    break;
                }
             }

             if(response != null) {
                 out.println(gson.toJson(response));
                 out.flush();
             }
             out.close();
             in.close();
             socket.close();
        } catch (IOException e) {
            System.out.println("Server error");
        }
    }
}
