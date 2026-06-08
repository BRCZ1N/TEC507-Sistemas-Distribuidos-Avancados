package com.lamport;

public enum ProcessNode {

    P1(1, "192.168.0.12", 5000),
    P2(2, "192.168.0.2", 5001),
    P3(3, "192.168.0.149", 5000);

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
