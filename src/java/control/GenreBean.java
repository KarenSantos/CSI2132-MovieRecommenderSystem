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
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import model.Genre;

/**
 *
 * @author karensantos
 */
@Named(value = "genreBean")
@RequestScoped
public class GenreBean implements Serializable {

    private DataManager manager;

    private String name;
    private String message;
    private String addingStatus;

    /**
     * Creates a new instance of GenreBean
     */
    public GenreBean() {
        try {
            manager = DataManager.getInstance();
            manager.clearMessages();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenreBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GenreBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return manager.getTopMessage();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAddingStatus() {
        return manager.getStatusMessage();
    }

    public void setAddingStatus(String addingStatus) {
        this.addingStatus = addingStatus;
    }

    public String insert() {
        System.out.println("got into genre insert");
        String id;
        manager.clearMessages();
        try {
            id = this.manager.getDb().selectIDFromWhereEquals("genre", "name", "'" + this.name + "'");
            if (id == null) {
                Genre g = new Genre("", this.name);
                this.manager.getDb().insertGenre(g);
                manager.setTopMessage("Genre " + this.name + " inserted successfuly");
                System.out.println("after adding");
            } else {
                manager.setStatusMessage("This genre already exists.");
                System.out.println("already exists");
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenreBean.class.getName()).log(Level.SEVERE, null, ex);
            manager.setStatusMessage("An error has occured while trying to connect with the DB");
            System.out.println("error with DB");

        }
        return "insertStatus";
    }
}
