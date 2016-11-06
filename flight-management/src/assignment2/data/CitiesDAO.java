package assignment2.data;


import org.hibernate.*;
import assignment2.entities.City;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

public class CitiesDAO {

    private static final Log LOGGER = LogFactory.getLog(CitiesDAO.class);

    private SessionFactory factory;

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    public CitiesDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public City addCity(City city) {
        int cityId = -1;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx= session.beginTransaction();
            cityId = (Integer) session.save(city);
            city.setId(cityId);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("", e);
        } finally {
            session.close();
        }
        return city;
    }

    public List<City> getCities() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<City> cities = null;
        try {
            tx = session.beginTransaction();
            cities = session.createQuery("FROM City").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("", e);
        } finally {
            session.close();
        }
        return cities;
    }

    public City findCity(int id) {
        Session session = factory.openSession();
        Transaction tx = null;
        List<City> cities = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM City WHERE id = :id");
            query.setParameter("id", id);
            cities = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("", e);
        } finally {
            session.close();
        }
        return cities != null && !cities.isEmpty() ? cities.get(0) : null;
    }



}
