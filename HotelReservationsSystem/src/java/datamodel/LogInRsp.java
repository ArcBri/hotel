/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import Entity.PartnerEntity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Joshua
 */
@XmlRootElement
public class LogInRsp {
    private PartnerEntity partner;

    public LogInRsp(PartnerEntity partner) {
        this.partner = partner;
    }

    public LogInRsp() {
    }

    public PartnerEntity getPartner() {
        return partner;
    }

    public void setPartner(PartnerEntity partner) {
        this.partner = partner;
    }
    
}
