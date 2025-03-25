import java.sql.*;
import java.util.Scanner;

public class Batch_pro {
    public static void main(String[] args) {
        
        try {

            String url = "jdbc:mysql://localhost:3306/bank";
            String username = "root";
            String password = "appa@123";
            Connection conn = DriverManager.getConnection(url, username, password);
            String query = "insert into new_bank(account_num , balance) values (? ,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            Scanner sc = new Scanner(System.in);
            conn.setAutoCommit(false);
            while (true) {
                System.out.print("Enter the Account Number : ");
                String accountNum = sc.next();
                System.out.print("Enter the balance amount : ");
                double balance = sc.nextDouble();
                preparedStatement.setString(1, accountNum);
                preparedStatement.setDouble(2, balance);
                preparedStatement.addBatch();
                System.out.println("Add more values Y/N");
                String decision = sc.next();
                if (decision.toUpperCase().equals("N")) {
                    break;
                }
            }

            int batchResult[] = preparedStatement.executeBatch();
            conn.commit();

            for (int i : batchResult) {
                System.out.println(i);
            }

            
        } catch (Exception e) {
            System.out.println("connection failed"+e.getMessage());
        }
    }
}
