package com.lamport;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

class LamportServer extends Thread {

    private ProcessNode process;
    private AtomicLong clock;
    private ServerSocket server;
    private final Scanner scan = new Scanner(System.in);

    public LamportServer(ProcessNode process) throws IOException {

        this.clock = new AtomicLong(0);
        this.server = new ServerSocket(process.getPort());
        this.process = process;
        start();

    }

    public AtomicLong getClock() {

        return this.clock;

    }

    public void setClock(AtomicLong clock) {

        this.clock = clock;

    }

    public void localEvent() {

        clock.incrementAndGet();
        System.out.println("Evento local - Relógio: " + this.clock.get());

    }

    public void receiveEvent(Socket client) throws IOException {

        try (
                ObjectInputStream in = new ObjectInputStream(client.getInputStream())
        ) {

            Event message = (Event) in.readObject();
            long previousClock = clock.get();
            clock.updateAndGet(current -> Math.max(current, message.getClock()) + 1);

            System.out.println("=========================================");
            System.out.println("Mensagem Recebida do Processo: " + message.getFromId());
            System.out.println("Relógio Local Antes: " + previousClock);
            System.out.println("Relógio na Mensagem: " + message.getClock());
            System.out.println("Relógio Local Atualizado: " + clock.get());
            System.out.println("Conteúdo: " + message.getContent());
            System.out.println("=========================================");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        client.close();
    }

    public void sendEvent(ProcessNode destination){

        System.out.println("Digite a mensagem a ser enviada:");
        String contentMessage = scan.nextLine();

        try{

            Socket socket = new Socket(destination.getHost(), destination.getPort());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            clock.incrementAndGet();
            Event event = new Event(process.getId(),destination.getId(),clock.get(),contentMessage);

            System.out.println("=========================================");
            System.out.println("Mensagem Enviada");
            System.out.println("Processo Origem: " + event.getFromId());
            System.out.println("Processo Destino: " + event.getToId());
            System.out.println("Relógio Local Após Incremento: " + clock.get());
            System.out.println("Relógio Enviado: " + event.getClock());
            System.out.println("Conteúdo: " + event.getContent());
            System.out.println("=========================================");

            out.writeObject(event);
            out.flush();
            out.close();
            socket.close();

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Não foi possível conectar ao processo destino.");
        }

    }

    public void menuMessage() {

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
                        System.out.println("Erro ao receber mensagem.");
                    }
                }).start();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }

}
