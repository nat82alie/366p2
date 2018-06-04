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
    private String[] companyChoices = {"Change your password", "Edit your address", "Edit your contact info",
        "View your orders", "Create a new order", "Modify an order", "Track an order"};
    private String[] employeeChoices = {"Change your password", "Delete an Order",
        "Edit your contact info", "View all orders", "Delete an order"};
    private String[] adminChoices = {"admin option 1", "admin option 2"};
    private String choice;
    private String userType = "";
    private Login login;
    
    private String companyLogin;
    private String companyName;
    private Integer companyContactInfo;
    
//    public void getLoginSession() {
//        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//        login = (Login) elContext.getELResolver().getValue(elContext, null, "login");
//    }
    
    public void setUserType() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        login = (Login) elContext.getELResolver().getValue(elContext, null, "login");
        
        userType = login.getUserType();
    }
    
//    public void setCompanyInfo() {
//        companyLogin = login.getUserLogin();
//        companyName = login.getUserName();
//        companyContactInfo = login.getUserContactInfo();
//    }
    
    public String[] getChoices() {
//        getLoginSession();
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
            //none of these are right:
            case "Create New Customer":
                return "newCustomer";
            case "List All Customers":
                return "listCustomers";
            case "Find Customer":
                return "findCustomer";
            case "Delete Customer":
                return "deleteCustomer";
            
            default:
                return null;
        }
    }

}
