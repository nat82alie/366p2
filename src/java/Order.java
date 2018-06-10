import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.el.ELContext;
import javax.faces.bean.ManagedProperty; 
import java.util.Date; 
import java.util.TimeZone; 
import java.util.Random;
import java.util.Date;  

@Named(value = "order")
@SessionScoped
@ManagedBean
public class Order {
    private DBConnect dbConnect = new DBConnect();
    private Integer id;
    private String company;
    private java.util.Date date;
    private Integer quantity;
    private Integer manid;
    private Integer trackid;
    private Double cost;

    public DBConnect getDbConnect() {
        return dbConnect;
    }

    public Integer getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public Date getDate() {
        return date;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getManid() {
        return manid;
    }

    public Integer getTrackid() {
        return trackid;
    }

    public Double getCost() {
        return cost;
    }

    public void setDbConnect(DBConnect dbConnect) {
        this.dbConnect = dbConnect;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setManid(Integer manid) {
        this.manid = manid;
    }

    public void setTrackid(Integer trackid) {
        this.trackid = trackid;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
    
    public List<Order> getOrders() throws SQLException {

        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps
                = con.prepareStatement(
                        "select * from customerorder order by date ASC");

        //get roomprices data from database
        ResultSet result = ps.executeQuery();

        List<Order> list = new ArrayList<Order>();

        while (result.next()) {
            Order rp = new Order();

            rp.setId(result.getInt("id"));
            rp.setCompany(result.getString("company"));
            rp.setDate(result.getDate("date"));
            rp.setQuantity(result.getInt("quantity"));
            rp.setManid(result.getInt("manufacturerid"));
            rp.setTrackid(result.getInt("trackingid"));
            rp.setCost(result.getDouble("totalcost"));
            
            //store all data into a List
            list.add(rp);
        }
        result.close();
        con.close();
        return list;
    }
    
    public String deleteOrder() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Statement statement = con.createStatement();
        statement.executeUpdate("Delete from custombox where orderid = '" + id + "'");
        statement.executeUpdate("Delete from customerorder where id = '" + id + "'");
        statement.close();
        con.commit();
        con.close();
        id = null;
        return "main";
    }
}
