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
    }

    @Override
    public void run() {

        try {
            Type type = new TypeToken <Request>(){}.getType();
            Request request = gson.fromJson(in.next(), type);    // need to get the request from the client as Json an then switch case on it.
            Response response = null;
            System.out.println("Got " + gson.toJson(request));
            System.out.println(request.getBody());
            System.out.println(request.getHeaders());


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
                    String[] args = {"Category","Name","Address","City","PhoneNumber","Rating"};
                    for(int i=0; i<args.length;i++)
                    {
                        args[i] = request.getHeaders().get(args[i]);
                    }
                    controller.SaveUpdateRestaurant(args);
                    response = new Response(args[1] + " Rest, was added successfully");
                    System.out.println("case 3");

                     break;
                 }

                 case "Update" : {
                     String[] args = {"Category","Name","Address","City","PhoneNumber","Rating"};
                     for(int i=0; i<args.length;i++)
                     {
                         args[i] = request.getHeaders().get(args[i]);
                     }
                     controller.SaveUpdateRestaurant(args);
                     response = new Response(args[1] + " Rest, was updated successfully");
                     System.out.println("case 4");
                     break;
                 }
                 case  "Delete": {

                     String restName = request.getBody();
                     controller.DeleteRest(restName);
                     response = new Response(restName + " Rest, was deleted successfully");
                     System.out.println("case 5");
                     break;
                 }
             }

             if(response != null) {
                 out.println(response.json);
                 out.flush();
                 System.out.println(response.json);
             }
             out.close();
             in.close();
             socket.close();
        } catch (IOException e) {
            System.out.println("Server error");
        }
    }
}
