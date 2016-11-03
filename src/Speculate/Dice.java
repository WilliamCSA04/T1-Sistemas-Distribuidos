
package Speculate;

import Client.Player;
import Exceptions.DiceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Dice {
    
    private static final Random dice = new Random(System.currentTimeMillis());
    
    /**
     * Method that return a list with the number of roll dice results.
     * This method receive as parameter the number of times that you want to
     * roll the dice and give exactly the same number of dice results inside of
     * a List.
     * @param rollTimes Number of times that a dice must roll
     * @return a list with all the results of a dice or a IllegalArgumentException
     * if the rollTimes bigger than 15 or lower than 1
     * @throws Exceptions.DiceException Throws this exception if the passing 
     * parameter are bigger than 15 or lower than 1
     */
    public static List<Integer> rollDice(int rollTimes, Player player) throws DiceException{
        
        rollTimesArgumentValidation(rollTimes, player);
        List<Integer> diceResults = new ArrayList<>();
        addAllDiceRollResultsToList(rollTimes, diceResults);
        return diceResults;
    }
    
    private static void addAllDiceRollResultsToList(int rollTimes, List<Integer> diceResults){
        for(int i = 0; i < rollTimes; i++){
            int diceResult = dice.nextInt(6);
            diceResult++;
            diceResults.add(diceResult);
        }
    }
    
    private static void rollTimesArgumentValidation(int rollTimes, Player player) throws DiceException{
        int maxRollTimes = player.getActualBallsQuantity();
        boolean invalid = rollTimes > maxRollTimes || rollTimes <= 0;
        if(invalid){
           throw new DiceException();
        }
    }
    
}
