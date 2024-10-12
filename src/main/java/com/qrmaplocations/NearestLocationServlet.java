package com.qrmaplocations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/nearest-locations")
public class NearestLocationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String lat = request.getParameter("lat");
        String lon = request.getParameter("lon");

        if (lat != null && lon != null) {
            try {
                double latitude = Double.parseDouble(lat);
                double longitude = Double.parseDouble(lon);

                List<Location> nearestLocations = getNearestLocations(latitude, longitude);

                // Set the nearest locations as a request attribute
                request.setAttribute("nearestLocations", nearestLocations);
                request.getRequestDispatcher("/nearestLocations.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Handle invalid latitude/longitude input
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid latitude or longitude.");
            } catch (Exception e) {
                e.printStackTrace(); // Log any other exceptions
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving locations.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Latitude and longitude are required.");
        }
    }

    private List<Location> getNearestLocations(double latitude, double longitude) throws Exception {
        List<Location> locations = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT name, description, latitude, longitude FROM locations");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                double locLat = rs.getDouble("latitude");
                double locLon = rs.getDouble("longitude");
                double distance = LocationUtil.calculateDistance(latitude, longitude, locLat, locLon);

                locations.add(new Location(name, description, distance));
            }
        }

        // Sort locations by distance and limit to 5
        Collections.sort(locations, Comparator.comparingDouble(Location::getDistance));
        return locations.size() > 5 ? locations.subList(0, 5) : locations;
    }
}
