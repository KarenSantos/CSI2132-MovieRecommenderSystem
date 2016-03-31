/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author karensantos
 */
public class Oscar {
    
    private String oscarID;
    private String name;
    private String description;
    
    public Oscar(){}
    
    public Oscar(String oscarID, String name, String description){
        this.oscarID = oscarID;
        this.name = name;
        this.description = description;
    }

    public String getOscarID() {
        return oscarID;
    }

    public void setOscarID(String oscarID) {
        this.oscarID = oscarID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj != null) {
            if (this.getClass() == obj.getClass()) {
                if (this.getOscarID().equals(((Oscar) obj).getOscarID())) {
                    result = true;
                }
            }
        }
        return result;
    }
}
