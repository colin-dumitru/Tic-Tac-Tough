/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.user;

/**
 *
 * @author Administrator
 */
public class TransactionError extends Exception{
    public TransactionError(String message) {
        super(message);
    }
    
    public TransactionError() {
        super("Transaction error");
    }
    
}
