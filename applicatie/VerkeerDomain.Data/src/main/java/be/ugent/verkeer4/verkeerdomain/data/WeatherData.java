/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.data;
import java.util.Date;

/**
 *
 * @author Niels
 */
public class WeatherData {
    //tijd, temp, type weer, windkracht, windrichting...
        private int Id;
        private Date timestamp;
        private Date updateTime;
        private double longitude;
        private double latitude;
        private double temperature;
        private double windSpeed;
        private int windDirection;
        private int weatherCondition;
        private String location;

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the temperature
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * @param temperature the temperature to set
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * @return the windSpeed
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * @param windSpeed the windSpeed to set
     */
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * @return the windDirection
     */
    public int getWindDirection() {
        return windDirection;
    }

    /**
     * @param windDirection the windDirection to set
     */
    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    /**
     * @return the condition
     */
    public int getWeatherCondition() {
        return weatherCondition;
    }

    /**
     * @param weatherCondition the condition to set
     */
    public void setWeatherCondition(int weatherCondition) {
        this.weatherCondition = weatherCondition;
    }   

    /**
     * @return the Id
     */
    public int getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(int Id) {
        this.Id = Id;
    }

    /**
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
