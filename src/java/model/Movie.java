
package model;

import java.util.Calendar;

/**
 *
 * @author karensantos
 */
public class Movie {
    
    private String movieID;
    private String name;
    private Calendar dateReleased;
    private String language;
    private boolean hasSubtitles;
    private String country;
    private int ageRestriction;
    
    public Movie(){}
    
    public Movie(String movieID, String name, Calendar dateReleased, 
            String language, boolean hasSubtitles, String country, int ageRestriction){
        this.movieID = movieID;
        this.name = name;
        this.dateReleased = dateReleased;
        this.language = language;
        this.hasSubtitles = hasSubtitles;
        this.country = country;
        this.ageRestriction = ageRestriction;
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

    public Calendar getDateReleased() {
        return dateReleased;
    }

    public void setDateReleased(Calendar dateReleased) {
        this.dateReleased = dateReleased;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isHasSubtitles() {
        return hasSubtitles;
    }

    public void setHasSubtitles(boolean hasSubtitles) {
        this.hasSubtitles = hasSubtitles;
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
}
