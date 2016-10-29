/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Speculate;

import Client.Player;
import Exceptions.DiceException;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author realnetwoking
 */
public class DiceTest {
    
    public DiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of rollDice method, of class Dice.
     */
    @Test
    public void testRollDice() throws DiceException {
        System.out.println("rollDice");
        Random r = new Random();
        int parameter = r.nextInt(15)+1;
        int rollTimes = parameter;
        Player player = new Player("test");
        List<Integer> resultList = Dice.rollDice(rollTimes, player);
        for (Integer result : resultList) {
            boolean isOutOfCorrectRange = result < 1 || result > 6;
            if(isOutOfCorrectRange){
                fail("Unexpected number: " + result);
            }
        }
        int result = resultList.size();
        int expResult = parameter;
        assertEquals(expResult, result);
    }
    
    @Test(expected=DiceException.class)
    public void testRollDiceThrowingExceptionPassingLowLimitByOne() throws DiceException {
        System.out.println("rollDice");
        int rollTimes = 0;
        Player player = new Player("test");
        Dice.rollDice(rollTimes, player);
    }
    
    @Test(expected=DiceException.class)
    public void testRollDiceThrowingExceptionPassingHigherLimitByOne() throws DiceException {
        System.out.println("rollDice");
        int rollTimes = 16;
        Player player = new Player("test");
        Dice.rollDice(rollTimes, player);
    }
    
    
}
