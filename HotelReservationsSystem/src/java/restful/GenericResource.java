/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import Entity.PartnerEntity;
import Stateless.PartnerControllerLocal;
import datamodel.LogInRsp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.InvalidLoginCredentialException;

/**
 * REST Web Service
 *
 * @author Joshua
 */
@Path("generic")
public class GenericResource {

    PartnerControllerLocal partnerController = lookupPartnerControllerLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of restful.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response LogIn(String username, String password) throws InvalidLoginCredentialException{
        PartnerEntity partner=partnerController.partnerLogin(username, password);
        LogInRsp resp= new LogInRsp(partner);
        return Response.status(Response.Status.OK).entity(resp).build();
    }

    private PartnerControllerLocal lookupPartnerControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PartnerControllerLocal) c.lookup("java:global/Hotel/Hotel-ejb/PartnerController!Stateless.PartnerControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
