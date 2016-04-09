
package model;

import java.util.Calendar;

/**
 *
 * @author karensantos
 */
public class UserProfile {
    
    private String userID;
    private String DBO;
    private String gender;
    private String occupation;
    
    public UserProfile(){}
    
    public UserProfile(String userID, String DBO, String gender, String occupation){
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

    public String getDBO() {
        return DBO;
    }

    public void setDBO(String DBO) {
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
                if (this.getUserID().equals(((UserProfile) obj).getUserID())) {
                    result = true;
                }
            }
        }
        return result;
    }
}
