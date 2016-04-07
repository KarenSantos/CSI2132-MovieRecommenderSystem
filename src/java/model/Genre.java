
package model;

/**
 *
 * @author karensantos
 */
public class Genre {
    
    private String genreID;
    private String name;
    
    public Genre(){}
    
    public Genre(String genreID, String description){
        this.genreID = genreID;
        this.name = description;
    }

    public String getGenreID() {
        return genreID;
    }

    public void setGenreID(String genreID) {
        this.genreID = genreID;
    }

    public String getName() {
        return name;
    }

    public void setName(String description) {
        this.name = description;
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
