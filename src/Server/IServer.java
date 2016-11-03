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
    
    public boolean registerPlayer(String name) throws RemoteException;
    public String getBoard(int index) throws RemoteException;
    public int finishSession(int userID) throws RemoteException;
    public int sendPlay(int index, int rollTimes) throws RemoteException;
    public boolean tryStart(int index) throws RemoteException;
}
