package com.lamport;

import java.io.IOException;

class Main {

    public static void main(String[] args) {

        try {
            int port = 5000, processId = 1;
            System.out.println("Iniciando servidor Lamport TCP - porta: " + port + " número do processo: " + processId);
            LamportServer lamport = new LamportServer(port,processId);

        } catch (IOException e) {

            throw new RuntimeException(e);

        }


    }

}

