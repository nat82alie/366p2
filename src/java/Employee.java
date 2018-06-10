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
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.faces.bean.ManagedProperty;

@Named(value = "employee")
@SessionScoped
@ManagedBean
public class Employee implements Serializable {

    @ManagedProperty(value = "#{login}")
    private Login login;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    private DBConnect dbConnect = new DBConnect();
    private String emplID;
    private String pwd;
    private String name;
    private String email;
    private String phone;
    private boolean isAdmin;
    private String myLogin;

    public String getMyLogin() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Login login = (Login) elContext.getELResolver().getValue(elContext, null, "login");
        return login.getLogin();
    }

    public void setMyLogin(String myLogin) {
        this.myLogin = myLogin;
    }
    //this method gets by numerical id, but we grab by login, so not sure if we need this ish
    public String getEmployeeID() throws SQLException {
        if (emplID == null) {
            Connection con = dbConnect.getConnection();

            if (con == null) {
                throw new SQLException("Can't get database connection");
            }

            PreparedStatement ps
                    = con.prepareStatement("select Login from Employee");
            ResultSet result = ps.executeQuery();
            if (!result.next()) {
                return null;
            }
            emplID = result.getString("Login");
            result.close();
            con.close();
        }
        return emplID;
    }

    public String getEmplID() {
        return emplID;
    }

    public void setEmplID(String emplID) {
        this.emplID = emplID;
    }
    

    public void setEmployeeID(String emplID) {
        this.emplID = emplID;
    }
    
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        // ELContext elContext = FacesContext.getCurrentInstance().getELContext();
    //Login login = (Login) elContext.getELResolver().getValue(elContext, null, "login");
    
      //  return login.getLogin();
           return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String createEmployee() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Statement statement = con.createStatement();

        PreparedStatement preparedStatement = con.prepareStatement("Insert into employee (login, pwd, name, email, isadmin) values(?,?,?,?,?)");
        preparedStatement.setString(1, emplID);
        preparedStatement.setString(2, pwd);
        preparedStatement.setString(3, name);
        preparedStatement.setString(4, email);
        preparedStatement.setBoolean(5, isAdmin);
        preparedStatement.executeUpdate();
        statement.close();
        con.commit();
        con.close();
        emplID = null;
        pwd = null;
        name = null;
        email = null;
        //Util.invalidateUserSession();
        return "main";
        
    }

    public String deleteEmployee() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Statement statement = con.createStatement();
        statement.executeUpdate("Delete from employee where Login = '" + emplID + "'");
        statement.close();
        con.commit();
        con.close();
        emplID = null;
        return "main";
    }

    public String showEmployee() {
        return "showEmployee";
    }

    public Employee getEmployee() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps
                = con.prepareStatement(
                        "select * from Employee where Login = " + emplID);

        //get employee data from database
        ResultSet result = ps.executeQuery();

        result.next();
        
        pwd = result.getString("Pwd");
        name = result.getString("Name");
        email = result.getString("Email");
        phone = result.getString("Phone");
        isAdmin = result.getBoolean("Admin");
        return this;
    }

    public List<Employee> getEmployeeList() throws SQLException {

        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps
                = con.prepareStatement(
                        "select Login, Pwd, Name, Email, Phone, Admin from Employee order by Login");

        //get employee data from database
        ResultSet result = ps.executeQuery();

        List<Employee> list = new ArrayList<Employee>();

        while (result.next()) {
            Employee emp = new Employee();

            emp.setEmployeeID(result.getString("Login"));
            emp.setPwd(result.getString("Pwd"));
            emp.setName(result.getString("Name"));
            emp.setEmail(result.getString("Email"));
            emp.setPhone(result.getString("Phone"));
            emp.setIsAdmin(result.getBoolean("Admin"));

            //store all data into a List
            list.add(emp);
        }
        result.close();
        con.close();
        return list;
    }

    public void employeeIDExists(FacesContext context, UIComponent componentToValidate, Object value)
            throws ValidatorException, SQLException {

        if (!existsEmployeeId((String) value)) {
            FacesMessage errorMessage = new FacesMessage("ID does not exist");
            throw new ValidatorException(errorMessage);
        }
    }

    public void validateEmployeeID(FacesContext context, UIComponent componentToValidate, Object value)
            throws ValidatorException, SQLException {
        String id = (String) value;
//        if (id < 0) {
//            FacesMessage errorMessage = new FacesMessage("ID must be positive");
//            throw new ValidatorException(errorMessage);
//        }
        if (existsEmployeeId(id)) {
            FacesMessage errorMessage = new FacesMessage("ID already exists");
            throw new ValidatorException(errorMessage);
        }
    }

    private boolean existsEmployeeId(String id) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement("select * from employee where login = '" + id + "'");

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
    
        
    public String changepass() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
         
        String update = "update employee set pwd = ? where login = ?;";
        PreparedStatement ps = con.prepareStatement(update); 
        String login = getMyLogin();
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
        
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException {
        Connection con = dbConnect.getConnection();
        con.setAutoCommit(false);

        String selectStmt = "select * from employee where login='" + 
                            getMyLogin() + "' and pwd='" + value + "'";
        try (Statement stmt = con.createStatement()) {
            stmt.execute(selectStmt);
            ResultSet res = stmt.executeQuery(selectStmt);
            if (!res.next()) {
                FacesMessage badmes = new FacesMessage("Login/Password incorrect");
                throw new ValidatorException(badmes);
            } else {
                changepass();
            }
        } catch (SQLException e) {
            con.rollback();
        }
    }
        
    public String changecontact() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
         
        String update = "update employee set email = ? where login = ?;";
        PreparedStatement ps = con.prepareStatement(update); 
        String login = getMyLogin();
        ps.setString(1, email);
        ps.setString(2, login); 
        ps.executeUpdate(); 
        con.commit();
        con.close();
        email = null;
        pwd = null;
        Util.invalidateUserSession();
        return "main"; 
    }
}
