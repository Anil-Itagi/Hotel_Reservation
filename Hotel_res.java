
import java.sql.*;
import java.util.Scanner;


public class Hotel_res {

    public static void main(String[] args) {

        //Driver load

        try {
            String url = "jdbc:mysql://localhost:3306/mydb1";
            String username = "root";
            // String query="select * from customers";
            String password = "appa@123";
            Connection conn = DriverManager.getConnection(url, username, password);
            // System.out.println("Connection successfull"+conn);
            // Statement stmt=conn.createStatement();
            // ResultSet rs=stmt.executeQuery(query);
            while (true) {
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

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    
    private static void reserveRoom(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter guest name: ");
            String guestName = sc.next();
            sc.nextLine();
            System.out.print("Enter room number :");
            int roomNumber = sc.nextInt();
            System.out.println("Enter the contact number");
            String contactNumber = sc.next();

            String sql = "Insert into reservations(guest_name , room_number, contact_number)" +
                    "values ('" + guestName + "'," + roomNumber + ",'" + contactNumber + "')";

            try (Statement statemet = conn.createStatement()) {

                int affectedRows = statemet.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation successfull");
                } else {
                    System.out.println("Reservation failed");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private static void viewReservation(Connection conn) {
        String sql = "select reservation_id, quest_name, room_number,contact_number, reservation_date from reservations";

        try (Statement statement = conn.createStatement();
             ResultSet resultSet=statement.executeQuery(sql)
        ) {
            
            System.out.println("current Reservations are");
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("|   Reservation ID   |   Guest        |    Room Number    |   Contact Number    |    Date           |");
            System.out.println(
                    "------------------------------------------------------------------------------------------");
             
            while (resultSet.next()) {
                int reservation_id = resultSet.getInt("reservation_id");
                String questName = resultSet.getString("guest_name");
                int room_number = resultSet.getInt("room_number");
                String contactNumber = resultSet.getString("contact_number");
                String reservationDate = resultSet.getTimestamp("reservation_date").toString();
                System.out.printf("| %-14d | %-15s |  %-13d | %-20s | %-19s |\n", reservation_id, questName,room_number, contactNumber, reservationDate);

            }
                        System.out.println("------------------------------------------------------------------------------------------");

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
