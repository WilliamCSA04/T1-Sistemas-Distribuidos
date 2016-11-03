package Server;

import Client.Player;
import Exceptions.DiceException;
import Server.Helper.GameHelper;
import Speculate.Board;
import Speculate.Dice;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Game {

    private Player player1;
    private Player player2;
    private Board board;
    private boolean gameReadyToStart;
    private int gameID;

    public Game() {
        this.player1 = null;
        this.player2 = null;
        this.gameReadyToStart = false;
        this.board = new Board();
    }
    
    public int start(){
        gameReadyToStart = player1 != null && player2 != null;
        if(gameReadyToStart){ 
            
            gameReadyToStart = true;
            player1.setPlayTurn(true);
            player2.setPlayTurn(false);
            gameID = generateID();
            return gameID;
        }
        return -1;
    }

    public int getGameID() {
        return gameID;
    }
    
   
    
    private int generateID() {
        Date date = new Date();
        UUID myID;
        do {
            myID = UUID.randomUUID();
        } while (myID.hashCode() == -1 || myID.hashCode() == -2);

        return myID.hashCode();
    }

    public boolean isGameReadyToStart() {
        return gameReadyToStart;
    }
    
    public String boardAsString(){
        return board.returnBoardAsString();
    }
    
    public boolean addPlayerToTheGame(Player player){
        boolean existPlayerOne = player1 != null;
        if(existPlayerOne){
            boolean existPlayerTwo = player2 != null;
            if(existPlayerTwo){
                return false;
            }else{
                boolean isTheNewPlayerEqualsToTheFirstPlayer = checkIfThePlayersAreEquals(player);
                if(isTheNewPlayerEqualsToTheFirstPlayer){
                    return false;
                }
                player2 = player;
                return true;
            }
        }else{
            player1 = player;
            return true;
        }
    }
    
    private boolean checkIfThePlayersAreEquals(Player player){
        boolean isTheNewPlayerTheSameObject = player.equals(player1);
        return isTheNewPlayerTheSameObject;
    }

    private boolean isPlayerTurn(Player player) {
        boolean isPlayerTurn = player.isPlayTurn();
        return isPlayerTurn;
    }

    public int play(Player player, int rollDiceTimes) {
        if(!gameReadyToStart){
            return -2;
        }
        boolean isNotPlayerAllowedToPlay = !checkIfPlayerIsAllowedToPlay(player);
        if (isNotPlayerAllowedToPlay) {
            return -1;
        }
        try {
            rollDice(rollDiceTimes, player);
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
            return true;
        }
        return false;
    }

    private void rollDice(int rollDiceTimes, Player player) throws DiceException {
        List<Integer> diceResults = Dice.rollDice(rollDiceTimes, player);
        for (Integer diceResult : diceResults) {
            updatePlayersBalls(diceResult, player);
        }
    }

    private void updatePlayersBalls(int diceResult, Player player) {
        if (diceResult == 6) {
            reducePlayerBallsQuantityAfterPlay(diceResult, player);
        } else {
            boolean spaceWasOccupied = changeBoardAfterPlay(diceResult);
            if (spaceWasOccupied) {
                addPlayerBallsQuantityAfterPlay(diceResult, player);
            } else {
                reducePlayerBallsQuantityAfterPlay(diceResult, player);
            }
        }
    }

    private void reducePlayerBallsQuantityAfterPlay(int diceResult, Player player) {
        int actualBallsQuantity = player.getActualBallsQuantity();
        actualBallsQuantity--;
        player.setActualBallsQuantity(actualBallsQuantity);
        boolean haveToUpdateBallsOuOfGame = diceResult == 6;
        if(haveToUpdateBallsOuOfGame){
            updateBallsOutOfGame();
        }else{
            updateSpacesForReduce(diceResult);
        }
        
    }
    
    private void updateSpacesForAdd(int diceResult){
        String spaces = board.getSpaces();
        spaces = GameHelper.updateSpacesForAddHelper(diceResult, spaces);
        board.setSpaces(spaces);
    }
    
    private void updateSpacesForReduce(int diceResult){
        String spaces = board.getSpaces();
        spaces = GameHelper.updateSpacesForReduceHelper(diceResult, spaces);
        board.setSpaces(spaces);
    }
    
    private void updateBallsOutOfGame(){
        String ballsOutOfGame = board.getBallsOutOfGame();
        ballsOutOfGame = GameHelper.updateBallsOutOfGameHelper(ballsOutOfGame);
        board.setBallsOutOfGame(ballsOutOfGame);
    }

    private void addPlayerBallsQuantityAfterPlay(int diceResult, Player player) {
        int actualBallsQuantity = player.getActualBallsQuantity();
        actualBallsQuantity++;
        player.setActualBallsQuantity(actualBallsQuantity);
        updateSpacesForAdd(diceResult);
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
