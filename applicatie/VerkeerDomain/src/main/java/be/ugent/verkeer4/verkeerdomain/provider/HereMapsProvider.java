package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomas Bolckmans
 */
public class HereMapsProvider implements IProvider {

    /**
     * This method calls the HereMaps RestAPI for a specific route and returns the RouteData
     * @param route
     * @return RouteData
     */
    @Override
    public RouteData Poll(Route route) {
        try {
            // haal route gegevens op            
            
            String app_id = "rT5jJhpUtSJEYrkEXFSd";
            String app_code = "MDfihaAj-_kw7eMW2dIb-A";

            String apiCall = "https://route.cit.api.here.com/routing/7.2/calculateroute.json"+
                "?app_id=" + app_id +
                "&app_code=" + app_code +
                "&waypoint0=geo!" + route.getFromLatitude() + "," + route.getFromLongitude() +
                "&waypoint1=geo!" + route.getToLatitude() + "," + route.getToLongitude() +
                "&mode=fastest;car;traffic:enabled";
            
            String jsonResponse = getTrafficData(apiCall);
            
            //parsen van de json code
            //ik heb helaas nog geen librarie van HereMaps gevonden
            
            if(jsonResponse.isEmpty()){
                throw new IOException("No JSON data received");
            }
            
            return null;
        } catch (Exception ex) {
            Logger.getLogger(HereMapsProvider.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        } 
    }

    private String getTrafficData(String apiCall) throws Exception {
        StringBuilder result = new StringBuilder();

        URL url = new URL(apiCall);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
           result.append(line);
        }
        rd.close();
        return result.toString();
    }    
    
}
