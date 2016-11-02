package Server;

import Client.Client;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author realnetwoking
 */
public class Server extends UnicastRemoteObject implements IServer{
    
    private static final long SERIAL_VERSION_UID = 1L;
    private Client[] clientList;
    private int numberOfGames;
    
    public Server(int numberOfGames) throws RemoteException{
        this.numberOfGames = numberOfGames;
        clientList = new Client[numberOfGames];
        for (int i = 0; i < numberOfGames; i++) {
            clientList[i] = new Client();
}
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

    @Override
    public String getBoard(int userID) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int finishSession(int userID) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int sendPlay(int userID) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
