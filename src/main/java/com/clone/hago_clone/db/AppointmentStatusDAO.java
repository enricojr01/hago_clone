/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clone.hago_clone.db;
import com.clone.hago_clone.models.AppointmentStatusBean;
import com.clone.hago_clone.models.AppointmentBean;
import com.clone.hago_clone.models.StatusBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author anonymous
 */
public class AppointmentStatusDAO extends BaseDAO {
    private AppointmentDAO appointment;
    private StatusDAO status;
    
    public AppointmentStatusDAO(String url,String username, String password) throws ClassNotFoundException {
        super(url,username,password);
        this.appointment = new AppointmentDAO(url,username,password);
        this.status = new StatusDAO(url,username,password);        
    }
    
    
    @Override
    protected String createTableStatement() {
        return "CREATE IF NOT EXISTS AppointmentStatus(\n"
                + "id INT NOT NULL AUTO_INCREMENT,\n"
                + "appointmentId INT NOT NULL,\n"
                + "statusId INT NOT NULL,\n"
                + "PRIMARY KEY (id),\n"
                + "FOREIGN KEY (appointmentId) REFERENCES Appointment(id),\n"
                + "FOREIGN KEY (statusId) REFERENCES Status(id))";                                
    }

    @Override
    protected String dropTableStatement() {
        return "DROP TABLE AppointmentStatus";
    }
    
    //Create Functions
    public AppointmentStatusBean createAppointmentStatus(AppointmentBean ab,StatusBean sb) throws SQLException {                       
        AppointmentStatusBean retval = null;
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO AppointmentStatus (appointmentId,statusId) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);
        
        if(ps.executeUpdate() > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            int id = rs.getInt(1);
            retval = new AppointmentStatusBean(id,ab,sb);
            rs.close();
        }
        ps.close();
        c.close();
                
        return retval;
    }
        
    //Read Functions
    public ArrayList<AppointmentStatusBean> getAllAppointmentStatus() throws SQLException {        
        ArrayList<AppointmentStatusBean> retval = new ArrayList();
        Connection c = getConnection();
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT id, appointmentId, statusId FROM AppointmentStatus");
        while(rs.next()) {
            int id = rs.getInt("id"),
                appointmentId = rs.getInt("appointmentId"),
                statusId = rs.getInt("statusId");
            AppointmentStatusBean tmp = new AppointmentStatusBean(id,
                    appointment.findAppointmentById(appointmentId),
                        status.findStatusById(statusId));
            retval.add(tmp);
                    
        }
        rs.close();
        s.close();
        c.close();        
        return retval;                
    
    }
    
    //Update Functions
    
    //Delete Functions
    public boolean deleteAppointmentStatus(AppointmentStatusBean asb) throws SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM AppointmentStatus WHERE id = ?");
        ps.setInt(1, asb.getId());
        
        boolean retval = (ps.executeUpdate() > 0);
        
        ps.close();
        c.close();
        return retval;
    }
    
}
