<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.qrmaplocations.DatabaseUtil" %>
<%@ page import="com.qrmaplocations.Location" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>

<html>
<head>
    <title>Location Details</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD6hsVAG8AToj5vhk2eEAx_enRCGiQX42k&callback=initMap">
    </script>
    <script src="location.js"></script>
</head>
<body>

<%
    String locationId = request.getParameter("id");
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DatabaseUtil.getConnection();
        stmt = conn.prepareStatement("SELECT * FROM locations WHERE id = ?");
        stmt.setString(1, locationId);
        rs = stmt.executeQuery();

        if (rs.next()) {
            String name = rs.getString("name");
            String description = rs.getString("description");
            double latitude = rs.getDouble("latitude");
            double longitude = rs.getDouble("longitude");
%>
            <h1><%= name %></h1>
            <p><%= description %></p>
            <div id="map"></div>
            <script>
                initMap(<%= latitude %>, <%= longitude %>);
            </script>
<%
        } else {
            out.println("Location not found.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
%>

</body>
</html>
