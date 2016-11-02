package Speculate;

public class Board {
    
    private boolean[] board;
    private String spaces;
    private String ballsOutOfGame;
    
    public Board(){
        populateInitialSpaces();
    }
    
    private void populateInitialSpaces(){
        board = new boolean[]{true, false, true, false, true};
        spaces = "0_0_0";
        ballsOutOfGame = "_________________________________";
    }

    public boolean[] getBoard() {
        return board;
    }

    public void setOneSpaceIntoBoard(int space) {
        board[space] = !board[space];
    }

    public void setSpaces(String spaces) {
        this.spaces = spaces;
    }

    public void setBallsOutOfGame(String ballsOutOfGame) {
        this.ballsOutOfGame = ballsOutOfGame;
    }
    
    public String returnBoardAsAString() {
       return spaces + "\n" + ballsOutOfGame;
    }
    
    
    
    
}
