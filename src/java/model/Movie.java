
package model;

import java.util.Calendar;

/**
 *
 * @author karensantos
 */
public class Movie {
    
    private String movieID;
    private String name;
    private String dateReleased;
    private String language;
    private boolean subtitled;
    private String country;
    private int ageRestriction;
    private String directorID;
    
    public Movie(){}
    
    public Movie(String movieID, String name, String dateReleased, String language, 
            boolean hasSubtitles, String country, int ageRestriction, String directorID){
        this.movieID = movieID;
        this.name = name;
        this.dateReleased = dateReleased;
        this.language = language;
        this.subtitled = hasSubtitles;
        this.country = country;
        this.ageRestriction = ageRestriction;
        this.directorID = directorID;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isSubtitled() {
        return subtitled;
    }

    public void setSubtitled(boolean isSubtitled) {
        this.subtitled = isSubtitled;
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
    
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj != null) {
            if (this.getClass() == obj.getClass()) {
                if (this.getMovieID().equals(((Movie) obj).getMovieID())) {
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" + movieID + ", " + name + ", " + dateReleased + ", " + language 
                + ", " + subtitled + ", " + country + ", " + ageRestriction + "}";
    }
    
    
}
