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
    private String[] companyChoices = {"View account info", "Change your password", "Change contact info",
        "View your orders", "Create a new order", "Modify an order", "Track an order"};
    private String[] employeeChoices = {"Change your password (employee)", "Delete an Order",
        "Edit your contact info", "View all orders", "Delete an order"};
    private String[] adminChoices = {"Change your password (admin)", "Add employee", "Delete employee",
        "View all orders", "Delete an order"};

    private String choice;
    private String userType = "company";
    private Login login;
    
    private String companyLogin;
    private String companyName;
    private Integer companyContactInfo;
    
    public void getLoginSession() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        login = (Login) elContext.getELResolver().getValue(elContext, null, "login");
    }
    
    public void setUserType() {
//        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//        login = (Login) elContext.getELResolver().getValue(elContext, null, "login");
        userType = login.getUserType();
    }
    
//    public void setCompanyInfo() {
//        companyLogin = login.getUserLogin();
//        companyName = login.getUserName();
//        companyContactInfo = login.getUserContactInfo();
//    }
    
    public String[] getChoices() {
        getLoginSession();
        setUserType();
//        setCompanyInfo();

        if (userType == "company") {
            choices = companyChoices;
            return companyChoices;
        }
        if (userType == "employee") {
            choices = employeeChoices;
            return employeeChoices;
        }
        if (userType == "admin") {
            choices = adminChoices;
            return adminChoices;
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
            //company choices
            case "View account info":
                return "viewAcc"; 
            case "Change your password":
                return "changePw";
            case "Change contact info":
                return "changeContactInfo";
            case "View your orders":
                return "viewYourOrders";
            case "Create a new order":
                return "createOrder";
            case "Modify an order":
                return "modifyOrder";
            case "Track an order":
                return "trackOrder";
                
            //employee choices
            case "Change your password (employee)":
                return "changeEmployeePassword";
            case "Delete an Order": //also admin
                return "deleteOrder";
            case "Edit your contact info":
                return "editYourContactInfo";
            case "View all orders": //also admin
                return "viewAllOrders";
                                
            //admin choices
            case "Change your password (admin)":
                return "changeAdminPassword";
            case "Add employee":
                return "addEmployee";
            case "Delete employee":
                return "deleteEmployee";

            //company choices -Amanda
            case "Make An Order":
                return "makeOrder";
            case "View Orders":
                return "viewOrders";
            case "My Account":
                return "myAccount";

                
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
