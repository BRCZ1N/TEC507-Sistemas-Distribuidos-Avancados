package com.lamport;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

class LamportServer extends Thread {

    private ProcessNode process;
    private AtomicLong timeStamp;
    private ServerSocket server;

    public LamportServer(ProcessNode process) throws IOException {

        this.timeStamp = new AtomicLong(0);
        this.server = new ServerSocket(process.getPort());
        this.process = process;
        start();

    }

    public AtomicLong getTimeStamp() {

        return this.timeStamp;

    }

    public void setTimeStamp(AtomicLong timeStamp) {

        this.timeStamp = timeStamp;

    }

    public void localEvent() {

        timeStamp.incrementAndGet();
        System.out.println("Evento local - " + "Relógio: " + this.timeStamp);

    }


    public void receiveEvent(Socket client) throws IOException {

        try (
                ObjectInputStream in = new ObjectInputStream(client.getInputStream())
        ) {

            Event message = (Event) in.readObject();
            long previousTimeStamp = timeStamp.get();
            timeStamp.updateAndGet(current -> Math.max(current, message.getTimeStamp()) + 1);

            System.out.println("=========================================");
            System.out.println("Mensagem Recebida do Processo: " + message.getFromId());
            System.out.println("Relógio Local Antes: " + previousTimeStamp);
            System.out.println("Relógio na Mensagem: " + message.getTimeStamp());
            System.out.println("Relógio Local Atualizado: " + timeStamp.get());
            System.out.println("Conteúdo: " + message.getContent());
            System.out.println("=========================================");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        client.close();
    }

    public void sendEvent(ProcessNode destination){

        System.out.println("Digite a mensagem a ser enviada:");
        Scanner scan = new Scanner(System.in);
        String contentMessage = scan.nextLine();

        try{

            Socket socket = new Socket(
                    destination.getHost(),
                    destination.getPort()
            );
            ObjectOutputStream out =
                    new ObjectOutputStream(
                            socket.getOutputStream());
            timeStamp.incrementAndGet();
            Event event = new Event(process.getId(),destination.getId(),timeStamp.get(),contentMessage);
            out.writeObject(event);
            out.flush();

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void menuMessage() {

        Scanner scan = new Scanner(System.in);
        ProcessNode destination;

        while(true){

            System.out.println("Menu de mensagens - Lamport");
            System.out.println("1 - Evento Local");
            System.out.println("2 - Envio de mensagem");
            System.out.println("3 - Sair");
            String option = scan.nextLine();

            switch (option) {

                case "1":
                    localEvent();
                    break;

                case "2":

                    System.out.println("Escolha o processo que irá receber a mensagem:");
                    String optionId;

                    switch (process) {

                        case P1:

                            System.out.println("1 - P2");
                            System.out.println("2 - P3");
                            optionId = scan.nextLine();
                            switch (optionId) {

                                case "1":
                                    destination =  ProcessNode.valueOf("P2");
                                    sendEvent(destination);
                                    break;

                                case "2":
                                    destination = ProcessNode.valueOf("P3");
                                    sendEvent(destination);
                                    break;

                                default:
                                    System.out.println("P1 não pode enviar para si mesmo.");
                            }

                            break;

                        case P2:

                            System.out.println("1 - P1");
                            System.out.println("2 - P3");
                            optionId = scan.nextLine();

                            switch (optionId) {

                                case "1":
                                    destination = ProcessNode.valueOf("P1");
                                    sendEvent(destination);
                                    break;

                                case "2":
                                    destination = ProcessNode.valueOf("P3");
                                    sendEvent(destination);
                                    break;

                                default:
                                    System.out.println("P2 não pode enviar para si mesmo.");
                            }

                            break;

                        case P3:

                            System.out.println("1 - P1");
                            System.out.println("2 - P2");
                            optionId = scan.nextLine();

                            switch (optionId) {

                                case "1":
                                    destination = ProcessNode.valueOf("P1");
                                    sendEvent(destination);
                                    break;

                                case "2":
                                    destination = ProcessNode.valueOf("P2");
                                    sendEvent(destination);
                                    break;

                                default:
                                    System.out.println("P3 não pode enviar para si mesmo.");
                            }

                            break;
                    }

                    break;

                case "3":

                    return;
            }


        }

    }


    @Override
    public void run() {

        new Thread(() -> {
            menuMessage();
        }).start();

        while (true) {

            try {

                Socket client = server.accept();

                new Thread(() -> {
                    try {
                        receiveEvent(client);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }

}
