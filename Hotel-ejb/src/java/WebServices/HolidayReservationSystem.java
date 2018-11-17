/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import Entity.PartnerEntity;
import Stateless.PartnerControllerLocal;
import java.util.Scanner;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import sun.security.util.Password;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Joshua
 */
@WebService(serviceName = "HolidayReservationSystem")
@Stateless()
public class HolidayReservationSystem {

    @EJB
    private PartnerControllerLocal partnerController;
    
    private PartnerEntity current;

    /**
     * This is a sample web service operation
     *
    
    */
    public PartnerEntity LogIn() throws InvalidLoginCredentialException{
        Scanner sc = new Scanner(System.in);
        String username=sc.nextLine();
        String password =sc.nextLine();
        return partnerController.partnerLogin(username, password);
    }
}
