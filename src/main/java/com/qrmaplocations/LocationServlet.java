package com.qrmaplocations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/location")
public class LocationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String locationId = request.getParameter("id");
        if (locationId != null) {
            try (Connection conn = DatabaseUtil.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM locations WHERE id = ?");
                stmt.setString(1, locationId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    request.setAttribute("locationName", rs.getString("name"));
                    request.setAttribute("locationDescription", rs.getString("description"));
                    request.setAttribute("locationImage", rs.getString("image"));
                    request.setAttribute("latitude", rs.getString("latitude"));
                    request.setAttribute("longitude", rs.getString("longitude"));

                    // Forward to location details page
                    request.getRequestDispatcher("/locationDetails.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
