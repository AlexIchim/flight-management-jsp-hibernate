package assignment2.entities;

/**
 * Created by Alex PC on 15/10/2016.
 */

import assignment2.enums.Role;

/**
 * @Author: Alex Ichim - DS 2016
 * @Module: SD-Assignment 2
 * @Since: October 10, 2016
 * @Description:
 *  Class describing the users of the application.
 */

public class User {

    private String username;

    private String password;

    private Role role;

    public User() {
    }

    public User(String username, String password, Role role) {

        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

