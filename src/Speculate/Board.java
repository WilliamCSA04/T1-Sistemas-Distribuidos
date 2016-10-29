package Speculate;

public class Board {
    
    boolean spaces[];
    
    public Board(){
        populateInitialSpaces();
    }
    
    private void populateInitialSpaces(){
        spaces = new boolean[]{true, false, true, false, true};
    }
    
    /**
     * This method must be use to see if one specific board space.
     * @param space Number of the space on the board. The valid range is 0 until 4, out of this range throws a IllegalArgumentException
     * @return return true if the space was occupied or false if was empty
     */
    public boolean playResult(int space){
        isAValueArgument(space);
        boolean actualValue =  spaces[space];
        spaces[space] = !actualValue;
        return actualValue;
    }
    
    private void isAValueArgument(int argument){
        boolean isOutOfRange = argument>4 || argument<0;
        if(isOutOfRange){
            throw new IllegalArgumentException();
        }
    }
    
}
