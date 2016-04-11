/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import model.*;

/**
 *
 * @author karensantos
 */
@Named(value = "movieBean")
@RequestScoped
public class MovieBean implements Serializable {

    private final int DATE_LENGTH = 10;
    private DataManager manager;

    private String movieID;
    private String name;
    private String dateReleased;
    private Calendar date;
    private String language;
    private boolean subtitled;
    private String country;
    private int ageRestriction;
    private String director;
    private String directorID;
    private String directorCountry;

    private List<Genre> genres;
    private List<Star> stars;
    private Map<Star, Role> roles;
    private List<Studio> studios;

    private String topMessage;
    private String addingStatus;

    /**
     * Creates a new instance of MovieBean
     */
    public MovieBean() {
        try {
            manager = DataManager.getInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MovieBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateReleased() {
        return dateReleased;
    }

    public void setDateReleased(String dateReleased) {
        this.dateReleased = dateReleased;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
        this.dateReleased = date.get(Calendar.YEAR) + (date.get(Calendar.MONTH) + 1) + date.get(Calendar.DAY_OF_MONTH) + "";
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isSubtitled() {
        return subtitled;
    }

    public void setSubtitled(boolean subtitled) {
        this.subtitled = subtitled;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public String getDirectorID() {
        return directorID;
    }

    public void setDirectorID(String directorID) {
        this.directorID = directorID;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
        String[] columns = {"first_name", "last_name"};
        String[] values = {"'" + director.split(" ")[0] + "'", "'" + director.split(" ")[1] + "'"};
        try {
            String id = manager.getDb().selectIDFromWhereEquals("director", columns, values);
            if (id == null) {
                Director dir = new Director("", values[1], values[0], this.directorCountry);
                directorID = manager.getDb().insertDirector(dir);
            } else {
                this.directorID = id;
            }
        } catch (Exception ex) {
            Logger.getLogger(MovieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getDirectorCountry() {
        return directorCountry;
    }

    public void setDirectorCountry(String directorCountry) {
        this.directorCountry = directorCountry;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Star> getStars() {
        return stars;
    }

    public void setStars(List<Star> stars) {
        this.stars = stars;
    }

    public Map<Star, Role> getRoles() {
        return roles;
    }

    public void setRoles(Map<Star, Role> roles) {
        this.roles = roles;
    }

    public List<Studio> getStudios() {
        return studios;
    }

    public void setStudios(List<Studio> studios) {
        this.studios = studios;
    }

    public String getTopMessage() {
        return topMessage;
    }

    public void setTopMessage(String topMessage) {
        this.topMessage = topMessage;
    }

    public String getAddingStatus() {
        return addingStatus;
    }

    public void setAddingStatus(String addingStatus) {
        this.addingStatus = addingStatus;
    }

}
