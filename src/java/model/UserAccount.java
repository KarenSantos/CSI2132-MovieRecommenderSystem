package model;

import java.io.Serializable;

/**
 *
 * @author karensantos
 */
public class UserAccount implements Serializable {

    private String userID;
    private String password;
    private String lastName;
    private String firstName;
    private String email;
    private String city;
    private String province;
    private String country;

    public UserAccount() {
    }

    public UserAccount(String userID, String password, String lastName, String firstName, String email, String city, String province, String country) {
        this.userID = userID;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.city = city;
        this.province = province;
        this.country = country;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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
                if (this.getUserID().equals(((UserAccount) obj).getUserID())) {
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "UserAccount{" + userID + ", " + password + ", " 
                + lastName + ", " + firstName + ", " + email + ", " + city + ", " + province + ", " + country + '}';
    }
    
    

}
