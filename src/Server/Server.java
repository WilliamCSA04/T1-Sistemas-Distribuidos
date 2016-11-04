package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IServer {

    private static final long SERIAL_VERSION_UID = 1L;
    private Game[] gameList;
    private Register[] registerList;
    private static final int MAX_GAMES_RUNNING = 50;
    private static final int MAX_PLAYERS_ON_SERVER = MAX_GAMES_RUNNING * 2;

    public Server() throws RemoteException {
        gameList = new Game[MAX_GAMES_RUNNING];
        for (int actual = 0; actual < MAX_GAMES_RUNNING; actual++) {
            gameList[actual] = new Game();
        }
        registerList = new Register[MAX_PLAYERS_ON_SERVER];
    }

    /**
     * Register Palyer. To receive a name and invoke a method of the registered
     * player of server
     *
     * @param name player name for the register
     * @throws java.rmi.RemoteException
     */
    @Override
    public int registerPlayer(String name) throws RemoteException {
        boolean isThisPlayerAlreadyCreated = checkIfThereIsPlayerWithSameName(name);
        if (isThisPlayerAlreadyCreated) {
            return -1;
        }
        Register register = new Register();
        boolean registerWasNotSuccessful = !register.registerPlayer(name);
        if (registerWasNotSuccessful) {
            return -2;
        }
        boolean thereWasNoMoreSpaceForPlayers = !addRegisterIntoNullSpace(register);
        if (thereWasNoMoreSpaceForPlayers) {
            return -3;
        }

        return register.getUserID();
    }

    /**
     * Check if there is player with the same name. To receive a name and invoke
     * a method of the registered player of server and check if there is player
     * with the same name
     *
     * @param name player name for the register
     * @throws java.rmi.RemoteException
     */
    private boolean checkIfThereIsPlayerWithSameName(String name) throws RemoteException {
        for (Register register : registerList) {
            if (register == null) {
                return false;
            }
            String playerName = register.getPlayer().getName();
            boolean theNewPlayerHaveTheSameName = playerName.equals(name);
            if (theNewPlayerHaveTheSameName) {
                return false;
            }
        }
        return true;
    }

    /**
     * Add register into null space. To registered a register and check if there
     * is register into null space
     *
     * @param newRegister new register for the register
     * @throws java.rmi.RemoteException
     */
    private boolean addRegisterIntoNullSpace(Register newRegister) throws RemoteException {
        for (int actual = 0; actual < MAX_PLAYERS_ON_SERVER; actual++) {
            boolean isEmptySpace = registerList[actual] == null;
            if (isEmptySpace) {
                registerList[actual] = newRegister;
                return true;
            }
        }
        return false;
    }

    /**
     * Add a player to game when try to start . To registered a register and 
     * check if there was started a game
     *
     * @param register register for the register
     * @throws java.rmi.RemoteException
     */
    private Game addPlayerToTheGameWhenTryStart(Register register) throws RemoteException {
        for (Game game : gameList) {
            boolean isGameDidNotHaveStarted = !game.isGameReadyToStart();
            if (isGameDidNotHaveStarted) {
                game.addPlayerToTheGame(register.getPlayer());
                return game;
            }

        }
        return null;
    }

    /**
     * Add a player to game when try to start . To registered a register and 
     * check if there was started a game
     *
     * @param gameID game id for the register in each
     * @throws java.rmi.RemoteException
     */
    @Override
    public String getBoard(int gameID) throws RemoteException {
        Game game = findGameByID(gameID);
        return game.boardAsString();
    }

    /**
    * Finish session. Calls the method terminating the match freeing server 
    * resources. 
    *
    * @param userID user id chosen
    * @return code boolean
    * @throws java.rmi.RemoteException
    */
    @Override
    public boolean finishSession(int userID) throws RemoteException {

        for (int actual = 0; actual < MAX_PLAYERS_ON_SERVER; actual++) {
            Register actualRegister = registerList[actual];
            boolean isThisTheCorrectRegister = actualRegister.getUserID() == userID;
            if(isThisTheCorrectRegister){
                registerList[actual] = null;
                return true;
            }
        }
        return false;
    }

    /**
    * Sending play
    * Calls the method that applies to move to the chosen player.
    *
    * @param gameID game id of the play
    * @param userID user id of the chosen player
    * @param rollTimes roll times of the play
    * @return play with its register and roll times for the session
    * @throws java.rmi.RemoteException
    */
    @Override
    public int sendPlay(int gameID, int userID, int rollTimes) throws RemoteException {
        Game game = findGameByID(gameID);
        if (game == null) {
            return -4;
        }
        Register register = findRegisterByID(userID);
        if (register == null) {
            return -5;
        }
        return game.play(register.getPlayer(), rollTimes);
    }

    @Override
    public int tryStart(int userID, int gameID) throws RemoteException {
        Register register = findRegisterByID(userID);
        boolean registerWasNotFind = register == null;
        if (registerWasNotFind) {
            return -2;
        }
        Game game = findGameByID(gameID);
        return game.start();
    }

    private Register findRegisterByID(int userID) throws RemoteException {
        for (Register register : registerList) {
            boolean isThisTheCorrectRegister = register.getUserID() == userID;
            if (isThisTheCorrectRegister) {
                return register;
            }
        }
        return null;
    }

    private Game findGameByID(int gameID) throws RemoteException {
        for (Game game : gameList) {
            boolean isThisTheCorrectGame = game.getGameID() == gameID;
            if (isThisTheCorrectGame) {
                return game;
            }
        }
        return null;
    }

    @Override
    public boolean itsMyTurn(int userID) throws RemoteException {
        Register register = findRegisterByID(userID);
        return register.getPlayer().isPlayTurn();
    }

    @Override
    public String playerStatus(int userID) throws RemoteException {
        Register register = findRegisterByID(userID);
        String playerStatus = register.getPlayer().playerStatus();
        return playerStatus;
    }

    @Override
    public int requestToEnterInGame(int userID) throws RemoteException {
        Register register = findRegisterByID(userID);
        Game game = addPlayerToTheGameWhenTryStart(register);
        boolean wasNotAbleToPutThisRegisterToPlay = game == null;
        if(wasNotAbleToPutThisRegisterToPlay){
            return -1;
        }
        return game.getGameID();
    }

    @Override
    public boolean checkForForceGameOver(int gameID) throws RemoteException {
        Game game = findGameByID(gameID);
        return game.checkForForceGameOver();
    }

}
