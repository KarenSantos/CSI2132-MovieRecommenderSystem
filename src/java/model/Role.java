
package model;

import java.io.Serializable;

/**
 *
 * @author karensantos
 */
public class Role implements Serializable {
    
    private String roleID;
    private String name;
    
    public Role(){}
    
    public Role(String roleID, String name){
        this.roleID = roleID;
        this.name = name;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj != null) {
            if (this.getClass() == obj.getClass()) {
                if (this.getRoleID().equals(((Role) obj).getRoleID())) {
                    result = true;
                }
            }
        }
        return result;
    }
}
