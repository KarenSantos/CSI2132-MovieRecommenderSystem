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

    private CurrentData current;

    private String email;
    private String password;

    private boolean submited;
    private boolean validated;
    private String status;

    public LoginControl() {
        try {
            current = CurrentData.getInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
            status = "Sorry. We have encountered a problem connecting with the database.\n"
                    + "Please try again later.\nError: ClassNotFoundExcpetion.";
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            status = "Sorry. We have encountered a problem connecting with the database.\n"
                    + "Please try again later.\nError: SQLException.";
        }
    }

    public String goToMain() {
        return "main";
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
                        status = "Incorrect password or email.";
                    }
                } else {
                    status = "Incorrect email or password.";

                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginControl.class
                        .getName()).log(Level.SEVERE, null, ex);
                status = "Could not connect to the database. Try again later.";
            }

        } catch (Exception ex) {
            Logger.getLogger(LoginControl.class
                    .getName()).log(Level.SEVERE, null, ex);
            status = "An error has occured. Try again later.";
        }

        validated = true;
//        FacesMessage msg = new FacesMessage("wrong");
//        FacesContext.getCurrentInstance().addMessage("status", msg);
//        status = "incorrect";
        return returnPage;
    }
}
