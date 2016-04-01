/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerweb.viewmodels;

import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Tomas Bolckmans
 */
public class LogEntryVM {
    private int id;
    private String type;
    private String date;
    private String time;
    private String category;
    private String message;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the string
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the timestamp to set
     */
    public void setDate(String date) {
        this.date = date;
    }
    
     /**
     * @param time the string to set
     */
    public void setTime(String time){
        this.time = time;
    }
    
    /**
     * @return the string time
     */
    public String getTime(){
        return time;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Opgelet:
     * Hier moet een substring tot 255 geïmplementeerd worden aangezien we een varchar(255) gebruiken
     * @param message the message to set
     */
    public void setMessage(String message) {
        if(message.length() > 254){
            this.message = message.substring(0,254);
        } else{
            this.message = message;
        }
    }
}
