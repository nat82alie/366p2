import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.el.ELContext;
import javax.faces.bean.ManagedProperty; 
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;

@Named(value = "company")
@SessionScoped
@ManagedBean
public class Company implements Serializable {

    @ManagedProperty(value = "#{login}")
    private Login login;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    private DBConnect dbConnect = new DBConnect();
    private String userlogin;
    private String pwd;
    private String companyname;
    private String contactname;
    private String email;
    private String phone;
    private String street1;
    private String street2 = null;
    private String city;
    private String state;
    private String zip; 
    private String country;

    public String getUserlogin() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Login login = (Login) elContext.getELResolver().getValue(elContext, 
                                                                null, "login");
        return login.getLogin();
    }
    
    public void setUserlogin(String userlogin) {
        this.userlogin = userlogin; 
    }
        
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String createCompany() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Statement statement = con.createStatement();

        String insert = "insert into company (login, pwd, companyname, "
                + "contactname, email, phone, street1, street2, city, state, "
                + "zip, country) values (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(insert);
        preparedStatement.setString(1, userlogin);
        preparedStatement.setString(2, pwd);
        preparedStatement.setString(3, companyname);
        preparedStatement.setString(4, contactname);
        preparedStatement.setString(5, email);
        preparedStatement.setString(6, phone);
        preparedStatement.setString(7, street1); 
        preparedStatement.setString(8, street2);
        preparedStatement.setString(9, city); 
        preparedStatement.setString(10, state); 
        preparedStatement.setString(11, zip); 
        preparedStatement.setString(12, country); 
        preparedStatement.executeUpdate();
        statement.close();
        con.commit();
        con.close();
        
        return "main";
    }

    public String deleteCompany() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Statement statement = con.createStatement();
        statement.executeUpdate("delete from company where login = " 
                                                                   + userlogin);
        statement.close();
        con.commit();
        con.close();
        Util.invalidateUserSession();
        return "main";
    }
    
    public String editPage() {
        return "changeContactInfo";
    }
    
    public String updateCompany() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        String update = "update company "
                      + "set companyname = ?, contactname = ?, email = ?, "
                      + "phone = ?, street1 = ?, street2 = ?, "
                      + "city = ?, state = ?, zip = ?, country = ? "
                      + "where login = ?;";
        PreparedStatement ps = con.prepareStatement(update); 
        ps.setString(1, companyname);
        ps.setString(2, contactname);
        ps.setString(3, email);
        ps.setString(4, phone);
        ps.setString(5, street1); 
        ps.setString(6, street2);
        ps.setString(7, city);
        ps.setString(8, state);
        ps.setString(9, zip);
        ps.setString(10, country);
        ps.setString(11, getUserlogin());
        System.out.println(companyname + " " + contactname + " " + email + " " + phone + " " + 
                street1 + " " + city + " " + state + " " + zip + " " + country+" "+getUserlogin());
        ps.executeUpdate();
        con.commit();
        con.close();
        
        return "editDone";
    }

    public String changePassword() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
         
        String update = "update company set pwd = ? where login = ?;";
        PreparedStatement ps = con.prepareStatement(update); 
        String login = getUserlogin();
        ps.setString(1, pwd);
        ps.setString(2, login); 
        ps.executeUpdate(); 
        con.commit();
        con.close();
        Util.invalidateUserSession();
        return "changePass"; 
    }
    
    public String cancel() {
        return "cancel";
    }

    public Company getCompany() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        String select = "select * from company where login = ?";
        PreparedStatement ps = con.prepareStatement(select);
        ps.setString(1, getUserlogin()); 
        ResultSet result = ps.executeQuery();
        result.next();

        companyname = result.getString("companyname");
        contactname = result.getString("contactname");
        email = result.getString("email");
        phone = result.getString("phone");
        street1 = result.getString("street1");
        street2 = result.getString("street2");
        city = result.getString("city");
        state = result.getString("state");
        zip = result.getString("zip");
        country = result.getString("country");
        return this;
    }

    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException {
        Connection con = dbConnect.getConnection();
        con.setAutoCommit(false);

        String selectStmt = "select * from company where login='" + 
                            getUserlogin() + "' and pwd='" + value + "'";
        try (Statement stmt = con.createStatement()) {
            stmt.execute(selectStmt);
            ResultSet res = stmt.executeQuery(selectStmt);
            if (!res.next()) {
                FacesMessage badmes = new FacesMessage("Login/Password incorrect");
                throw new ValidatorException(badmes);
            } else {
                changePassword();
            }
        } catch (SQLException e) {
            con.rollback();
        }
    }

    public void companyLoginExists(FacesContext context, 
                                  UIComponent componentToValidate, Object value)
            throws ValidatorException, SQLException {

        if (!existsCompanyLogin((String) value)) {
            FacesMessage errorMessage = new FacesMessage("User does not exist");
            throw new ValidatorException(errorMessage);
        }
    }

    public void validateCompanyLogin(FacesContext context, 
                                  UIComponent componentToValidate, Object value)
            throws ValidatorException, SQLException {
        String loginUser = (String) value;
        if (existsCompanyLogin((String) loginUser)) {
            FacesMessage errorMessage = new FacesMessage("User already exists");
            throw new ValidatorException(errorMessage);
        }
    }

    private boolean existsCompanyLogin(String userLogin) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        String select = "select * from company where login = ";
        PreparedStatement ps = con.prepareStatement(select + userLogin);

        ResultSet result = ps.executeQuery();
        if (result.next()) {
            result.close();
            con.close();
            return true;
        }
        result.close();
        con.close();
        return false;
    }
    
    public void refresh() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context,
                                            context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse(); /* optional */ 
    }
}
