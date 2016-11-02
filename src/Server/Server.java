/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Client.Client;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author realnetwoking
 */
public class Server extends UnicastRemoteObject implements IServer{
    
    private static final long serialVersionUID = 1L;
    private Client[] clientList;
    private int numberOfGames;
    
    public Server(int numberOfGames) throws RemoteException{
        this.numberOfGames = numberOfGames;
    }
    
    public int registerPlayer(String name) throws RemoteException {
        int status=0;
        boolean exists = false;
        
        for (Client client : clientList) {
            if(name.equals(client.getUserName()))
                exists = true;
        }
        
        
        if(!exists){
            for (Client client : clientList) {
                status = client.registerPlayer(name);  // if sucessfull returns userID
                if(status != -1) break;
            }
        }else{
            status = -2;
        }
        return status;
}
    
}
