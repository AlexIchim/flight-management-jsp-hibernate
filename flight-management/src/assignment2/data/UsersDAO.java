package assignment2.data;

import assignment2.entities.User;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.hibernate.*;

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

public class UsersDAO {

    private static final Log LOGGER = LogFactory.getLog(User.class);

    private SessionFactory factory;

    public UsersDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public User addUser(User user) {
        String userName = "";
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            userName = (String) session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("", e);
        } finally {
            session.close();
        }
        return user;
    }

    public User getUser(String username) {
        Session session = factory.openSession();
        Transaction tx = null;
        List<User> users = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM User WHERE id= :id");
            query.setString("id", username);
            users = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("", e);
        } finally {
            session.close();
        }
        return users != null && !users.isEmpty() ? users.get(0) : null;
    }
}
