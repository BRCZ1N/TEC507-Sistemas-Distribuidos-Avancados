
public class Event {

    private int fromId;
    private int toId;
    private int timeStamp;
    private String content;
    private MessageType type;

    public Event(int fromId, int toId, String content, MessageType type) {
        this.fromId = fromId;
        this.toId = toId;
        this.timeStamp = 0;
        this.content = content;
        this.type = type;

    }

    public Event(int fromId, Integer toId, int timeStamp, String content, MessageType type) {
        this.fromId = fromId;
        this.toId = toId;
        this.timeStamp = timeStamp;
        this.content = content;
        this.type = type;

    }

}