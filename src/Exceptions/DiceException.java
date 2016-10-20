/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author realnetwoking
 */
public class DiceException extends Exception{
    
    public DiceException(){
        
    }
    
    public DiceException(String message){
        super(message);
    }
    
}
