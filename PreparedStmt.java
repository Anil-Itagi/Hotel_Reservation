import java.sql.*;

public class PreparedStmt {
    public static void main(String[] args) {

        try {
            String url = "jdbc:mysql://localhost:3306/hotel_db";
            String username = "root";
            String query="select * from reservations  where reservation_id=? and guest_name=?";
            String password = "appa@123";
            Connection conn = DriverManager.getConnection(url, username, password);

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Anil1");
            ResultSet res = preparedStatement.executeQuery();
          
            while (res.next()) {
                int id = res.getInt("reservation_id");
                String name = res.getString("guest_name");
                System.out.println("Reservation ID :" + id + "  Guest Name  : " + name);
            }
            conn.close();
            
        } catch (Exception e) {
            System.out.println("Failed to connnect ");
        }
    }
}
