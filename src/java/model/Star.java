
package model;

import java.io.Serializable;

/**
 *
 * @author karensantos
 */
public class Star implements Serializable {
    
    private final int DATE_LENGTH = 10;
    
    private String starID;
    private String lastName;
    private String firstName;
    private String name;
    private String DBO;
    private String country;
    
    public Star(){}
    
    public Star(String starID, String lastName, String firstName, 
            String DBO, String country) throws Exception{
        this.starID = starID;
        this.lastName = lastName;
        this.firstName = firstName;
        name = firstName + " " + lastName;
        setDBO(DBO);
        this.country = country;
    }

    public String getStarID() {
        return starID;
    }

    public void setStarID(String actorID) {
        this.starID = actorID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDBO() {
        return DBO;
    }

    public void setDBO(String DBO) throws Exception {
        if (DBO.length() != DATE_LENGTH) throw new Exception();
        this.DBO = DBO;
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
                if (this.getStarID().equals(((Star) obj).getStarID())) {
                    result = true;
                }
            }
        }
        return result;
    }
}
