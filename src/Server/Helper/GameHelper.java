package Server.Helper;

public final class GameHelper {
    
    public static String updateBallsOutOfGameHelper(String sentence){
        int indexOfOldCharacter = sentence.indexOf('_');
        return updateSentence(sentence, indexOfOldCharacter, '0');
    }
    
    public static String updateSpacesForReduceHelper(int index, String sentence){
        int correctedIndex = index-1;
        return updateSentence(sentence, correctedIndex, '0');
    }
    
    public static String updateSpacesForAddHelper(int index, String sentence){
        int correctedIndex = index-1;
        return updateSentence(sentence, correctedIndex, '_');
    }
    
    private static String updateSentence(String sentence, int indexOfOldCharacter, char newChar){
        char[] arrayFromSentence = sentence.toCharArray();
        arrayFromSentence[indexOfOldCharacter] = newChar;
        return String.valueOf(arrayFromSentence);
    }
    
}
