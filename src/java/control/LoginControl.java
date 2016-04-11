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
import model.PasswordService;
import model.UserAccount;

/**
 *
 * @author karensantos
 */
@Named(value = "loginControl")
@RequestScoped
public class LoginControl {

    private DataManager current;

    private String email;
    private String password;

    private String topMessage;
    private String errorMessage;

    public LoginControl() {
        clearMessages();
        try {
            current = DataManager.getInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
            topMessage = "Sorry. We have encountered a problem connecting with the database.\n"
                    + "Please try again later.\nError: ClassNotFoundExcpetion.";
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            topMessage = "Sorry. We have encountered a problem connecting with the database.\n"
                    + "Please try again later.\nError: SQLException.";
        }
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

    public String getTopMessage() {
        return topMessage;
    }

    public void setTopMessage(String topMessage) {
        this.topMessage = topMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String login() {
        String returnPage = "unauthorized";
        PasswordService ps = null;
        while (ps == null) {
            ps = PasswordService.getInstance();
        }
        String encryptedPW = "";
        try {
            encryptedPW = ps.encrypt(password);
            try {
                UserAccount user = current.getDb().selectUserByEmail(email);
                if (user != null) {
                    if (encryptedPW.equals(user.getPassword())) {
                        current.setUserID(user.getUserID());
                        returnPage = "main";
                    } else {
                        clearMessages();
                        errorMessage = "Incorrect password or email.";
                    }
                } else {
                    clearMessages();
                    errorMessage = "Incorrect email or password.";

                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginControl.class
                        .getName()).log(Level.SEVERE, null, ex);
                clearMessages();
                errorMessage = "Could not connect to the database. Try again later.";
            }

        } catch (Exception ex) {
            Logger.getLogger(LoginControl.class
                    .getName()).log(Level.SEVERE, null, ex);
            clearMessages();
            errorMessage = "An error has occured. Try again later.";
        }

        return returnPage;
    }
    
    private void clearMessages(){
        topMessage = "";
        errorMessage = "";
    }
}
