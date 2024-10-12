<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.qrmaplocations.Location" %>

<%
    List<Location> nearestLocations = (List<Location>) request.getAttribute("nearestLocations");
%>

<h2>Nearest Locations</h2>
<ul>
    <%
        if (nearestLocations != null && !nearestLocations.isEmpty()) {
            for (Location location : nearestLocations) {
    %>
                <li>
                    <%= location.getName() %> - <%= location.getDistance() %> km away
                </li>
    <%
            }
        } else {
    %>
            <li>No nearest locations found.</li>
    <%
        }
    %>
</ul>
