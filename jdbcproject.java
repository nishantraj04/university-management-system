import java.sql.*; 
import java.util.Scanner; 
 
public class jdbcproject { 
    static Scanner sc = new Scanner(System.in); 
 
    public static void main(String[] args) { 
        // Update with your actual database name and password 
        String url = "jdbc:mysql://localhost:3306/university"; 
        String user = "root"; 
        String password = "YOUR_PASSWORD_HERE"; // <-- CHANGE THIS TO YOUR 
MYSQL PASSWORD 
 
        try (Connection conn = DriverManager.getConnection(url, user, password)) { 
            int ch; 
            if (conn != null) { 
                System.out.println("Connected to the database!"); 
            } 
             
            do { 
                System.out.println("\n\n***** University Management System *****"); 
                System.out.println("1. Show Student Records"); 
                System.out.println("2. Add Student Record"); 
                System.out.println("3. Delete Student Record"); 
                System.out.println("4. Update Student Information"); 
                System.out.println("5. Show Instructor Details"); 
                System.out.println("6. Show Course Details with Enrolled Students"); 
                System.out.println("7. Show Course Details taken by Instructor"); 
                System.out.println("8. Deposit HRA To Salary"); 
                System.out.println("9. Deduct TDS From Salary"); 
                System.out.println("10. EXIT The Program"); 
                System.out.print("Enter your choice(1-10): "); 
                 
                ch = sc.nextInt(); 
                sc.nextLine(); // Consume newline 
 
                switch (ch) { 
                    case 1: 
                        showStudents(conn); 
                        break; 
                    case 2: 
                        addStudent(conn); 
                        showStudents(conn); 
                        break; 
                    case 3: 
                        deleteStudent(conn); 
                        showStudents(conn); 
                        break; 
                    case 4: 
                        updateStudent(conn); 
                        showStudents(conn); 
                        break; 
                    case 5: 
                        System.out.print("Enter Instructor ID: "); 
                        showInstructor(conn, sc.nextLine()); 
                        break; 
                    case 6: 
                        showCourseEnrollment(conn); 
                        break; 
                    case 7: 
                        showInstructorCourses(conn); 
                        break; 
                    case 8: 
                        updateSalary(conn, "HRA"); 
                        break; 
                    case 9: 
                        updateSalary(conn, "TDS"); 
                        break; 
                    case 10: 
                        System.out.println("Exiting..."); 
                        break; 
                    default: 
                        System.out.println("Please enter valid input (1-10)"); 
                } 
            } while (ch != 10); 
             
            conn.close(); 
             
        } catch (SQLException e) { 
            System.out.println("Connection failed: " + e.getMessage()); 
        } 
    } 
 
    // --- Database Operation Methods --- 
 
    private static void showStudents(Connection conn) throws SQLException { 
        String query = "SELECT * FROM student"; 
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) { 
            System.out.printf("%-10s | %-20s | %-20s | %-10s\n", "ID", "Name", "Dept", "Credits"); 
            while (rs.next()) { 
                System.out.printf("%-10s | %-20s | %-20s | %-10d\n",  
                    rs.getString("ID"), rs.getString("name"), rs.getString("dept_name"), 
rs.getInt("tot_cred")); 
            } 
        } 
    } 
 
    private static void addStudent(Connection conn) throws SQLException { 
        System.out.print("Enter ID: "); String id = sc.nextLine(); 
        System.out.print("Enter Name: "); String name = sc.nextLine(); 
        System.out.print("Enter Dept: "); String dept = sc.nextLine(); 
        System.out.print("Enter Credits: "); int cred = sc.nextInt(); 
        String sql = "INSERT INTO student VALUES (?, ?, ?, ?)"; 
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) { 
            pstmt.setString(1, id); pstmt.setString(2, name); 
            pstmt.setString(3, dept); pstmt.setInt(4, cred); 
            pstmt.executeUpdate(); 
            System.out.println("Student added."); 
        } 
    } 
 
    private static void deleteStudent(Connection conn) throws SQLException { 
        System.out.print("Enter ID to delete: "); 
        String id = sc.nextLine(); 
        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM student WHERE 
ID = ?")) { 
            pstmt.setString(1, id); 
            pstmt.executeUpdate(); 
            System.out.println("Record deleted."); 
        } 
    } 
 
    private static void updateStudent(Connection conn) throws SQLException { 
        System.out.print("Enter Student ID: "); String id = sc.nextLine(); 
        System.out.println("1: Name | 2: Dept | 3: Credits"); 
        int choice = sc.nextInt(); sc.nextLine(); 
        String col = choice == 1 ? "name" : (choice == 2 ? "dept_name" : "tot_cred"); 
        System.out.print("Enter new value: "); String val = sc.nextLine(); 
        String sql = "UPDATE student SET " + col + " = ? WHERE ID = ?"; 
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) { 
            pstmt.setString(1, val); pstmt.setString(2, id); 
            pstmt.executeUpdate(); 
        } 
    } 
 
    private static void showInstructor(Connection conn, String id) throws SQLException { 
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM instructor 
WHERE ID = ?")) { 
            pstmt.setString(1, id); 
            ResultSet rs = pstmt.executeQuery(); 
            if (rs.next()) System.out.printf("ID: %s | Name: %s | Salary: %.2f\n", rs.getString("ID"), 
rs.getString("name"), rs.getDouble("salary")); 
            else System.out.println("Not found."); 
        } 
    } 
 
    private static void showCourseEnrollment(Connection conn) throws SQLException { 
        System.out.print("Enter Course ID: "); String cid = sc.nextLine(); 
        String sql = "SELECT s.ID, s.name, t.semester, t.year FROM takes t JOIN student s ON 
t.ID = s.ID WHERE t.course_id = ?"; 
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) { 
            pstmt.setString(1, cid); 
            ResultSet rs = pstmt.executeQuery(); 
            while (rs.next()) System.out.printf("ID: %s | Name: %s | %s %d\n", rs.getString("ID"), 
rs.getString("name"), rs.getString("semester"), rs.getInt("year")); 
        } 
    } 
 
    private static void showInstructorCourses(Connection conn) throws SQLException { 
        System.out.print("Enter Instructor ID: "); String iid = sc.nextLine(); 
        String sql = "SELECT course_id FROM teaches WHERE ID = ?"; 
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) { 
            pstmt.setString(1, iid); 
            ResultSet rs = pstmt.executeQuery(); 
            while (rs.next()) System.out.println("Taught Course: " + rs.getString("course_id")); 
        } 
    } 
 
    private static void updateSalary(Connection conn, String type) throws SQLException { 
        System.out.print("Enter Instructor ID: "); String id = sc.nextLine(); 
        double multiplier = type.equals("HRA") ? 1.15 : 0.80; 
        try (PreparedStatement pstmt = conn.prepareStatement("UPDATE instructor SET salary = 
salary * ? WHERE ID = ?")) { 
            pstmt.setDouble(1, multiplier); pstmt.setString(2, id); 
            if (pstmt.executeUpdate() > 0) { 
                System.out.println(type + " processed."); 
                showInstructor(conn, id); 
            } 
        } 
    } 
}
