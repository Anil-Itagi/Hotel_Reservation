import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Img_insert {
    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/hotel_db";
            String username = "root";
            String query="insert into image_table (image_data) values (?) ";
            String password = "appa@123";
            String img_path="./colab.png";
            Connection conn = DriverManager.getConnection(url, username, password);
            FileInputStream fileInput = new FileInputStream(img_path);
            byte[] imageData = new byte[fileInput.available()];
            fileInput.read(imageData);
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setBytes(1, imageData);
            int affectedRows = preparedStatement.executeUpdate();
          
            if (affectedRows > 0) {
                System.out.println("Image inserted successfully");
            }
             
            else {
                System.out.println("Image insertion failed");
            }
            conn.close();
            
        } catch (Exception e) {
            System.out.println("Failed to connnect ");
        }
    }
}
