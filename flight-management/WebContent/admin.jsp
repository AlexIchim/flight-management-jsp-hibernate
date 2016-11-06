<%--
  Created by IntelliJ IDEA.
  User: Alex PC
  Date: 18/10/2016
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@page import="assignment2.enums.Role"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="assignment2.entities.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<% User user = (User) session.getAttribute("user");

	if (user == null) {
		System.out.println("Null user");
		response.sendRedirect("index.jsp");
		return;
	}

	List<Flight> allFlights = (List) session.getAttribute("flights");
	List<City> allCities = (List) session.getAttribute("cities");
	
	String formMessage = (String) session.getAttribute("form_message");
	
	session.removeAttribute("form_message");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="style.css">
	<style>
		input:read-only { 
		    background-color: gray;
		}
		a.button {
			float: right;
		}
	</style>
<title>Admin page</title>
</head>
<body>
	<p>
		<strong>
			<%
				if (formMessage != null)
					out.println(formMessage);
			%>
		</strong>
	</p>
	<% if (user.getRole().equals(Role.ADMIN)) { %>
    	<a href="index.jsp" class="button" style="vertical-align:middle"><span>LOGOUT</span></a>
		<h1>Welcome, <%= user.getUsername() %> !</h1>
		<h2>Insert New Flight:</h2>
		
		
		<form id="addFlight" method="post" action="AdminServlet">
			<input type="hidden" name="request_type" value="insert"/>
			<table>
				<tr>
					<td colspan="2">
						<label for="airplane_type">Airplane Type</label>
						<input type="text" name="airplane_type" required>
					</td>
				</tr>
				<tr>
					<td>
						<label for="departure_city">Departure City</label>
						<select name="departure_city" id="departure_city" required>
							<% for (City city : allCities) { %>
								<option value="<%= city.getId() %>"><%=city.getName() %></option>
							<% } %>
						</select>
					</td>
					
					<td>
						<label for="arrival_city">Arrival City</label>
						<select name="arrival_city" id="arrival_city" required>
							<% for (City city : allCities) { %>
								<option value="<%= city.getId() %>"><%=city.getName() %></option>
							<% } %>
						</select>
						</br>
					</td>
				</tr>
				<tr>
					<td>
						<label for="departure_date">Departure Date</label>
						<input type="date" name="departure_date" required/>
					</td>
					<td>
						<label for="arrival_date">Arrival Date</label>
						<input type="date" name="arrival_date" required/>
					</td>
				</tr>
				
				
				<tr>
					<td>
						<label for="departure_time">Departure Time</label>
						<input type="time" name="departure_time" required/>
					</td>
					<td>
						<label for="arrival_time">Arrival Time</label>
						<input type="time" name="arrival_time" required/>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="submit" value="Insert" class="button">
						<input type="reset"  value="Clear" class="button">
					</td>
				</tr>
					
			</table>
			
		</form>		
		<h2>Update/Delete Flights:</h2>
		<table border="1" id="flights">
        <tr>
            <th>Flight number</th>
			<th>Airplane type</th>
			<th>Departure city</th>
			<th>Departure date</th>
			<th>Departure time</th>
			<th>Arrival city</th>
			<th>Arrival date</th>
			<th>Arrival time</th>
        </tr>
        
        <% for (Flight flight : allFlights) { %>
			<tr class="flightRow" id="flight_<%= flight.getFlight_number() %>">
				<form id="update_flight_<%=flight.getFlight_number() %>" method="post" action="AdminServlet">
					<input type="hidden" name="request_type" value="update"/>
					<td>
						<input type="text" name="flight_number" value="<%=flight.getFlight_number()%>" readonly />
					</td>
					<td>
						<input type="text" name="airplane_type1" class="update" value="<%=flight.getAirplane_type() %>"  />
					</td>
					<td>
						<select name="departure_city1" id="departure_city1" required>
							<% for (City city : allCities) { 
								 if (city.getId() == flight.getDeparture_city().getId()) { %>	
									<option value="<%= city.getId() %>" selected><%=city.getName() %></option>
								<% } else { %>
									<option value="<%= city.getId() %>"><%=city.getName() %></option>
								<% } %>		
							<% } %>
						</select>
					
					</td>
					<td>
						<input type="date" name="departure_date1" class="update" value="<%= flight.getDeparture_date() %>"/>
					</td>
					<td>
						<input type="time" name="departure_time1" class="update" value="<%= flight.getDeparture_time() %>"/>					
					</td>
					<td>
						<select name="arrival_city1" id="arrival_city1" required>
							<% for (City city : allCities) { 
								 if (city.getId() == flight.getArrival_city().getId()) { %>	
									<option value="<%= city.getId() %>" selected><%=city.getName() %></option>
								<% } else { %>
									<option value="<%= city.getId() %>"><%=city.getName() %></option>
								<% } %>		
							<% } %>
						</select>					</td>
					<td>
						<input type="date" name="arrival_date1" class="update" value="<%= flight.getArrival_date() %>"/>
					</td>
					<td>	
						<input type="time" name="arrival_time1" class="update" value="<%= flight.getArrival_time() %>"/>					
					</td>
					</tr>
					<tr>
					<td>
						<input class="update_btn" type="submit" value="Update"/>
					</td>
					</form>
					<td>
						<form id="deleteFlight" method="post" action="AdminServlet">
							<input type="hidden" name="request_type" value="delete" />
							<input type="hidden" name="del_flight_number" id="delFlightNumber" value="<%=flight.getFlight_number() %>" />
							<input type="submit" value="Delete" />
						</form>
					</td>
					</tr>			
		<% } %>
       </table>
		<% } else { %>
			<p>You do not have enough rights to access this page!</p>
			</br>
			<a href="index.jsp">Back</a>	
		<% } %>
</body>
</html>