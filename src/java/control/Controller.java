package control;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import model.*;

/**
 *
 * @author karensantos
 */
@Named(value = "controller")
@SessionScoped
public class Controller implements Serializable {

    private CurrentData current;
    private String topMessage;

    private UserAccount user;
    private List<Movie> movies;
    private List<Star> stars;
    private List<Director> directors;
    private List<Genre> genres;
    private List<Role> roles;
    private List<Studio> studio;
    
    private String currentMovieID;

    public Controller() {
    }

    @PostConstruct
    public void init() {
        try {
            current = CurrentData.getInstance();
            try {
                this.topMessage = current.getMessage();
                user = current.getUser();
                movies = current.getDb().selectAllMovies();
                stars = current.getDb().selectAllStars();
                directors = current.getDb().selectAllDirectors();
                genres = current.getDb().selectAllGenres();
                roles = current.getDb().selectAllRoles();
                studio = current.getDb().selectAllStudios();
            } catch (Exception ex1) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex1);
                topMessage = "Sorry. We are not being able to connect to the database.";
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            this.topMessage = "Sorry. We have encountered a problem connecting with the database.\n"
                    + "Please try again later.\nError: ClassNotFoundExcpetion.";
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            this.topMessage = "Sorry. We have encountered a problem connecting with the database.\n"
                    + "Please try again later.\nError: SQLException.";

        }
    }

    public CurrentData getCurrent() {
        return current;
    }

    public void setCurrent(CurrentData current) {
        this.current = current;
    }
    
    public String getTopMessage() {
        return topMessage;
    }

    public void setTopMessage(String message) {
        this.topMessage = message;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Star> getStars() {
        return stars;
    }

    public void setStars(List<Star> stars) {
        this.stars = stars;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Studio> getStudio() {
        return studio;
    }

    public void setStudio(List<Studio> studio) {
        this.studio = studio;
    }

    public String getCurrentMovieID() {
        return currentMovieID;
    }

    public void setCurrentMovieID(String currentMovieID) {
        this.current.setMovieID(currentMovieID);
        this.currentMovieID = currentMovieID;
    }
    
    public String goToMovie(String movieID){
        try {
            current.setCurrentMovie(current.getDb().selectMovieByID(movieID));
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            this.topMessage = "Unable to connect to database at this time.";
        }
        return "movie";
    }
}
