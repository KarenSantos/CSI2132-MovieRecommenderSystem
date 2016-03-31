
package model;

/**
 *
 * @author karensantos
 */
public class Studio {
    
    private String studioID;
    private String name;
    private String country;
    
    public Studio(){}
    
    public Studio(String studioID, String name, String country){
        this.studioID = studioID;
        this.name = name;
        this.country = country;
    }

    public String getStudioID() {
        return studioID;
    }

    public void setStudioID(String studioID) {
        this.studioID = studioID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj != null) {
            if (this.getClass() == obj.getClass()) {
                if (this.getStudioID().equals(((Studio) obj).getStudioID())) {
                    result = true;
                }
            }
        }
        return result;
    }
}
