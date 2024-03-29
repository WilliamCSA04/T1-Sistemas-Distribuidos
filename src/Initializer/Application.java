package Initializer;

import Client.Player;
import Server.Game;

public class Application {
    
    public static void main(String[] args) {
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        
        Game game = new Game();
        game.addPlayerToTheGame(player1);
        game.addPlayerToTheGame(player2);
        game.start();
        while(game.play(game.getPlayerThatHasToPlay(), game.getPlayerThatHasToPlay().getActualBallsQuantity()) != 1){
            System.out.println("name: " + game.getPlayerThatHasToPlay().getName() + ", balls: " + game.getPlayerThatHasToPlay().getActualBallsQuantity());
        }
    }
    
}
