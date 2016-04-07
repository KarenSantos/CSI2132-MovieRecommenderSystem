/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import model.PasswordService;
import model.UserAccount;
import persistence.DBHelper;

/**
 *
 * @author karensantos
 */
@Named(value = "userBean")
@RequestScoped
public class UserBean implements Serializable {

    private DBHelper DBHelper;

    private String email;
    private String password;
    private String lastName;
    private String firstName;
    private String city;
    private String province;
    private String country;

    private String addingStatus;
    private static String pass;

    public UserBean() {

        DBHelper = new DBHelper();

        System.out.println("UserBean has started");
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

    public void signUp(ActionEvent actionEvent) {

//        PasswordService ps = null;
//        while (ps == null) {
//            ps = PasswordService.getInstance();
//        }
//        String encryptedPW = "";
//        try {
//            PasswordService ps = PasswordService.getInstance();
//            encryptedPW = ps.encrypt(password);
        UserAccount user = new UserAccount("", password, lastName, firstName, email, city, province, country);
        try {
            DBHelper.insertUser(user);
            addingStatus = "User profile created successfully";
        } catch (SQLException ex) {
            addingStatus = "Error while creating user account. Try again later.";
            Logger
                    .getLogger(UserBean.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
//        } catch (Exception ex) {
//            addingStatus = "Error while encrypting password. Try again later.";
//            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

}
