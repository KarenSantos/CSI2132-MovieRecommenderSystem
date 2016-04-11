/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.event.ActionEvent;
import model.PasswordService;
import model.UserAccount;

/**
 *
 * @author karensantos
 */
@Named(value = "userBean")
@RequestScoped
public class UserBean implements Serializable {

    private CurrentData current;

    private String userID;

    private String email;
    private String password;
    private String lastName;
    private String firstName;
    private String city;
    private String province;
    private String country;

    private String addingStatus;

    public UserBean() {
    }

    @PostConstruct
    public void init() {
        try {
            current = CurrentData.getInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            addingStatus = "Sorry. We have encountered a problem connecting with the database.\n"
                    + "Please try again later.\nError: ClassNotFoundExcpetion.";
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            addingStatus = "Sorry. We have encountered a problem connecting with the database.\n"
                    + "Please try again later.\nError: SQLException.";
        }
    }

    public String getUserID() {
        return userID;
    }

    public void setMsg() {
        this.addingStatus = "foi";
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAddingStatus() {
        return addingStatus;
    }

    public void setAddingStatus(String addstatus) {
        this.addingStatus = addstatus;
    }

    public void setValues(String id) {
        this.addingStatus = id;
    }

    public String signUp(ActionEvent actionEvent) {

        try {
            if (current.getDb().selectUserByEmail(email) == null) {
                PasswordService ps = null;
                while (ps == null) {
                    ps = PasswordService.getInstance();
                }
                String encryptedPW = "";
                try {
                    encryptedPW = ps.encrypt(password);
                    UserAccount user = new UserAccount("", encryptedPW, lastName, firstName, email, city, province, country);
                    try {
                        String userID = current.getDb().insertUserAccount(user);
                        current.setMessage("User profile created successfully");
                        current.setUserID(userID);
                        return "main";
                    } catch (SQLException ex) {
                        addingStatus = "Error while creating user account. Try again later.";
                        Logger
                                .getLogger(UserBean.class
                                        .getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (Exception ex) {
                    addingStatus = "Error while encrypting password. Try again later.";
                    Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                addingStatus = "This email account already exists. Try another one.";
            }
        } catch (SQLException ex) {
            addingStatus = "Error while creating user account. Try again later.";
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
