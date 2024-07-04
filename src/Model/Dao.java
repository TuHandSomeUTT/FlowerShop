package Model;

import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class Dao {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public int insertProduct1(Product p) {
        int rowsAffected = 0;
        PreparedStatement ps = null;
        Connection con = null;

        try {
            // Kết nối tới cơ sở dữ liệu
            con = MyConnection.getConnection();

            // SQL Query
            String sql = "INSERT INTO product(name, price, image) VALUES (?, ?, ?)";
            System.out.println(sql);

            // Create PreparedStatement object
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setBytes(3, p.getImage());

            // Execute query  
            rowsAffected = ps.executeUpdate();

            // Check result
            if (rowsAffected > 0) {
                System.out.println("You executed query: " + sql);
                System.out.println("Product inserted successfully");
            } else {
                System.out.println("You executed query: " + sql);
                System.out.println("No rows affected");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng các tài nguyên đúng cách
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    MyConnection.closeConnection(con);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rowsAffected;
    }
//Done :V out đây
    public ArrayList<Product> getAllProduct() {
        ArrayList<Product> danhsachHoa = new ArrayList<>();
        PreparedStatement ps = null;
        Connection con = null;

        try {
            // Kết nối cơ sở dữ liệu
            con = MyConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM product");
            rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setImage(rs.getBytes("image"));
                danhsachHoa.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng các tài nguyên đúng cách
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    MyConnection.closeConnection(con);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return danhsachHoa;
    }

    public void getallProduct(JTable table) {
        String sql = "SELECT * FROM product ORDER BY id DESC";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                Object[] row = new Object[4];
                row[0] = rs.getInt("id");
                row[1] = rs.getString("name");
                row[2] = rs.getDouble("price");
                row[3] = rs.getBytes("image"); // Store image bytes

                model.addRow(row);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    MyConnection.closeConnection(con);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean update(Product product) {
        String sql = "UPDATE product SET name = ?, price = ? WHERE id = ?";
        PreparedStatement ps = null;
        Connection con = null;

        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    MyConnection.closeConnection(con);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean delete(Product product){
        try {
            ps = con.prepareStatement("delete from product where id = ? ");
            ps.setInt(1,product.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
        
    }
}
