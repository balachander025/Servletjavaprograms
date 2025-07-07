import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pooling")
public class pooling extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out=response.getWriter();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Lookup DataSource
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/student");

            // Get connection from pool
            conn = ds.getConnection();
            
            // Insert a user
            ps = conn.prepareStatement("INSERT INTO employee (name, email) VALUES (?, ?)");
            ps.setString(1, "Balachander");
            ps.setString(2, "bala1234@gmail.com");
            ps.executeUpdate();

            // Query users
            ps = conn.prepareStatement("SELECT * FROM employee");
            rs = ps.executeQuery();

            out.println("<h1>User List</h1>");
            out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Email</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("id") + "</td><td>" + rs.getString("name") + "</td><td>" + rs.getString("email") + "</td></tr>");
            }
            out.println("</table>");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close(); // Return connection to pool
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	
	}
}

