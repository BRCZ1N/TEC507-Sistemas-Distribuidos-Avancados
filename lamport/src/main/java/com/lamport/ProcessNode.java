package com.lamport;

public enum ProcessNode {

    P1(1, "172.16.103.7", 6000),
    P2(2, "172.16.103.7", 6001),
    P3(3, "172.16.103.8", 6000);

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
