package com.lamport;

public class Event {

    private long fromId;
    private long toId;
    private long timeStamp;
    private String content;

    public Event(long fromId, long toId, long timeStamp, String content) {

        this.fromId = fromId;
        this.toId = toId;
        this.timeStamp = timeStamp;
        this.content = content;

    }

    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}