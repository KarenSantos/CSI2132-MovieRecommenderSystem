
package model;

import java.io.Serializable;

/**
 *
 * @author karensantos
 */
public class Director implements Serializable {
    
    private String directorID;
    private String lastName;
    private String firstName;
    private String name;
    private String country;
    
    public Director(){}
    
    public Director(String directorID, String lastName, String firstName, 
            String country){
        this.directorID = directorID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.name = firstName + " " + lastName;
        this.country = country;
    }

    public String getDirectorID() {
        return directorID;
    }

    public void setDirectorID(String directorID) {
        this.directorID = directorID;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Director{" + directorID + ", " + lastName + ", " + firstName + ", " + country + '}';
    }
    
    
    
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj != null) {
            if (this.getClass() == obj.getClass()) {
                if (this.getDirectorID().equals(((Director) obj).getDirectorID())) {
                    result = true;
                }
            }
        }
        return result;
    }
}
