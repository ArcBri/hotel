/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.PartnerEntity;
import javax.ejb.Local;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Joshua
 */
@Local
public interface PartnerControllerLocal {

    public PartnerEntity partnerLogin(String username, String password) throws InvalidLoginCredentialException;
    
}
