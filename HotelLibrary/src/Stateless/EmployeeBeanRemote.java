/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.EmployeeEntity;
import java.util.List;
import util.EmployeeNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Joshua
 */
public interface EmployeeBeanRemote {

    public List<EmployeeEntity> retrieveAllEmployeeEntity();

    public EmployeeEntity retrieveStaffByUsername(String username)throws EmployeeNotFoundException;

    public EmployeeEntity staffLogin(String username, String password)throws InvalidLoginCredentialException;

    public Long createEmployee(EmployeeEntity newEmployeeEntity);
    
}
