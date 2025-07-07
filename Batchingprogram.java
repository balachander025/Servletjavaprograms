import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/batchprocessing")
public class batchprocessing extends HttpServlet {
//	 batchprocessing(){
//		 super();
//	 }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String[] names = request.getParameterValues("name");
        String[] emails = request.getParameterValues("email");
        
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Lookup the DataSource
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/student");
            conn = ds.getConnection();

            // Prepare SQL statement
            String sql = "INSERT INTO employee (name, email) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            
            // Add parameters to batch
            for (int i = 0; i < names.length; i++) {
                pstmt.setString(1, names[i]);
                pstmt.setString(2, emails[i]);
                pstmt.addBatch(); // Add to batch
            }
            
            // Execute batch
            int[] counts = pstmt.executeBatch();
            response.getWriter().println("Inserted " + counts.length + " records successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error occurred: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
