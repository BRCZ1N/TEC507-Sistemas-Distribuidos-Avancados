package com.lamport;

import java.io.IOException;

class Main {

    public static void main(String[] args) {

        try {

            LamportServer lamport = new LamportServer(ProcessNode.valueOf(args[0]));

        } catch (IOException e) {

            throw new RuntimeException(e);

        }

    }

}

