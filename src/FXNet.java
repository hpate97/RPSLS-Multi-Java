import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class FXNet extends Application {

    Stage myStage;
    Stage stageT = new Stage();
    BackgroundFill red = new BackgroundFill(Color.DARKRED, new CornerRadii(1), new Insets(0.0,0.0,0.0,0.0));
    BorderPane pane = new BorderPane();
    private NetworkConnection  conn =createServer();
    private TextArea messages = new TextArea();
    private Game gamePlay = new Game();
    //private TextArea roundWinnerT = new TextArea();
    private Text playersConnected = new Text();
    int numPlayers = 0;
    int playersInPlay = 0;
    int playerPlayed= 0;
    ArrayList<Integer> clients = new ArrayList<Integer>();

    private Parent createContent(Stage primaryStage) {
        //----------------------------
        myStage = primaryStage;
        primaryStage.setTitle("RPSLS SERVER");
        Text text = new Text("RPSLS");
        text.setFont(new Font("Times New Roman",100));
        pane.setBackground(new Background(red));
        //--------------------------------

        messages.setPrefHeight(200);
        playersConnected.setText("Number Players Connected: " + conn.numPlayers);
        playersConnected.setFont(new Font("Times New Roman",25));


        Label portLabel = new Label("Server is connected to port: 5555");
        Button offButton = new Button("Close Server");


        offButton.setOnAction(e->{
            try {
                conn.closeConn();
            }catch(Exception a) {
                System.out.println("trouble closing");
            }
            stageT.close();
            primaryStage.close();
        });



        VBox root = new VBox(10, text,portLabel, messages, playersConnected, offButton);
        root.setPrefSize(600, 600);

        root.setAlignment(Pos.CENTER);
        //root.setPrefSize(600, 600);
        pane.setCenter(root);

        return root;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        ProgressBar progressBar = new ProgressBar();
        Text text = new Text("Waiting on Players");
        ProgressIndicator progressIndicator = new ProgressIndicator();

        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.getChildren().addAll(progressIndicator);
        root.setAlignment(Pos.CENTER);
        VBox box = new VBox();
        box.getChildren().addAll(text,root);
        box.setAlignment(Pos.CENTER);
        BorderPane Pane = new BorderPane();
        Pane.setBackground(new Background(red));
        Pane.setCenter(box);
        Scene scene2 = new Scene(Pane, 400, 300);
        stageT.setTitle("JavaFX ProgressBar & ProgressIndicator (o7planning.org)");
        stageT.setScene(scene2);
        stageT.show();


        Scene scene = new Scene(pane,600,600);
        primaryStage.setScene(scene);
        createContent(primaryStage);
        //primaryStage.setScene(new Scene(createContent(primaryStage)));
        primaryStage.show();

    }

    @Override
    public void init() throws Exception{
        conn.startConn();
    }

    @Override
    public void stop() throws Exception{
        conn.closeConn();
        stageT.close();
    }


    private Server createServer() {
        return new Server(test-> {
            Platform.runLater(()->{
                messages.appendText(test.toString() + "\n");

                if(conn.numPlayers > numPlayers) {
                    numPlayers++;
                    try {
                        Integer sendNumber = numPlayers-1;
                        conn.send(numPlayers, sendNumber);
                        conn.sendAll(Integer.toString(numPlayers));
                    } catch(Exception a){
                        System.out.println("Did not send num players: " + numPlayers +":"+conn.numPlayers);
                    }
                    messages.appendText("Player " + numPlayers + " has connected\n");
                }

                if(numPlayers<=1){
                    ProgressBar progressBar = new ProgressBar();
                    Text text = new Text("Waiting on Players");
                    ProgressIndicator progressIndicator = new ProgressIndicator();

                    FlowPane root = new FlowPane();
                    root.setPadding(new Insets(10));
                    root.setHgap(10);
                    root.getChildren().addAll(progressIndicator);
                    root.setAlignment(Pos.CENTER);
                    VBox box = new VBox();
                    box.getChildren().addAll(text,root);
                    box.setAlignment(Pos.CENTER);
                    BorderPane Pane = new BorderPane();
                    Pane.setBackground(new Background(red));
                    Pane.setCenter(box);
                    Scene scene2 = new Scene(Pane, 400, 300);
                    stageT.setTitle("JavaFX ProgressBar & ProgressIndicator (o7planning.org)");
                    stageT.setScene(scene2);
                    stageT.show();
                }

                if(numPlayers >= 2) {
                    stageT.close();
                }

                playersConnected.setText("Number Players Connected: " + conn.numPlayers);


                if(test.toString().startsWith("t")) {
                    String[] tokens = test.toString().split(",");
                    clients.add(Integer.valueOf(tokens[1]));
                    playersInPlay++;
                }


                if(test.toString().startsWith("p")) {
                    String[] tokens = test.toString().split(",");
                    int playerNum = Integer.valueOf(tokens[1]) - 1;
                    conn.players.get(playerNum).played = tokens[2];
                    playerPlayed++;
                }

                if(test.toString().startsWith("x")) {
                    String[] tokens = test.toString().split(",");
                    clients.remove(Integer.valueOf(tokens[1]));
                    playersInPlay--;
                }



                if((playerPlayed != 0) && playerPlayed==playersInPlay) {
                    messages.appendText("in the if state\n");
                    int winner = gamePlay.roundWinner(clients.get(0), conn.players.get(clients.get(0) - 1).played, clients.get(1), conn.players.get(clients.get(1) - 1).played);
                    messages.appendText("WINNER: " + winner);
                    String msg = "Player " + winner + " has one the game";
                    try {
                        conn.send(msg, clients.get(0) - 1);
                        conn.send(msg, clients.get(1) - 1);
                    }catch(Exception e) {
                        System.out.println("Does not send the winner");
                    }
                    clients.clear();
                    playersInPlay = 0;
                    playerPlayed = 0;
                }
            });
        });
    }
}
