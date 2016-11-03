package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IServer {
    
    private static final long SERIAL_VERSION_UID = 1L;
    private Game[] gameList;
    private static final int MAX_GAMES_RUNNING = 50;
            
    public Server() throws RemoteException {
        gameList = new Game[MAX_GAMES_RUNNING];
        for(int actual = 0; actual < MAX_GAMES_RUNNING; actual++){
            gameList[actual] = new Game();
        }
    }

    @Override
    public int registerPlayer(String name) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
