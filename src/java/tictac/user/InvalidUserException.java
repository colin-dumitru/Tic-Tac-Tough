/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.user;

/**
 *
 * @author Administrator
 */

public class InvalidUserException extends Exception{
    public InvalidUserException() {
        super("Invalid user specified1");
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public InvalidUserException(String message) {
        super(message);
    }
    
}
