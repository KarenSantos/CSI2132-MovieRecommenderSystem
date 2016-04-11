package control;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
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

    private DataManager manager;
    private String topMessage;
    private String statusMessage;
    private String searchKey;

    private UserAccount user;
    private List<Movie> movies;
    private String moviesBy;
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
            manager = DataManager.getInstance();
            clearMessages();
            try {
                this.topMessage = manager.getTopMessage();
                user = manager.getUser();
                movies = manager.getDb().selectAllMovies();
                moviesBy = "";
                stars = manager.getDb().selectAllStars();
                directors = manager.getDb().selectAllDirectors();
                genres = manager.getDb().selectAllGenres();
                roles = manager.getDb().selectAllRoles();
                studio = manager.getDb().selectAllStudios();
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

    public DataManager getCurrent() {
        return manager;
    }

    public void setCurrent(DataManager current) {
        this.manager = current;
    }

    public String getTopMessage() {
        if (manager != null) {
            return manager.getTopMessage();
        }
        return "";
    }

    public void setTopMessage(String message) {
        manager.setTopMessage(message);
    }

    public String getStatusMessage() {
        if (manager != null) {
            return manager.getStatusMessage();
        }
        return "";
    }

    public void setStatusMessage(String statusMessage) {
        manager.setStatusMessage(statusMessage);
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
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

    public String getMoviesBy() {
        return moviesBy;
    }

    public void setMoviesBy(String moviesBy) {
        this.moviesBy = moviesBy;
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
        this.manager.setMovieID(currentMovieID);
        this.currentMovieID = currentMovieID;
    }

    public Director getDirectorByID(String directorID) {
        Director dir = null;
        try {
            dir = manager.getDb().selectDirectorByID(directorID);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            this.topMessage = "Sorry, we are unable to connect to database at this time.";
        }
        return dir;
    }

    public String goToMovie(String movieID) {
        System.out.println("got in goToMovie by ID");
        try {
            manager.setCurrentMovie(manager.getDb().selectMovieByID(movieID));
            System.out.println("movieID: " + movieID + " and movie is: " + (manager.getCurrentMovie() == null));
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            this.topMessage = "Unable to connect to database at this time.";
        }
        return "movie?faces-redirect=true";
    }

    public String goToMovies() {
        System.out.println("got in goTo ALL movies");
        this.moviesBy = "";
        try {
            this.movies = manager.getDb().selectAllMovies();
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            this.topMessage = "Unable to connect to database at this time.";
        }
        return "movies?faces-redirect=true";
    }

    public String goToStarMovies(String starID) {
        System.out.println("got in go to Star movies");
        System.out.println("starID: " + starID);
        try {
            this.movies = manager.selectStarMovies(starID);
            this.moviesBy = " with " + manager.getDb().selectStarByID(starID).getName();
            System.out.println("loaded starMovies");
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            this.topMessage = "Unable to connect to database at this time.";
        }
        return "movies?faces-redirect=true";
    }

    public String goToSearchMovies(String searchKey) {
        System.out.println("got in go to Star movies");
        System.out.println("search key " + searchKey);
        searchKey = searchKey.toLowerCase();
        List<Movie> allMovies = new ArrayList<>();
        List<Director> allDirectors = new ArrayList<>();
        try {
            allMovies = manager.getDb().selectAllMovies();
            allDirectors = manager.getDb().selectAllDirectors();
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            this.topMessage = "Unable to connect to database at this time.";
        }
        List<Movie> movies = new ArrayList<>();
        for (Movie m : allMovies) {
            if (m.getName().toLowerCase().contains(searchKey)) {
                movies.add(m);
            } else if (m.getDateReleased().toLowerCase().contains(searchKey)) {
                movies.add(m);
            } else if (m.getLanguage().toLowerCase().contains(searchKey)) {
                movies.add(m);
            } else if (m.getCountry().toLowerCase().contains(searchKey)) {
                movies.add(m);
            }
        }
//        for (Director d : allDirectors){
//            if (d.getName().contains(searchKey)){}
//        }
        this.movies = movies;
        return "movies?faces-redirect=true";
    }

    private void clearMessages() {
        manager.clearMessages();
    }

}
