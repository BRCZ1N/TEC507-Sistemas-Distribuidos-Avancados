package com.lamport;

import java.io.Serializable;

public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    private long fromId;
    private long toId;
    private long clock;
    private String content;

    public Event(long fromId, long toId, long clock, String content) {

        this.fromId = fromId;
        this.toId = toId;
        this.clock = clock;
        this.content = content;

    }

    public long getFromId() {
        return this.fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public long getToId() {
        return this.toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    public long getClock() {
        return this.clock;
    }

    public void setClock(long clock) {
        this.clock = clock;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}