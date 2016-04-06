
package control;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import persistence.DBHelper;

/**
 *
 * @author karensantos
 */
@Named(value = "controller")
@SessionScoped
public class Controller implements Serializable {

    private DBHelper DBHelper;

    public Controller() {
        try {
            DBHelper = new DBHelper();
        } catch (Exception e) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("Controller has started");
    }
    
    
    
}
