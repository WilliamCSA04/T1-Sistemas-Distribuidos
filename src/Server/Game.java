package Server;

import Client.Player;
import Exceptions.DiceException;
import Speculate.Board;
import Speculate.Dice;
import java.util.List;

public class Game {

    private Player player1;
    private Player player2;
    private Board board;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        player1.setPlayTurn(true);
        player2.setPlayTurn(false);
        this.board = new Board();
    }

    private boolean isPlayerTurn(Player player) {
        boolean isPlayerTurn = player.isPlayTurn();
        return isPlayerTurn;
    }

    public int play(Player player, int rollDiceTimes) {

        boolean isNotPlayerAllowedToPlay = !checkIfPlayerIsAllowedToPlay(player);
        if (isNotPlayerAllowedToPlay) {
            return -1;
        }
        try {
            List<Integer> diceResults = rollDice(rollDiceTimes, player);
            if(isGameOver(player)){
                return 1;
            }
            afterTurn();
            return 0;
        } catch (DiceException ex) {
            return -1;
        }
        

    }
    
    public Player getPlayerThatHasToPlay(){
        boolean isFirstPlayersTurn = player1.isPlayTurn();
        if(isFirstPlayersTurn){
            return player1;
        }
        return player2;
    }
    
    private boolean isGameOver(Player player){
        int leftBalls = player.getActualBallsQuantity();
        boolean playerDoesNotHaveBalls = leftBalls == 0;
        if(playerDoesNotHaveBalls){
            System.out.println("Vencedor: " + player.getName());
            return true;
        }
        return false;
    }

    private List<Integer> rollDice(int rollDiceTimes, Player player) throws DiceException {
        List<Integer> diceResults = Dice.rollDice(rollDiceTimes, player);
        for (Integer diceResult : diceResults) {
            updatePlayersBalls(diceResult, player);
        }
        return diceResults;
    }

    private void updatePlayersBalls(int diceResult, Player player) {
        if (diceResult == 6) {
            reducePlayerBallsQuantityAfterPlay(player);
        } else {
            boolean spaceWasOccupied = changeBoardAfterPlay(diceResult);
            if (spaceWasOccupied) {
                addPlayerBallsQuantityAfterPlay(player);
            } else {
                reducePlayerBallsQuantityAfterPlay(player);
            }
        }
    }

    private void reducePlayerBallsQuantityAfterPlay(Player player) {
        int actualBallsQuantity = player.getActualBallsQuantity();
        actualBallsQuantity--;
        player.setActualBallsQuantity(actualBallsQuantity);
    }

    private void addPlayerBallsQuantityAfterPlay(Player player) {
        int actualBallsQuantity = player.getActualBallsQuantity();
        actualBallsQuantity++;
        player.setActualBallsQuantity(actualBallsQuantity);
    }

    private boolean changeBoardAfterPlay(int space) {
        isAValueArgument(space);
        space--;
        boolean[] board = this.board.getBoard();
        boolean actualValue = board[space];
        board[space] = !actualValue;
        return actualValue;
    }

    private void isAValueArgument(int argument) {
        boolean isOutOfRange = argument > 5 || argument < 1;
        if (isOutOfRange) {
            throw new IllegalArgumentException();
        }
    }

    private boolean checkIfPlayerIsAllowedToPlay(Player player) {
        return isPlayerTurn(player);
    }

    public void afterTurn() {
        switchPlayerTurn();
    }

    private void switchPlayerTurn() {
        boolean firstPlayerTurn = player1.isPlayTurn();
        boolean secondPlayerTurn = player2.isPlayTurn();
        player1.setPlayTurn(!firstPlayerTurn);
        player2.setPlayTurn(!secondPlayerTurn);
    }

}
