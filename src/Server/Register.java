/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Client.Player;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author realnetwoking
 */
public class Register {

    private volatile boolean state;
    private Player player;
    private int userID;

    public Register() {
        state = false;
    }

    public boolean registerPlayer(String name) {
        if (state) {
            return false;
        }
        player = new Player(name);
        userID = generateID();
        state = true;

        return true;
    }


    private int generateID() {
        Date date = new Date();
        UUID myID;
        do {
            myID = UUID.randomUUID();
        } while (myID.hashCode() == -1 || myID.hashCode() == -2);

        return myID.hashCode();
    }
    
    public Player getPlayer(){
        return player;
    }
    
    public int getUserID(){
        return userID;
    }
    
    public int gameOver() {
        int status = -1;
        if (state == true) {
            this.state = false;
            this.userID = 0;
            this.player = player;
            status = 0;
        }
        return status;
    }

}
