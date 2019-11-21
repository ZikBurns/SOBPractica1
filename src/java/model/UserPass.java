/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Pepe
 */
@XmlRootElement
@Entity
public class UserPass implements Serializable {
    @Id
    private String username;
    private String password;

    public UserPass() {
    }

    public UserPass(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUser() {
        return username;
    }

    public String getPass() {
        return password;
    }

    public void setUser(String username) {
        this.username = username;
    }

    public void setPass(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserPass{" + "username=" + username + ", password=" + password + '}';
    }


    
    
}
