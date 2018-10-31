/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.GuestEntity;
import javax.ejb.Remote;
import util.EmployeeNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Joshua
 */
@Remote
public interface GuestControllerRemote {

    public void register(GuestEntity guest);

    public GuestEntity retrieveByUsername(String username) throws EmployeeNotFoundException;

    public GuestEntity userLogin(String username, String password) throws InvalidLoginCredentialException;
    
}
