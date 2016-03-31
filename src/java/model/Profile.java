
package model;

import java.util.Calendar;

/**
 *
 * @author karensantos
 */
public class Profile {
    
    private String userID;
    private Calendar DBO;
    private String gender;
    private String occupation;
    
    public Profile(){}
    
    public Profile(String userID, Calendar DBO, String gender, String occupation){
        this.userID = userID;
        this.DBO = DBO;
        this.gender = gender;
        this.occupation = occupation;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Calendar getDBO() {
        return DBO;
    }

    public void setDBO(Calendar DBO) {
        this.DBO = DBO;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj != null) {
            if (this.getClass() == obj.getClass()) {
                if (this.getUserID().equals(((Profile) obj).getUserID())) {
                    result = true;
                }
            }
        }
        return result;
    }
}
