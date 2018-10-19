/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagementclient;

import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Joshua
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws util.exception.InvalidLoginCredentialException
     */
    
     public static void main(String[] args) throws InvalidLoginCredentialException {
        mainApp MainApp = new mainApp();
        MainApp.run();
    }
    
    
}
