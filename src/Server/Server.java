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
    
    /**
    * Register Palyer.
    * To Receive a name and invoke a method of the registered player of server
    *
    * @param name player name for the register
    * @throws java.rmi.RemoteException
    */
    @Override
    public int registerPlayer(String name) throws RemoteException {
        boolean isThisPlayerAlreadyCreated = checkIfThereIsPlayerWithSameName(name);
        if(isThisPlayerAlreadyCreated){
            return -1;
        }
        Register register = new Register();
        boolean registerWasNotSuccessful = !register.registerPlayer(name);
        if(registerWasNotSuccessful){
            return -2;
        }
        boolean thereWasNoMoreSpaceForPlayers = !addRegisterIntoNullSpace(register);
        if(thereWasNoMoreSpaceForPlayers){
            return -3;
        }
        
        
        return register.getUserID();
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
    
    private Game addPlayerToTheGameWhenTryStart(Register register){
        for (Game game : gameList) {
            boolean isGameDidNotHaveStarted = !game.isGameReadyToStart();
            if(isGameDidNotHaveStarted){
                game.addPlayerToTheGame(register.getPlayer());
                return game;
            }
            
            
        }
        return null;
    }

    @Override
    public String getBoard(int gameID) throws RemoteException {
        Game game = findGameByID(gameID);
        return game.boardAsString();
    }

    @Override
    public int finishSession(int userID) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int sendPlay(int gameID, int userID, int rollTimes) throws RemoteException {
        Game game = findGameByID(gameID);
        if(game == null){
            return -1;
        }
        Register register = findRegisterByID(userID);
        if(register == null){
            return -2;
        }
        return game.play(register.getPlayer(), rollTimes);
    }
    
    @Override
    public int tryStart(int userID) throws RemoteException{
        Register register = findRegisterByID(userID);
        boolean registerWasNotFind = register == null;
        if(registerWasNotFind){
            return -3;
        }
        Game game = addPlayerToTheGameWhenTryStart(register);
//        boolean thereWasNotHowToAddPlayer = game == null;
//        if(thereWasNotHowToAddPlayer){
//            return -2;
//        }
        gameList[0].addPlayerToTheGame(register.getPlayer());
        return gameList[0].start();
    }

    private Register findRegisterByID(int userID) {
        for (Register register : registerList) {
            boolean isThisTheCorrectRegister = register.getUserID() == userID;
            if(isThisTheCorrectRegister){
                return register;
            }          
        }
        return null;
    }
    
    private Game findGameByID(int gameID){
        for (Game game : gameList) {
            boolean isThisTheCorrectGame = game.getGameID() == gameID;
            if(isThisTheCorrectGame){
                return game;
            }
        }
        return null;
    }

    @Override
    public boolean itsMyTurn(int userID, int gameID) throws RemoteException {
        Register register = findRegisterByID(userID);
        Game game = findGameByID(gameID);
        return register.getPlayer().isPlayTurn();
    }

    @Override
    public String playerStatus(int userID) throws RemoteException {
        Register register = findRegisterByID(userID);
        String playerStatus = register.getPlayer().playerStatus();
        return playerStatus;
    }
    
    

    

}
