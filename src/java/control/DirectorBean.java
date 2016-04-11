/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import model.Director;

/**
 *
 * @author karensantos
 */
@Named(value = "directorBean")
@SessionScoped
public class DirectorBean implements Serializable {

    private DataManager manager;

    private String lastName;
    private String firstName;
    private String country;
    private String message;
    private String addingStatus;

    /**
     * Creates a new instance of GenreBean
     */
    public DirectorBean() {
        try {
            manager = DataManager.getInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DirectorBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DirectorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getName() {
        return lastName;
    }

    public void setName(String name) {
        this.lastName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAddingStatus() {
        return addingStatus;
    }

    public void setAddingStatus(String addingStatus) {
        this.addingStatus = addingStatus;
    }

    public String insert() {
        System.out.println("got into director insert");
        String id;
        manager.clearMessages();
        String[] columns = {"last_name", "first_name"};
        String[] values = {"'" + lastName + "'", "'" + firstName + "'"};
        try {
            id = this.manager.getDb().selectIDFromWhereEquals("director", columns, values);
            if (id == null) {
                Director d = new Director("", this.lastName, this.firstName, this.country);
                this.manager.getDb().insertDirector(d);
                manager.setTopMessage("Director " + this.firstName + " " + this.lastName + " inserted successfuly");
                System.out.println("after inserting");
            } else {
                manager.setStatusMessage("This director already exists.");
                System.out.println("director already exists");
            }
        } catch (Exception ex) {
            Logger.getLogger(DirectorBean.class.getName()).log(Level.SEVERE, null, ex);
            manager.setStatusMessage("An error has occured while trying to connect with the DB");
            System.out.println("error with DB");
        }
        return "insertStatus";
    }
}
