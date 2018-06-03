import java.io.Serializable;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.el.ELContext;

import java.util.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author team1
 */
@Named(value = "selector")
@ManagedBean
@SessionScoped
public class Selector implements Serializable {

    private String[] choices;
    /* keep the choices for the companies -Amanda */ 
    private String[] companyChoices = {"Make An Order", "View Orders", 
                                                                  "My Account"}; 
    private String choice;
    private String userType = "company";
    private Login login;
    
    public void setUserType() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Login login = (Login) elContext.getELResolver().getValue(elContext, 
                                                                 null, "login");
        userType = login.getUserType();
    }
    
    public String[] getChoices() {
        setUserType();
       
        if (userType == "company") {
            choices = companyChoices;
            return companyChoices;
        }

        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String transition() {
        switch (choice) {
            //company choices -Amanda
            case "Make An Order":
                return "makeOrder";
            case "View Orders":
                return "viewOrders";
            case "My Account":
                return "myAccount";
            
            case "Change Your Password": //a,e
                return "changePwd";
            case "View Room Prices": //a,e
                return "roomPrices";
            case "Change Room Prices": //a
                return "changeRoomPrices";
            case "Add Employee": //a
                return "addEmpl";
            case "Delete Employee": //a
                return "deleteEmpl";
            case "Add A Customer": //a,e
                return "addCustomer";
            case "Delete A Customer": //a,e
                return "deleteCustomer";
            case "Check In A Customer": //a,e
                return "checkInCustomer";
            case "Check Out A Customer": //a,e
                return "checkOutCustomer";
            case "Add Charges To A Reservation": //a,e
                return "addCharges";
            case "View A Reservation": //a,e
                return "viewReservation";
            case "Create A Reservation": //a,e
                return "createReservation";
            case "Cancel A Reservation": //a,e
                return "cancelReservation";
                
            case "Check Your Reservations": //customer
                return "checkYourReservations";
            case "Create Your Reservation": //customer
                return "createYourReservation";
            case "Cancel Your Reservation": //customer
                return "cancelYourReservation";
            default:
                return null;
        }
    }
    
    /* keep this for logout functionality on main user page -Amanda */
    public String logout() {
        Util.invalidateUserSession();
        return "logout";
    }

}
