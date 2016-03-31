
package model;

import java.util.Calendar;

/**
 *
 * @author karensantos
 */
public class Actor {
    
    private String actorID;
    private String lastName;
    private String firstName;
    private Calendar DBO;
    private String country;
    
    public Actor(){}
    
    public Actor(String actorID, String lastName, String firstName, 
            Calendar DBO, String country){
        this.actorID = actorID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.DBO = DBO;
        this.country = country;
    }

    public String getActorID() {
        return actorID;
    }

    public void setActorID(String actorID) {
        this.actorID = actorID;
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

    public Calendar getDBO() {
        return DBO;
    }

    public void setDBO(Calendar DBO) {
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
                if (this.getActorID().equals(((Actor) obj).getActorID())) {
                    result = true;
                }
            }
        }
        return result;
    }
}
