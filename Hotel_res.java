
import java.sql.*;
import java.util.Scanner;


public class Hotel_res {

	public static void main(String[] args) {
		
		//Driver load
		
		try {
			String url="jdbc:mysql://localhost:3306/mydb1";
			String username="root";
			// String query="select * from customers";
			String password="appa@123";
			Connection conn= DriverManager.getConnection(url,username,password);
			// System.out.println("Connection successfull"+conn);
			// Statement stmt=conn.createStatement();
			// ResultSet rs=stmt.executeQuery(query);
			while(true) {
                System.out.println();
                System.out.println("Hotel Managament System");
                Scanner sc = new Scanner(System.in);
                System.out.println("1. Reverse a room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.print("Choose an option : ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        reserveRoom(conn, sc);
                        break;
                    case 2:
                        viewReservation(conn);
                        break;

                    case 3:
                        getRoomNumber(conn, sc);
                        break;
                    case 4:
                        updateReservation(conn, sc);
                        break;

                    case 5:
                        deleteReservation(conn, sc);
                        break;
                    
                    case 0:
                        exit();
                        sc.close();
                        return;
        
                    default:
                        System.out.println("Invalid choice . Try again.");
                }
				
			}
			
			// System.out.println("connection closed");
			
		}
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
		
	}

}
