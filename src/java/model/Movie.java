
package model;

import java.io.Serializable;

/**
 *
 * @author karensantos
 */
public class Movie implements Serializable {
    
    private final int DATE_LENGTH = 10;
    
    private String movieID;
    private String name;
    private String dateReleased;
    private String language;
    private boolean subtitled;
    private String country;
    private int ageRestriction;
    private String directorID;
    private int averageRating;
    
    public Movie(){}
    
    public Movie(String movieID, String name, String dateReleased, String language, 
            boolean hasSubtitles, String country, int ageRestriction, String directorID) throws Exception{
        this.movieID = movieID;
        this.name = name;
        this.setDateReleased(dateReleased);
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

    public void setDateReleased(String dateReleased) throws Exception {
        if (dateReleased.length() != DATE_LENGTH) throw new Exception();
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

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
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
