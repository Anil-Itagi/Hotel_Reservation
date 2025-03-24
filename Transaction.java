import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Transaction {
    public static void main(String[] args) {
         try {
            String url = "jdbc:mysql://localhost:3306/bank";
            String username = "root";
            String password = "appa@123";
            String withdrawQuery = "update new_bank set balance=balance- ? where account_num=? ";
            String depositeQuery = "update new_bank set balance=balance+ ? where account_num=? ";

            Connection conn = DriverManager.getConnection(url, username, password);

            conn.setAutoCommit(false);
            PreparedStatement withdrawStmt = conn.prepareStatement(withdrawQuery);
            PreparedStatement depositeStmt = conn.prepareStatement(depositeQuery);

            withdrawStmt.setDouble(1, 100.00);
            withdrawStmt.setString(2, "101");
  
            depositeStmt.setDouble(1, 100.00);
            depositeStmt.setString(2, "100");


            int withdrawRowsAffected = withdrawStmt.executeUpdate();
            int depositeRowsAffected = depositeStmt.executeUpdate();

            if (withdrawRowsAffected > 0 && depositeRowsAffected > 0) {
                conn.commit();
                System.out.println("amount transfer successfull");
            }
            else {
                conn.rollback();
                System.out.println("amount transfer failed");
            }

         }
         catch(Exception e){
             e.printStackTrace();
         }
    }
}
