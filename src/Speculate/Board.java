package Speculate;

public class Board {
    
    boolean spaces[];
    
    public Board(){
        populateInitialSpaces();
    }
    
    private void populateInitialSpaces(){
        spaces = new boolean[]{true, false, true, false, true};
    }
    
    
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
