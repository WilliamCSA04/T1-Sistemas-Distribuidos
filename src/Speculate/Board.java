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
    
    /**
     * This method must be use to see if one specific board space.
     * @param space Number of the space on the board. The valid range is 0 until 4, out of this range throws a IllegalArgumentException
     * @return return true if the space was occupied or false if was empty
     */
    
    
}
