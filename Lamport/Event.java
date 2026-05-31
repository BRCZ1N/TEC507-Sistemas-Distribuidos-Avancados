
public class Event {

    private long fromId;
    private long toId;
    private long timeStamp;
    private String content;
    private MessageType type;

    public Event(long fromId, long toId, String content, MessageType type) {
        this.fromId = fromId;
        this.toId = toId;
        this.timeStamp = 0;
        this.content = content;
        this.type = type;

    }

    public Event(long fromId, long toId, int timeStamp, String content, MessageType type) {
        this.fromId = fromId;
        this.toId = toId;
        this.timeStamp = timeStamp;
        this.content = content;
        this.type = type;

    }

}