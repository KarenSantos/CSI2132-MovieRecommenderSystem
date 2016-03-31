
package model;

/**
 *
 * @author karensantos
 */
public class Genre {
    
    private String genreID;
    private String description;
    
    public Genre(){}
    
    public Genre(String genreID, String description){
        this.genreID = genreID;
        this.description = description;
    }

    public String getGenreID() {
        return genreID;
    }

    public void setGenreID(String genreID) {
        this.genreID = genreID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj != null) {
            if (this.getClass() == obj.getClass()) {
                if (this.getGenreID().equals(((Genre) obj).getGenreID())) {
                    result = true;
                }
            }
        }
        return result;
    }
}
