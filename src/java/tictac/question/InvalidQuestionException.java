/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.question;

/**
 *
 * @author bkt
 */
class InvalidQuestionException extends Exception {

    public InvalidQuestionException() {
        super("Invalid question specified");
    }

    public InvalidQuestionException(String message) {
        super(message);
    }
    
}
