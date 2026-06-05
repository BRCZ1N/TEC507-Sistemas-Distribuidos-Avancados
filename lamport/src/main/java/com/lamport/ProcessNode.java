package com.lamport;

public enum ProcessNode {

    P1(1, "localhost", 5000),
    P2(2, "localhost", 5001),
    P3(3, "localhost", 5002);

    private final int id;
    private final String host;
    private final int port;

    ProcessNode(int id, String host, int port) {
        this.id = id;
        this.host = host;
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
