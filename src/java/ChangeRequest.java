import java.io.Serializable;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;
import java.sql.*;
import javax.el.ELContext;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

/**
 *
 * @author team1
 */
@Named(value = "changerequest")
@ManagedBean
@SessionScoped
public class ChangeRequest implements Serializable {
    @ManagedProperty(value = "#{login}")
    private Login login;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    private DBConnect dbConnect = new DBConnect();
    private ArrayList<Integer> choices;
    private int choice; 
    private int id;
    private String company;
    private int orderid;
    private String message;
    
    public ArrayList<Integer> getChoices() throws SQLException {
        CustomBox cb = new CustomBox();
        choices = cb.getCustomBoxOrders();
        return choices;
    }

    public void setChoices(ArrayList<Integer> choices) {
        this.choices = choices;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Login login = (Login) elContext.getELResolver().getValue(elContext, 
                                                                null, "login");
        return login.getLogin();
    }
    
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String sendRequest() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        String insert = "insert into changerequest (company, orderid, message)"
                + " values (?,?,?)";
        PreparedStatement ps = con.prepareStatement(insert);
        ps.setString(1, getUser());
        ps.setInt(2, choice);
        ps.setString(3, message);
        ps.executeUpdate();
        con.commit();
        con.close();
        choice = 0;
        message = null;
        return "messageSent";
    }
    
    // add function to grab the message for the employee 
        public List<ChangeRequest> getMessages() throws SQLException {

        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps
                = con.prepareStatement("select * from changerequest");

        //get roomprices data from database
        ResultSet result = ps.executeQuery();

        List<ChangeRequest> list = new ArrayList<ChangeRequest>();

        while (result.next()) {
            ChangeRequest rp = new ChangeRequest();

            rp.setId(result.getInt("id"));
            rp.setCompany(result.getString("company"));
            rp.setOrderid(result.getInt("orderid"));
            rp.setMessage(result.getString("message"));
            
            //store all data into a List
            list.add(rp);
        }
        result.close();
        con.close();
        id = 0;
        company = null;
        orderid = 0;
        message = null;
        return list;
    }
}
