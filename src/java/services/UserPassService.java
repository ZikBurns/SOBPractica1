/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import model.Renter;
import model.Room;
import model.UserPass;

/**
 *
 * @author Pepe
 */
@Provider
@PreMatching
public class UserPassService implements ContainerRequestFilter {
    private EntityManager em;
    private static final String X_USER = "username";
    private static final String X_PASSWORD = "password";  
    public UserPassService(EntityManager em){
        this.em=em;
    }
    public UserPassService() {
        em = Persistence.createEntityManagerFactory("Service")
                        .createEntityManager();
    }
    public UserPass createUserPass(String user, String pass){
        UserPass userpass = new UserPass(user,pass);
        em.getTransaction().begin();
        em.persist(userpass);
        em.getTransaction().commit();
        return userpass;
    }
    public UserPass createUserPass(UserPass userpass){
        em.getTransaction().begin();
        em.persist(userpass);
        em.getTransaction().commit();
        return userpass;
    }
    
    public UserPass queryUserPasswithid(String user){
        String query="SELECT u FROM UserPass u WHERE u.username = :user";
        try {
        return (UserPass) em.createQuery(query)
                        .setParameter("user", user)
                        .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<UserPass> queryAllUserPass() {
        return (List<UserPass>) em.createQuery(
                "SELECT u FROM UserPass u").getResultList();
    }
    
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String user = requestContext.getHeaderString(X_USER);
        String pass = requestContext.getHeaderString(X_PASSWORD);
        if(!validUserPass(user,pass)) requestContext.abortWith( Response.status( Response.Status.UNAUTHORIZED ).build() );
    
    }
    public boolean validUserPass(String user,String pass){
        List<UserPass> list=this.queryAllUserPass();
        int i=0;
        UserPass userpass=list.get(i);
        while(i<list.size() && (!userpass.getUser().equals(user)))
        {
            userpass=list.get(i);
            i++;
        }
        if(!userpass.getUser().equals(user)) return false;
        return userpass.getPass().equals(sha256(pass));
    }
    
    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
           throw new RuntimeException(ex);
        }
    }
    
}