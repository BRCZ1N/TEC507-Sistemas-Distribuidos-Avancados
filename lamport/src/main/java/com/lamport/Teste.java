package com.lamport;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

class Teste {

    public static void main(String[] args) {

        Socket socket = null;
        try {
            socket = new Socket("localhost", 5000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Event event = new Event(
                1,
                2,
                10,
                "Teste Lamport"
        );

        Gson gson = new Gson();

        out.println(gson.toJson(event));

        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}

