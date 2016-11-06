<%--
  Created by IntelliJ IDEA.
  User: Alex PC
  Date: 27/10/2016
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@page import="assignment2.business.TimeService"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="assignment2.entities.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
<%@ page import="assignment2.enums.*" %>


<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        System.out.println("User is null");
        response.sendRedirect("index.jsp");
        return;
    } else if (user.getRole().equals(Role.ADMIN)) {
        response.sendRedirect("admin.jsp");
        return;
    }

    List<Flight> allFlights = (List) session.getAttribute("flights");
    List<City> allCities = (List) session.getAttribute("cities");
%>
<% %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script>
	function myFunction( param1, param2 ) {
		alert("Local time is: " + param1 + " in " + param2);
	}
	</script>
	<link rel="stylesheet" type="text/css" href="styleClient.css">
    <title>User</title>
</head>
<body>
    <h1>Welcome <%= user.getUsername() %></h1>
    <table>
        <tr>
            <th>Flight number</th>
            <th>Airplane</th>
            <th>Departure citty</th>
            <th>Departure date</th>
            <th>Departure time</th>
            <th>Arrival city</th>
            <th>Arrival date</th>
            <th>Arrival time</th>
        </tr>
        
        <% for (Flight flight : allFlights) { %>
			<tr>
				<td><%=flight.getFlight_number() %></td>
				<td><%=flight.getAirplane_type() %></td>
				<td><%=allCities.get(flight.getDeparture_city().getId() - 1).getName() %>
					</br>
					<button onClick="myFunction('<%= TimeService.getCurrentLocalTime(String.valueOf(allCities.get(flight.getDeparture_city().getId() - 1).getLatitude()), String.valueOf(allCities.get(flight.getDeparture_city().getId() - 1).getLongitude())) %>', '<%=allCities.get(flight.getDeparture_city().getId() - 1).getName() %>'); return false;" >
					Check Local Time
					</button>
				</td>
				<td><%=flight.getDeparture_date() %></td>
				<td><%=flight.getDeparture_time() %></td>
				<td><%=allCities.get(flight.getArrival_city().getId() - 1).getName() %>
					</br>
					<button onClick="myFunction('<%= TimeService.getCurrentLocalTime(String.valueOf(allCities.get(flight.getArrival_city().getId() - 1).getLatitude()), String.valueOf(allCities.get(flight.getArrival_city().getId() - 1).getLongitude())) %>', '<%=allCities.get(flight.getArrival_city().getId() - 1).getName() %>'); return false;" >
					Check Local Time
					</button>
				</td>
				<td><%=flight.getArrival_date() %></td>
				<td><%=flight.getArrival_time() %></td>
			</tr>
		<% } %>
       
    </table>
    </br>
    <a href="index.jsp" class="button" style="vertical-align:middle"><span>LOGOUT</span></a>
</body>
</html>
