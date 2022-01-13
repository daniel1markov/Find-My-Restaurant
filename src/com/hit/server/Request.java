package com.hit.server;

import java.util.Map;

public class Request {

    private Map <String, String> headers;
    private String body;

    public Request(Map<String, String> headers, String body) {
        super();
        this.headers = headers;
        this.body = body;
    }


    public String getBody() {
        return body;
    }


    public void setBody(String body) {
        this.body = body;
    }


    public Map<String, String> getHeaders() {
        return headers;
    }


    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }


    @Override
    public String toString() {
        return "Request [headers=" + headers + ", body=" + body + "]";
    }
}
