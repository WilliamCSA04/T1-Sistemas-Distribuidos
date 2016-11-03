package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IServer {
    
    private static final long SERIAL_VERSION_UID = 1L;
    private Game[] gameList;
    private Register[] registerList;
    private static final int MAX_GAMES_RUNNING = 50;
    private static final int MAX_PLAYERS_ON_SERVER = MAX_GAMES_RUNNING*2;
            
    public Server() throws RemoteException {
        gameList = new Game[MAX_GAMES_RUNNING];
        for(int actual = 0; actual < MAX_GAMES_RUNNING; actual++){
            gameList[actual] = new Game();
        }
        registerList = new Register[MAX_PLAYERS_ON_SERVER];
    }

    @Override
    public boolean registerPlayer(String name) throws RemoteException {
        boolean isThisPlayerAlreadyCreated = checkIfThereIsPlayerWithSameName(name);
        if(isThisPlayerAlreadyCreated){
            return false;
        }
        Register register = new Register();
        boolean registerWasNotSuccessful = !register.registerPlayer(name);
        if(registerWasNotSuccessful){
            return false;
        }
        boolean thereWasNoMoreSpaceForPlayers = !addRegisterIntoNullSpace(register);
        if(thereWasNoMoreSpaceForPlayers){
            return false;
        }
        boolean thereWasNotHowToAddPlayer = !addPlayerToTheGameAfterRegister(register);
        if(thereWasNotHowToAddPlayer){
            return false;
        }
        
        return true;
    }
    
    private boolean checkIfThereIsPlayerWithSameName(String name){
        for (Register register : registerList) {
            if(register == null){
                return false;
            }
            String playerName = register.getPlayer().getName();
            boolean theNewPlayerHaveTheSameName = playerName.equals(name);
            if(theNewPlayerHaveTheSameName){
                return false;
            }
        }
        return true;
    }
    
    private boolean addRegisterIntoNullSpace(Register newRegister){
        for (int actual = 0; actual < MAX_PLAYERS_ON_SERVER; actual++) {
            boolean isEmptySpace = registerList[actual] == null;
            if(isEmptySpace){
                registerList[actual] = newRegister;
                return true;
            }
        }
        return false;
    }
    
    private boolean addPlayerToTheGameAfterRegister(Register register){
        for (Game game : gameList) {
            boolean isGameDidNotHaveStarted = !game.isGameReadyToStart();
            if(isGameDidNotHaveStarted){
                game.addPlayerToTheGame(register.getPlayer());
                return true;
            }
            
            
        }
        return false;
    }

    @Override
    public String getBoard(int index) throws RemoteException {
        if(index >= MAX_GAMES_RUNNING){
            throw new IllegalArgumentException();
        }
        return gameList[index].boardAsString();
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
