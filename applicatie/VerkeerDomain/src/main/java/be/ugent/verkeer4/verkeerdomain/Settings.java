package be.ugent.verkeer4.verkeerdomain;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Settings {

    private static final String DB_DEV_CONFIG = "database-dev.conf";
    private static final String DB_PROD_CONFIG = "database-prod.conf";
    private static final String SETTINGS_CONFIG = "application.conf";

    private static Settings instance;

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public String getTomTomRoutingAPIKey() {
        Config conf = ConfigFactory.load(SETTINGS_CONFIG);
        return conf.getString("APIKeys.TomTom");
    }

    public String getDatabaseConnectionString() {
        return "//localhost:3306/verkeer"; // TIJDELIJK
        /*
        String configName = inProduction() ? DB_PROD_CONFIG : DB_DEV_CONFIG;
        Config conf = ConfigFactory.load(configName);
        return conf.getString("database.connection");*/
    }

    public String getDatabaseUser() {
        return "root"; // TIJDELIJK
        /*
        String configName = inProduction() ? DB_PROD_CONFIG : DB_DEV_CONFIG;
        Config conf = ConfigFactory.load(configName);
        return conf.getString("database.user");*/
        
    }

    public String getDatabasePassword() {
        return ""; // TIJDELIJK
        /*
        String configName = inProduction() ? DB_PROD_CONFIG : DB_DEV_CONFIG;
        Config conf = ConfigFactory.load(configName);
        return conf.getString("database.password");
*/
    }

    public boolean inProduction() {
        Config conf = ConfigFactory.load(SETTINGS_CONFIG);
        return conf.getBoolean("general.inProduction");
    }
}
