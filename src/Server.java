import java.io.Serializable;
import java.util.function.Consumer;

public class Server extends NetworkConnection {

    private int port;
    private String ip;

    public Server(Consumer<Serializable> callback) {
        super(callback);
        //players.get(0).callbackThread = callbackThread;
        // TODO Auto-generated constructor stub
        //this.port = port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    @Override
    protected int getPort() {
        // TODO Auto-generated method stub
        return port;
    }

    @Override
    protected String getIP() {
        // TODO Auto-generated method stub
        return this.ip;
    }

    @Override
    protected void setIP(String ip) {
        // TODO Auto-generated method stub
        this.ip = ip;
    }

}
