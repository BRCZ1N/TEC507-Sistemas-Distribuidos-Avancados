package com.lamport;

import java.io.IOException;

class Lamport {

    public static void main(String[] args) {

        try {

            int port = Integer.parseInt(args[0]);
            long processId = Long.parseLong(args[1]);
            System.out.println("Iniciando servidor Lamport TCP - porta: " + port + " número do processo: " + processId);
            LamportServer lamport = new LamportServer(port,processId);

        } catch (IOException e) {

            throw new RuntimeException(e);

        }


    }

}

