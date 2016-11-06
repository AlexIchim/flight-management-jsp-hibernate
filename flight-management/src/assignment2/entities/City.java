package assignment2.entities;

import javax.persistence.*;

/**
 * Created by Alex PC on 15/10/2016.
 */

/**
 * @Author: Alex Ichim - DS 2016
 * @Module: SD-Assignment 2
 * @Since: October 10, 2016
 * @Description:
 *  Class describing the cities of the flights.
 */

@Entity
@Table(name = "CITY")
public class City {

    private int id;
    private String name;
    private double latitude;
    private double longitude;

    public City() {
    }

    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "City: " + this.name + " Latitude: " + this.latitude + " Longitude: " + this.longitude;
    }
}
