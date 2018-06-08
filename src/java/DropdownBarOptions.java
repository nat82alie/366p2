import java.io.Serializable;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;
import java.sql.*;

/**
 *
 * @author team1
 */
@Named(value = "dropdownbaroptions")
@ManagedBean
@SessionScoped
public class DropdownBarOptions implements Serializable {

    private List<CustomBox> choices;
    private CustomBox choice; 
    
    public List<CustomBox> getChoices() throws SQLException {
        CustomBox cb = new CustomBox();
        choices = cb.getCustomBox();
        return choices;
    }

    public void setChoices(List<CustomBox> choices) {
        this.choices = choices;
    }

    public CustomBox getChoice() {
        return choice;
    }

    public void setChoice(CustomBox choice) {
        this.choice = choice;
    }

    public int transition() {
        /*switch (choice) {
            case "View account info":
                return "viewAcc"; 
            case "Change your password":
                return "changePw";
            case "Change contact info":
                return "changeContactInfo";
    
            default:
                return null;
        }*/
        return choice.getOrderid();
    }

}
