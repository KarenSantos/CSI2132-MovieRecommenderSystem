/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
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
        
        DBHelper DBHelper = new DBHelper();
//        UserAccount user = DBHelper.findUser(em, emailId);
//
//        if (user != null) {
//            if (password.equals(user.getPassword())) {
//                if (user instanceof Instructor) {
//                    return "instructorMenu";
//                } else if (user instanceof Student) {
//                    return "studentMenu";
//                }
//            }
//        }
//        validated = true;
//        FacesMessage msg = new FacesMessage("errado");
//        FacesContext.getCurrentInstance().addMessage("status", msg);
//        status = "incorrect";
        return "unauthorized";
    }
}