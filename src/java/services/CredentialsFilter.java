/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import model.Renter;
import model.Room;
import model.Credentials;

/**
 *
 * @author Pepe
 */
@Provider
@PreMatching
public class CredentialsFilter implements ContainerRequestFilter {
    private EntityManager em;
    private CredentialsService ups;
    private static final String X_USER = "username";
    private static final String X_PASSWORD = "password";  

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String user = requestContext.getHeaderString(X_USER);
        String pass = requestContext.getHeaderString(X_PASSWORD);
        UriInfo uri = requestContext.getUriInfo();
        if(uri.getPath().contains("register")) { return;}
        else if(!ups.validCredentials(user,pass)) requestContext.abortWith( Response.status( Response.Status.UNAUTHORIZED ).build() );
    }
    
    public CredentialsFilter(EntityManager em){
        this.em=em;
        ups=new CredentialsService (em);
    }
    public CredentialsFilter() {
        em = Persistence.createEntityManagerFactory("Service")
                        .createEntityManager();
        ups=new CredentialsService ();
    }
    
   
    
    
}