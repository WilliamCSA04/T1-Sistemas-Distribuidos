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
    
    String name;
    boolean playTurn;
    
    public Player(String name){
        this.name = name;
    }

    public boolean isPlayTurn() {
        return playTurn;
    }

    public void setPlayTurn(boolean playTurn) {
        this.playTurn = playTurn;
    }
    
    
}
