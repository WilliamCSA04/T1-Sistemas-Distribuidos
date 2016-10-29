/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Speculate;

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
public class BoardTest {
    
    public BoardTest() {
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
     * Test of playResult method, of class Board.
     */
    @Test
    public void testPlayResultToReturnFalse() {
        int space = 1;
        Board instance = new Board();
        boolean expResult = false;
        boolean result = instance.playResult(space);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testPlayResultToReturnTrue() {
        int space = 0;
        Board instance = new Board();
        boolean expResult = true;
        boolean result = instance.playResult(space);
        assertEquals(expResult, result);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testPlayResultToThrowIllegalArgumentExceptionPassingMaxLimitByOne() {
        int space = 0;
        Board instance = new Board();
        instance.playResult(5);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testPlayResultToThrowIllegalArgumentExceptionPassingMinLimitByOne() {
        int space = 0;
        Board instance = new Board();
        instance.playResult(-1);
    }
}
