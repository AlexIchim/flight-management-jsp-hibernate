package assignment2.business;

import java.io.IOException;
	import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.cfg.Configuration;

import assignment2.data.CitiesDAO;
import assignment2.data.FlightsDAO;
import assignment2.entities.City;
import assignment2.entities.Flight;

/**
 * Created by Alex PC on 18/10/2016.
 */

@WebServlet(name = "AdminServlet", urlPatterns={"/AdminServlet"})
public class AdminServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void processRequest(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		/* Getting method type, delete/update/insert */
		String formType = req.getParameter("request_type");
		
        CitiesDAO citiesDAO = new CitiesDAO(new Configuration().configure().buildSessionFactory());

		
		if (formType.equals("insert")) {
			
			
			Flight  createdFlight = createFLightFromInputData(citiesDAO,
								req.getParameter("airplane_type"),
								req.getParameter("departure_city"),
								req.getParameter("departure_date"),
								req.getParameter("departure_time"),
								req.getParameter("arrival_city"),
								req.getParameter("arrival_date"),
								req.getParameter("arrival_time")
					);
			
			
			
			HttpSession session = req.getSession(false);
			
			FlightsDAO flightsDAO = new FlightsDAO(new Configuration().configure().buildSessionFactory());
			createdFlight = flightsDAO.addFlight(createdFlight);
			
			if (createdFlight.getFlight_number() <  0) {
				session.setAttribute("form_message", "Error when inserting flight!");
				res.sendRedirect("admin.jsp");
			} else {
				session.setAttribute("form_message", "Successfully inserted flight " + createdFlight.getFlight_number() + "!");
				
				List allFlights = flightsDAO.getFlights();
				session.setAttribute("flights", allFlights);
				
				res.sendRedirect("admin.jsp");
			}
		} else if (formType.equals("delete")) {
			String flightToDelete = req.getParameter("del_flight_number");
			HttpSession session = req.getSession(false);
			
			FlightsDAO flightsDAO = new FlightsDAO(new Configuration().configure().buildSessionFactory());
			flightsDAO.deleteFlight(Integer.parseInt(flightToDelete));
			
			List allFlights = flightsDAO.getFlights();
			session.setAttribute("flights", allFlights);
			
			session.setAttribute("form_message", "Successfully deleted flight " + flightToDelete + " !");
			res.sendRedirect("admin.jsp");
		} else if (formType.equals("update"))  {
			
		
			Flight  createdFlight = createFLightFromInputData(citiesDAO,
					req.getParameter("airplane_type1"),
					req.getParameter("departure_city1"),
					req.getParameter("departure_date1"),
					req.getParameter("departure_time1"),
					req.getParameter("arrival_city1"),
					req.getParameter("arrival_date1"),
					req.getParameter("arrival_time1")
					);
			
			String flightToUpdate = req.getParameter("flight_number");
			createdFlight.setFlight_number(Integer.parseInt(flightToUpdate));
			
			HttpSession session = req.getSession(false);
			
			FlightsDAO flightsDAO = new FlightsDAO(new Configuration().configure().buildSessionFactory());
			flightsDAO.updateFlight(createdFlight);
			
			List allFlights = flightsDAO.getFlights();
			session.setAttribute("flights", allFlights);
			
			session.setAttribute("form_message", "Succesfully updated flight with no " + flightToUpdate + " !");
			res.sendRedirect("admin.jsp");
		}
	}
	
	public Flight createFLightFromInputData (CitiesDAO dao, String airplaneType, String departureCityID, String departureDate, String departureTime,
												String arrivalCityID, String arrivalDate, String arrivalTime) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date depDate = null;
		Time depTime = null;
		Date arrivDate = null;
		Time arrivTime = null;
		
		String tokenizedTime[];

		
		try {
			depDate = dateFormat.parse(departureDate);				
			arrivDate = dateFormat.parse(arrivalDate);
			
			tokenizedTime = departureTime.split(":");
			depTime = new Time(Integer.parseInt(tokenizedTime[0]), Integer.parseInt(tokenizedTime[1]), 0);
			
			tokenizedTime = arrivalTime.split(":");
			arrivTime = new Time(Integer.parseInt(tokenizedTime[0]), Integer.parseInt(tokenizedTime[1]), 0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		City departureCity = dao.findCity(Integer.valueOf(departureCityID));
		City arrivalCity = dao.findCity(Integer.valueOf(arrivalCityID));

		Flight createdFlight = null;
		if (departureCity != null && arrivalCity != null) {
			 createdFlight = new Flight(airplaneType, departureCity, depDate, depTime, arrivalCity, arrivDate, arrivTime );
		} else {
			System.out.println("INVALID CITY ID");
		}
		
		return createdFlight;
	}
	
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		processRequest(req, res);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws	ServletException, IOException {
		processRequest(req, res);
	}
}

