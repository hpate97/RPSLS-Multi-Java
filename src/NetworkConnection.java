import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

public abstract class NetworkConnection {

    private ConnThread connthread = new ConnThread();
    private Consumer<Serializable> callback;
    public ArrayList<ClientThread> players = new ArrayList<ClientThread>();
    //int numbersClicked = 0;
    int numPlayers = 0;


    public NetworkConnection(Consumer<Serializable> callback) {
        this.callback = callback;
        connthread.setDaemon(true);


    }

    public void startConn() throws Exception{
        connthread.start();
    }

    public void send(Serializable data, int i) throws Exception{
        players.get(i).out.writeObject(data);
    }

    public void sendAllByte(String data) {
        try {
            System.out.println("sending..");
            for(int i =0; i<numPlayers; i++) {
                players.get(i).out.writeObject(data);
            }
        }catch(Exception e) {
            System.out.println("Did not send to all players");
        }
    }

    public void sendAll(Serializable data) {
        try {
            System.out.println("sending..");
            for(int i =0; i<numPlayers; i++) {
                players.get(i).out.writeObject(data);
            }
        }catch(Exception e) {
            System.out.println("Did not send to all players");
        }
    }

    public void sendTo1(Serializable data) throws Exception{
        //connthread.out.writeObject(data);
        players.get(0).out.writeObject(data);
        //players.get(1).out.writeObject(data);
    }

    public void sendTo2(Serializable data) throws Exception{
        //connthread.out.writeObject(data);
        players.get(1).out.writeObject(data);
        //players.get(1).out.writeObject(data);
    }


    public void closeConn() throws Exception{
        connthread.closeServer();
    }

    abstract protected void setIP(String ip);
    abstract protected String getIP();
    abstract protected int getPort();
    abstract protected void setPort(int port);

    class ConnThread extends Thread{
        private ServerSocket server;
        //private ObjectOutputStream out;

        public void closeServer() throws IOException {
            server.close();
        }

        public void run() {
            try(ServerSocket server = new ServerSocket(5555);){

                this.server = server;
//				this.socket = socket;
                //this.out = out;
//				socket.setTcpNoDelay(true);

                while(true) {
                    ClientThread t1 = new ClientThread(server.accept());
                    //this.out = new ObjectOutputStream( t1.socket.getOutputStream());
                    t1.start();
                    players.add(t1);
                    numPlayers++;
                    System.out.println(players.size());
                }

            }
            catch(Exception e) {
                callback.accept("connection Closed");
            }
        }
    }

    class ClientThread extends Thread {
        ObjectOutputStream out;
        ObjectInputStream in;
        String read;
        Socket socket;
        Consumer<Serializable> callbackThread;
        boolean check = false;
        //boolean accounted = false;
        String played;

        ClientThread(Socket s){
            this.socket = s;
            //players.add(socket);
        }

        public void run() {
            //players.add(socket);
            try {
                ObjectOutputStream out = new ObjectOutputStream( socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                this.out= out;
                System.out.println("GOing in while loop");
                callback.accept("adding player...");

                while(true) {
                    String data =  in.readObject().toString();

                    this.read = data;
                    check = true;

                    callback.accept(data);
                }

            }
            catch(IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}

