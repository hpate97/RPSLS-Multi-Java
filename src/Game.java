public class Game {
    int player1Points = 0;
    int player2Points = 0;
    boolean isWinner = false;

    public int roundWinner(int player1Num, String player1in, int player2Num, String player2in) {
        int winner = 0;
        String player1 = player1in.intern();
        String player2 = player2in.intern();



        if(player1 == "rock" && player2=="rock") {
            winner = -1;
        }
        if(player1 == "rock" && player2=="paper") {
            winner = player2Num;
            player2Points++;
        }
        if(player1 == "rock" && player2=="scissors") {
            System.out.println("ifffff");
            winner = player1Num;
            player1Points++;
        }
        if(player1 == "rock" && player2=="lizard") {
            winner = player1Num;
            player1Points++;
        }
        if(player1 == "rock" && player2=="spock") {
            winner = player2Num;
            player2Points++;
        }

        if(player1 == "paper" && player2=="rock") {
            winner = player1Num;
            player1Points++;
        }
        if(player1 == "paper" && player2=="paper") {
            winner = -1;
        }
        if(player1 == "paper" && player2=="scissors") {
            winner = player2Num;
            player2Points++;
        }
        if(player1 == "paper" && player2=="lizard") {
            winner = player2Num;
            player2Points++;
        }
        if(player1 == "paper" && player2=="spock") {
            winner = player1Num;
            player1Points++;
        }

        if(player1 == "scissors" && player2=="rock") {
            winner = player2Num;
            player2Points++;
        }
        if(player1 == "scissors" && player2=="paper") {
            winner = player1Num;
            player1Points++;
        }
        if(player1 == "scissors" && player2=="scissors") {
            winner = -1;
        }
        if(player1 == "scissors" && player2=="lizard") {
            winner = player1Num;
            player1Points++;
        }
        if(player1 == "scissors" && player2=="spock") {
            winner = player2Num;
            player2Points++;
        }

        if(player1 == "lizard" && player2=="rock") {
            winner = player2Num;
            player2Points++;
        }
        if(player1 == "lizard" && player2=="paper") {
            winner = player1Num;
            player1Points++;
        }
        if(player1 == "lizard" && player2=="scissors") {
            winner = player2Num;
            player2Points++;
        }
        if(player1 == "lizard" && player2=="lizard") {
            winner = -1;
        }
        if(player1 == "lizard" && player2=="spock") {
            winner = player1Num;
            player1Points++;
        }

        if(player1 == "spock" && player2=="rock") {
            winner = player1Num;
            player1Points++;
        }
        if(player1 == "spock" && player2=="paper") {
            winner = player2Num;
            player2Points++;
        }
        if(player1 == "spock" && player2=="scissors") {
            winner = player1Num;
            player1Points++;
        }
        if(player1 == "spock" && player2=="lizard") {
            winner = player2Num;
            player1Points++;
        }
        if(player1 == "spock" && player2=="spock") {
            winner = -1;
        }


        return winner;
    }

    public boolean checkWinner() {
        if(player1Points==3 || player2Points==3) {
            isWinner = true;
        }

        if(isWinner) {
            return true;
        }else {
            return false;
        }
    }

    public String winner() {
        if(player1Points ==  3) {
            return "player1";
        }
        if(player2Points == 3) {
            return "player2";
        }

        return null;
    }

    public void reset() {
        player1Points = 0;
        player2Points = 0;
        isWinner = false;
    }

}
