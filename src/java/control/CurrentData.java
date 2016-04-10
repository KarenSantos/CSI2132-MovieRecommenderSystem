/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;
import persistence.DBHelper;

/**
 *
 * @author karensantos 
 */
public class CurrentData {

    private static CurrentData instance;

    private DBHelper db;
    private String topMessage;
    private String userID;
    private String movieID;
    private UserAccount user;
    private Movie currentMovie;
    
    private CurrentData() throws ClassNotFoundException, SQLException {
        this.db = new DBHelper();
        System.out.println("helper has ben initialized at current data");
    }

    public DBHelper getDb() {
        return db;
    }

    public void setDb(DBHelper db) {
        this.db = db;
    }

    public String getMessage() {
        return topMessage;
    }

    public void setMessage(String message) {
        this.topMessage = message;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
        try {
            this.user = db.selectUserByID(userID);
        } catch (SQLException ex) {
            Logger.getLogger(CurrentData.class.getName()).log(Level.SEVERE, null, ex);
            topMessage = "Database is currently unreachable. Please try again later";
        }
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }
    
    public static synchronized CurrentData getInstance() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new CurrentData();
        }
        return instance;
    }

    public Movie getCurrentMovie() {
        return currentMovie;
    }

    public void setCurrentMovie(Movie movie) {
        this.currentMovie = movie;
    }
    
}
