/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;
import persistence.DBHelper;

/**
 *
 * @author karensantos 
 */
public class DataManager {

    private final String schema = "movie_recommender_system";
    private static DataManager instance;

    private DBHelper db;
    private String topMessage;
    private String userID;
    private String movieID;
    private UserAccount user;
    private Movie currentMovie;
    private String statusMessage;
    
    private DataManager() throws ClassNotFoundException, SQLException {
        this.db = new DBHelper();
        System.out.println("helper has ben initialized at current data");
    }

    public DBHelper getDb() {
        return db;
    }

    public void setDb(DBHelper db) {
        this.db = db;
    }

    public String getTopMessage() {
        return topMessage;
    }

    public void setTopMessage(String message) {
        this.topMessage = message;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
        try {
            this.user = db.selectUserByID(userID);
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public static synchronized DataManager getInstance() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public Movie getCurrentMovie() {
        return currentMovie;
    }

    public void setCurrentMovie(Movie movie) {
        this.currentMovie = movie;
    }
    
    public void clearMessages(){
        this.topMessage = "";
        this.statusMessage = "";
    }
    
    public List<Movie> selectStarMovies(String starID) throws SQLException, Exception{
        List<Movie> movies = new ArrayList<>();
        ResultSet rs = db.doQuery("SELECT * FROM " + schema + ".movie m, " + schema + ".movie_star ms "
                + "WHERE ms.star_id = '" + starID + "' AND m.movie_id = ms.movie_id;");
        while (rs.next()) {
            Movie m = new Movie(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                    rs.getBoolean(5), rs.getString(6), rs.getInt(7), rs.getString(8));
            movies.add(m);
            System.out.println(m.toString());
        }
        rs.close();
        db.closeStatement();
        return movies;
    }
    
}
