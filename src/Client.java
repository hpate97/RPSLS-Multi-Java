import java.io.Serializable;
import java.util.function.Consumer;

public class Client extends NetworkConnection {

    private String ip;
    private int port;

    public Client(Consumer<Serializable> callback) {
        super(callback);
        //this.client = 0;
        //this.ip = ip;
        //this.port = port;
    }

    @Override
    protected String getIP() {
        // TODO Auto-generated method stub
        return this.ip;
    }

    @Override
    protected int getPort() {
        // TODO Auto-generated method stub
        return this.port;
    }

    @Override
    protected void setPort(int port) {
        // TODO Auto-generated method stub
        this.port = port;
    }

    @Override
    protected void setIP(String ip) {
        // TODO Auto-generated method stub
        this.ip = ip;
    }

}
