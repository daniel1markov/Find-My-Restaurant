package com.hit.server;

import com.google.gson.Gson;

import java.util.List;

public class Response {
    public String json;

    public <T> Response(List<T> itsJson)
    {
        if(itsJson.isEmpty())
        {
            json = "We are sorry but it wasn't found";
        }
        else {
            json = new Gson().toJson(itsJson);
        }
    }

    public Response(String string) {
        json = string;
    }
    public Response()
    {

    }
}
