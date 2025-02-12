import java.sql.*;
import java.util.*;

class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/feereport";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    public static Connection getConnection() throws SQLException {
    	System.out.println("Database connected");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
class AdminDAO {
    public boolean validateAdmin(String username, String password) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM admin WHERE username=? AND password=?")) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

class AccountantDAO {
    public void addAccountant(String name, String email, String phone, String password) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO accountant (name, email, phone, password) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, password);
            ps.executeUpdate();
            System.out.println("Accountant added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class StudentDAO {
    public void addStudent(String name, String email, String course, double fee, double paid, String address, String phone) {
        double due = fee - paid;
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO student (name, email, course, fee, paid, due, address, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, course);
            ps.setDouble(4, fee);
            ps.setDouble(5, paid);
            ps.setDouble(6, due);
            ps.setString(7, address);
            ps.setString(8, phone);
            ps.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class FeeReportApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AdminDAO adminDAO = new AdminDAO();
        AccountantDAO accountantDAO = new AccountantDAO();
        StudentDAO studentDAO = new StudentDAO();
        
        System.out.println("Enter Admin Username: ");
        String username = scanner.next();
        System.out.println("Enter Admin Password: ");
        String password = scanner.next();
        
        if (adminDAO.validateAdmin(username, password)) {
            System.out.println("Admin Login Successful");
            System.out.println("1. Add Accountant\n2. Add Student\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.println("Enter Accountant Name: ");
                    String accName = scanner.nextLine();
                    System.out.println("Enter Email: ");
                    String accEmail = scanner.nextLine();
                    System.out.println("Enter Phone: ");
                    String accPhone = scanner.nextLine();
                    System.out.println("Enter Password: ");
                    String accPassword = scanner.nextLine();
                    accountantDAO.addAccountant(accName, accEmail, accPhone, accPassword);
                    break;
                case 2:
                    System.out.println("Enter Student Name: ");
                    String stuName = scanner.nextLine();
                    System.out.println("Enter Email: ");
                    String stuEmail = scanner.nextLine();
                    System.out.println("Enter Course: ");
                    String stuCourse = scanner.nextLine();
                    System.out.println("Enter Total Fee: ");
                    double stuFee = scanner.nextDouble();
                    System.out.println("Enter Paid Fee: ");
                    double stuPaid = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    System.out.println("Enter Address: ");
                    String stuAddress = scanner.nextLine();
                    System.out.println("Enter Phone: ");
                    String stuPhone = scanner.nextLine();
                    studentDAO.addStudent(stuName, stuEmail, stuCourse, stuFee, stuPaid, stuAddress, stuPhone);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("Invalid Admin Credentials");
        }
        scanner.close();
    }
}
