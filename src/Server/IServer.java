/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author realnetwoking
 */
public interface IServer extends Remote{
    
    public int registerPlayer(String name) throws RemoteException;
    public String getBoard(int gameID) throws RemoteException;
    public int finishSession(int userID) throws RemoteException;
    public int sendPlay(int gameID, int userID, int rollTimes) throws RemoteException;
    public int tryStart(int userID) throws RemoteException;
    public boolean itsMyTurn(int userID) throws RemoteException;
    public String playerStatus(int userID) throws RemoteException;
}
