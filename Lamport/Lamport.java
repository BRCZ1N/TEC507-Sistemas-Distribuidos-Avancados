public class Lamport extends Thread{

    private long processId;
    private AtomicLong timeStamp;
    private int port;
    private ServerSocket server;

    public Lamport(int port, long processId){

        this.port = port;
        this.timeStamp = new AtomicLong(0);
        this.processId = processId;
        this.server = new ServerSocket(this.port);

    }

    public AtomicLong getTimeStamp(){

        return this.timeStamp;

    }

    public void setTimeStamp(AtomicLong timeStamp){

        this.timeStamp = timeStamp;

    }

    public int getPort(){

        return this.port;

    }

    public void setPort(int port){

        this.port = port;

    }

    public long getProcessId(){

        return this.id;

    }

    public void setProcessId(long processId){

        this.id = id;

    }

    public void localEvent(){

        timeStamp.incrementAndGet();

    }
    
    public void sendEvent(long toId, String contentMessage){

        timeStamp.incrementAndGet();
        System.out.println("Mensagem - From: "+ this.id + " To: "+ toId +" Message: " + contentMessage + " Relógio: "+ this.timeStamp);

    }

    public void receiveEvent(long fromId, long toId, long receivedTimeStamp, String contentMessage){

        timeStamp.updateAndGet(current -> Math.max(current, receivedTimeStamp) + 1);
        System.out.println("Mensagem - From: "+ fromId + " To: "+ toId +" Message: " + contentMessage + " Relógio: "+ this.timeStamp);

    }

    @Override
    public void run() {

        while (true) {

            try{

                Socket client = server.accept();

            }catch(IOException e){

                e.printStackTrace();

            }
            

        }

    }
    
}
