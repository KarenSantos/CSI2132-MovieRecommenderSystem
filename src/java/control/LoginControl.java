/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import model.UserAccount;
import persistence.DBHelper;

/**
 *
 * @author karensantos
 */
@Named(value = "loginControl")
@RequestScoped
public class LoginControl {

    private DBHelper DBHelper;

    private String email;
    private String password;
    private boolean submited;
    private boolean validated;
    private String status;

    public String goToIndex() {
        return "index";
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getShowMessage() {
        return (submited && !validated);
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public String login() {

        String returnPage = "unauthorized";
        connect();
        try {
            UserAccount user = DBHelper.selectUserByEmail(email);
            if (user != null) {
                if (password.equals(user.getPassword())) {
                    returnPage = "main";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        validated = true;
//        FacesMessage msg = new FacesMessage("wrong");
//        FacesContext.getCurrentInstance().addMessage("status", msg);
//        status = "incorrect";
        return returnPage;
    }

    private void connect() {
        if (this.DBHelper == null) {
            this.DBHelper = new DBHelper();
        }
    }
}
