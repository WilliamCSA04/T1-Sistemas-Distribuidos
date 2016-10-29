/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

/**
 *
 * @author realnetwoking
 */
public class Player {
    
    private String name;
    private boolean playTurn;
    private static final int INITIAL_BALLS_QUANTITY = 15;
    private int actualBallsQuantity;
    
    public Player(String name){
        this.name = name;
        actualBallsQuantity = INITIAL_BALLS_QUANTITY;
    }

    public boolean isPlayTurn() {
        return playTurn;
    }

    public void setPlayTurn(boolean playTurn) {
        this.playTurn = playTurn;
    }

    public int getActualBallsQuantity() {
        return actualBallsQuantity;
    }

    public void setActualBallsQuantity(int actualBallsQuantity) {
        this.actualBallsQuantity = actualBallsQuantity;
    }

    public String getName() {
        return name;
    }
    

    
    
    
}
