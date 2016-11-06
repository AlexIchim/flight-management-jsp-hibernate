package assignment2.data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import assignment2.entities.Flight;
import org.hibernate.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
/**
 * Created by Alex PC on 15/10/2016.
 */

/**
 * @Author: Alex Ichim - DS 2016
 * @Module: SD-Assignment 2
 * @Since: October 10, 2016
 * @Description:
 * Uses Hibernate for CRUD operations on the underlying database.
 * The Hibernate configuration files can be found in the src/main/resources folder
 */

public class FlightsDAO {

    private static final Log LOGGER = LogFactory.getLog(Flight.class);

    private SessionFactory factory;

    public FlightsDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public List<Flight> getFlights () {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Flight> flights = null;

        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Flight");
            flights = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("",e);
        } finally {
            session.close();
        }
        return flights;
    }

    public Flight addFlight(Flight flight) {
        int flightId = -1;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            flightId = (Integer) session.save(flight);
            flight.setFlight_number(flightId);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("", e);
        } finally {
            session.close();
        }
        return flight;
    }

    public void updateFlight(Flight flight) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(flight);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public Flight deleteFlight(int id) {
        Session session = factory.openSession();
        Transaction tx = null;
        Flight delFlight = null;

        try {
            tx = session.beginTransaction();
            delFlight = session.get(Flight.class, id);
            session.createQuery("DELETE FROM Flight Where flight_number= :id").setParameter("id", id).executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("", e);
        } finally {
            session.close();
        }
        return delFlight;
    }
}
