/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.EmployeeEntity;

/**
 *
 * @author Joshua
 */
public interface EmployeeBeanLocal {

    public Long createEmployeeEntity(EmployeeEntity newEmployeeEntity);

    public EmployeeEntity retrieveLinkedAccount(String name);

    public EmployeeEntity getReserver();
    
}
