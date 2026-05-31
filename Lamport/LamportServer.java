package lamport;

public class LamportServer extends Thread{

    private long processId;
    private AtomicLong timeStamp;
    private int port;
    private ServerSocket server;

    public LamportServer(int port, long processId){

        this.port = port;
        this.timeStamp = new AtomicLong(0);
        this.processId = processId;
        this.server = new ServerSocket(this.port);
        start();

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

        return this.processId;

    }

    public void setProcessId(long processId){

        this.processId = processId;

    }

    public void localEvent(){

        timeStamp.incrementAndGet();

    }
    
    public void sendEvent(Event message){

        timeStamp.incrementAndGet();
        System.out.println("Mensagem - From: "+ this.id + " To: "+ toId +" Message: " + contentMessage + " Relógio: "+ this.timeStamp);

    }

    public void receiveEvent(Event message){

        timeStamp.updateAndGet(current -> Math.max(current, receivedTimeStamp) + 1);
        System.out.println("Mensagem - From: "+ fromId + " To: "+ toId +" Message: " + contentMessage + " Relógio: "+ this.timeStamp);

    }

    public void processMessage(Socket client){

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (".".equals(inputLine)) {
                    break;
            }
                out.println(inputLine);
        }

        ObjectMapper mapper = new ObjectMapper();
        Event message = mapper.readValue(inputLine, Event.class);
        receiveEvent(message)

        in.close();
        clientSocket.close();

    }


    @Override
    public void run() {

        while (true) {

            try{

                Socket client = server.accept();

                 new Thread(() -> {
                    processMessage(client);
                 }).start();

            }catch(IOException e){

                e.printStackTrace();

            } 

        }

    }
    
}
