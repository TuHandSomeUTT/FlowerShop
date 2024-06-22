/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author VHC
 */
public class Dao {
    
    Connection con = MyConnection.getConnection();
    PreparedStatement ps ;
    Statement st;
    ResultSet rs;
    
    public boolean insertProduct ( Product p){
    
        String sql = " them san pham(name, price, image ) values(?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,p.getName());
            ps.setDouble(2,p.getPrice());
            ps.setBytes(3,p.getImage());
            return  ps.executeUpdate() > 0;
            
        } catch (Exception ex) {
            return false;
        }
    }
    
    public void getallProducts(JTable table){
    String sql = " select * from product order by id desc ";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table .getModel();
            Object[] row;
            
            while(rs.next()){
                row = new Object[4];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getDouble(3);
                row[3] = rs.getBytes(4);
                model.addRow(row);
            
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
