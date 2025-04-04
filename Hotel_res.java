
import java.sql.*;
import java.util.Scanner;


public class Hotel_res {

    public static void main(String[] args) {

        //Driver load

        try {
            String url = "jdbc:mysql://localhost:3306/hotel_db";
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

            if (isRoomEmpty(conn, roomNumber)) {
                System.out.println("Sorry this Room number is already Reserved");
                return;
            }

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
    
    private static boolean isRoomEmpty(Connection connection, int roomNumber) {

        String sql = "select reservation_id from reservations where room_number=" + roomNumber;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql);
        )
        {

            return resultSet.next();
        
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    

    private static void viewReservation(Connection conn) {
        String sql = "select reservation_id, guest_name, room_number,contact_number, reservation_date from reservations";

        try (Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("current Reservations are");
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------");
            System.out.println(
                    "|   Reservation ID     |         Guest        |       Room Number      |      Contact Number      |          Date      |");
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------");

            while (resultSet.next()) {
                int reservation_id = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("guest_name");
                int room_number = resultSet.getInt("room_number");
                String contactNumber = resultSet.getString("contact_number");
                String reservationDate = resultSet.getTimestamp("reservation_date").toString();
                System.out.printf("| %-20d | %-20s |  %-20d | %-20s | %-20s |\n", reservation_id, guestName,
                        room_number, contactNumber, reservationDate);

            }
            System.out.println(
                    "-------------------------------------------------------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static void getRoomNumber(Connection conn, Scanner sc) {
        try {

            System.out.println("Enter reservation ID:  ");
            int reservationId = sc.nextInt();
            System.out.println("Enter the guest Name");
            String guestName = sc.next();

            String sql = "Select room_number from reservations where reservation_id=" + reservationId +" and guest_name= '"+guestName+"'";


            try (
                    Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);) 
                    {
                if (resultSet.next()) {
                    int roomNumber = resultSet.getInt("room_number");
                    System.out.println("Room number for Reservation ID" + reservationId + " and Guest " + guestName
                            + " is " + roomNumber);
                } else {
                    System.out.println("Reservation not found for given ID and Guest");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private static void updateReservation(Connection conn, Scanner sc) {
        try {
            System.out.println("Enter the ID to Update");
            int id = sc.nextInt();
            sc.nextLine();

            if (!reservationExists(conn, id)) {
                System.out.println("Reservation not found for the given ID");
                return;
            }
            System.out.print("Enter guest name: ");
            String guestName = sc.next();
            sc.nextLine();
            System.out.print("Enter room number :");
            int roomNumber = sc.nextInt();
            System.out.println("Enter the contact number");
            String contactNumber = sc.next();

            String sql = "Update reservations set guest_name='" + guestName + "' "+", room_number=" + roomNumber
                    + ", contact_number='" + contactNumber + "' " + "where reservation_id=" + id + ";";
            try (Statement statement = conn.createStatement();) {
                int affectedRows = statement.executeUpdate(sql);
                if (affectedRows > 0) {
                    System.out.println("Reservation Updated successfully");

                } else {
                    System.out.println("Reservation update failed.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    private static boolean reservationExists(Connection conn, int id) {
        try {
            String sql = "select reservation_id  from reservations where reservation_id=" + id;
            try (Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }
    }
    
    public static void exit() throws InterruptedException {
        System.out.print("Existing System");
        int i = 5;
        while (i != 0) {
            System.out.println(".");
            Thread.sleep(450);
            i--;
        }

        System.out.println();
        System.out.println("Thank you For Using Hotel Reservation System!!!");
    }
    

    private static void deleteReservation(Connection conn, Scanner sc) {
        try {

            System.out.println("Enter the Reservation id ");
            int reservationId = sc.nextInt();
            System.out.println("Enter the Guest Name");
            String guestName = sc.next();
            String sql = "delete from reservations where reservation_id=" + reservationId + " and guest_name='"
                    + guestName + "';";

            try (Statement statement = conn.createStatement();) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Delete successfull");
                }
                else {
                    System.out.println("Delition failed");
                }
            }


            
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


}
