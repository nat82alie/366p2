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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.el.ELContext;
import javax.faces.bean.ManagedProperty; 
import java.util.Date; 
import java.util.TimeZone; 

@Named(value = "custombox")
@SessionScoped
@ManagedBean
public class CustomBox implements Serializable {

    @ManagedProperty(value = "#{login}")
    private Login login;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    private DBConnect dbConnect = new DBConnect();
    /* custombox */ 
    private int cbID;
    private double length;
    private double width;
    private double height;
    private String color;
    private int materialID;
    private String customtext;
    private double unitprice;
    private String company;
    
    /* customerorder */ 
    private int orderid; 
    private Date created_date; 
    private int quantity;
    private int manufacturerID;
    private int trackingID;
    private double totalcost; 

    public int getCbID() {
        return cbID;
    }

    public void setCbID(int id) {
        this.cbID = id;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public String getCustomtext() {
        return customtext;
    }

    public void setCustomtext(String customtext) {
        this.customtext = customtext;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }
    
    public String getCompany() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Login login = (Login) elContext.getELResolver().getValue(elContext, 
                                                                null, "login");
        return login.getLogin();
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
    
    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        this.created_date = created_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public int getTrackingID() {
        return trackingID;
    }

    public void setTrackingID(int trackingID) {
        this.trackingID = trackingID;
    }

    public double getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(double totalcost) {
        this.totalcost = totalcost;
    }

    /* we wanna generate some of the info */ 
    public String makeCustomBox() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Statement statement = con.createStatement();

        /* insert into custom box */ 
        String insert = "insert into custombox (length, width, height, color, "
                + "material, customtext, unitprice, orderid) values (?,?,?,?,?,"
                + "?,?,?)";
        PreparedStatement ps = con.prepareStatement(insert);
        ps.setDouble(1, length);
        ps.setDouble(2, width);
        ps.setDouble(3, height);
        ps.setString(4, color);
        ps.setInt(5, materialID);
        ps.setString(6, customtext);
        ps.setDouble(7, unitprice);  /* make a function to return price */ 
        ps.setString(8, company);
        ps.executeUpdate();
        statement.close();
        
        /* insert custom box into customer order */ 
        insert = "insert into customerorder (company, date, quantity, "
                + "manufacturerID, trackingID, totalcost) values (?,?,?,?,?,?)";
        ps = con.prepareStatement(insert);
        ps.setString(1, company); 
        ps.setDate(2, new java.sql.Date(created_date.getTime()));
        ps.setInt(3, quantity); 
        ps.setInt(4, manufacturerID);
        ps.setInt(5, trackingID);
        ps.setDouble(6, totalcost); 
        con.commit();
        con.close();
        
        // should i make a tracking object here too?
        
        return "placeOrder";
    }

    /*public CustomBox getCustombox() throws SQLException {
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
    }*/

    public List<CustomBox> getCustomBox() throws SQLException {

        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        String select = "select * from custombox where orderid = ?";
        PreparedStatement ps = con.prepareStatement(select);
        ResultSet result = ps.executeQuery();

        List<CustomBox> list = new ArrayList<CustomBox>();

        while (result.next()) {
            
            CustomBox cb = new CustomBox();

            cb.setCbID(result.getInt("id"));
            cb.setLength(result.getDouble("length"));
            cb.setWidth(result.getDouble("width"));
            cb.setHeight(result.getDouble("height"));
            cb.setColor(result.getString("color"));
            cb.setMaterialID(result.getInt("material"));
            cb.setCustomtext(result.getString("customtext"));
            cb.setUnitprice(result.getDouble("unitprice"));
            cb.setCompany(result.getString("orderid"));

            //store all data into a List
            list.add(cb);
        }
        result.close();
        con.close();
        return list;
    }
}
