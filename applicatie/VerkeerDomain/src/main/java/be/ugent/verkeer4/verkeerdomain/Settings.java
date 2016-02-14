package be.ugent.verkeer4.verkeerdomain;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;


// TODO wat propere settings voorzien, eens onderzoeken hoe dit in java best moet
public class Settings {
    
    private static Settings instance;
    public static Settings getInstance() {
        if(instance == null)
            instance = new Settings();
        return instance;
    }
    
    public String getTomTomRoutingAPIKey() {
        return "5j7n539vbsbf6frb7kwzxtc6";
    }
    
    
    public  String getDatabaseConnectionString() {
        return "//localhost:3306/verkeer";
    }
    
    public String getDatabaseUser() {
        Config conf = ConfigFactory.load();
        String user = conf.getString("database.user");
        return user;
    }
    
    public String getDatabasePassword() {
        Config conf = ConfigFactory.load();
        String pw = conf.getString("database.password");
        return pw;
    }
}
