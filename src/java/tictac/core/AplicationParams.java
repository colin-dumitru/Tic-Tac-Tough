/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class AplicationParams {
    protected final static String LOGGER_FILE_NAME = "logg.txt";
    
    BufferedWriter _logger;
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------    
    public AplicationParams() {
        this.createLogger(LOGGER_FILE_NAME);                
    }    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void createLogger(String fileName) {
        try {
            this._logger = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException ex) {
            Logger.getLogger(AplicationParams.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public BufferedWriter logger() {
        return this._logger;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    
}
