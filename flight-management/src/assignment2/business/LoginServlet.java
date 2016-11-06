package assignment2.business;

import assignment2.data.*;
import assignment2.entities.User;
import assignment2.enums.Role;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Alex PC on 18/10/2016.
 */

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        out.println("HELLO WORLD" + req.getContextPath());
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
       processRequest(req, res);
    }

    public void processRequest (HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String userName = req.getParameter("username");
        String userPassword = req.getParameter("password");

        HttpSession session = req.getSession(false);
       
        UsersDAO usersDAO = new UsersDAO(new Configuration().configure().buildSessionFactory());
        User user = usersDAO.getUser(userName);

        if (user == null) {
            session.setAttribute("login_message", "Invalid user credentials");
            res.sendRedirect("index.jsp");
        } else if (!userPassword.equals(user.getPassword())) {
            session.setAttribute("login_message", "Invalid password! Please try again!");
            res.sendRedirect("index.jsp");
        } else {

            session.setAttribute("user", user);
            if (user.getRole().equals(Role.ADMIN)) {
              
            	getFlightsAndCitiesResources(session);
                res.sendRedirect("admin.jsp");
            } else {
            	getFlightsAndCitiesResources(session);
                res.sendRedirect("client.jsp");
            }
        }
    }
    
    
    public void getFlightsAndCitiesResources( HttpSession session) {
    	  CitiesDAO citiesDAO = new CitiesDAO(new Configuration().configure().buildSessionFactory());
          List cities = citiesDAO.getCities();
          session.setAttribute("cities", cities);

          FlightsDAO flightsDAO = new FlightsDAO(new Configuration().configure().buildSessionFactory());
          List flights = flightsDAO.getFlights();
          session.setAttribute("flights", flights);
    }
}
