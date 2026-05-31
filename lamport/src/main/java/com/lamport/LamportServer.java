package com.lamport;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

class LamportServer extends Thread {

    private long processId;
    private AtomicLong timeStamp;
    private int port;
    private ServerSocket server;

    public LamportServer(int port, long processId) throws IOException {

        this.port = port;
        this.timeStamp = new AtomicLong(0);
        this.processId = processId;
        this.server = new ServerSocket(this.port);
        start();

    }

    public AtomicLong getTimeStamp() {

        return this.timeStamp;

    }

    public void setTimeStamp(AtomicLong timeStamp) {

        this.timeStamp = timeStamp;

    }

    public int getPort() {

        return this.port;

    }

    public void setPort(int port) {

        this.port = port;

    }

    public long getProcessId() {

        return this.processId;

    }

    public void setProcessId(long processId) {

        this.processId = processId;

    }

    public void localEvent() {

        timeStamp.incrementAndGet();

    }

    public void sendEvent(Event message) {

        timeStamp.incrementAndGet();
        System.out.println("Mensagem - From: " + message.getFromId() + " To: " + message.getToId() + " Message: " + message.getContent() + " Relógio: " + this.timeStamp);

    }

    public void receiveEvent(Event message) {

        timeStamp.updateAndGet(current -> Math.max(current, message.getTimeStamp()) + 1);
        System.out.println("Mensagem - From: " + message.getFromId() + " To: " + message.getToId() + " Message: " + message.getContent() + " Relógio: " + this.timeStamp);

    }

    public void processMessage(Socket client) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        Gson gson = new Gson();
        String inputLine;
        while (true) {
            try {
                if (!((inputLine = in.readLine()) != null)) {

                    Event message = gson.fromJson(inputLine, Event.class);
                    receiveEvent(message);

                };
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (".".equals(inputLine)) {
                break;
            }
            System.out.println(inputLine);
        }

        in.close();
        client.close();

    }


    @Override
    public void run() {

        while (true) {

            try {

                Socket client = server.accept();

                new Thread(() -> {
                    try {
                        processMessage(client);
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
