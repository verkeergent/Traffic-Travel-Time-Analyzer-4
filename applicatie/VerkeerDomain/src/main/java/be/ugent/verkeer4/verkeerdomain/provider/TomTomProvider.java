package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.Settings;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.CalculateRouteResponse;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.TomTomClient;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TomTomProvider implements IProvider {

    @Override
    public RouteData poll(Route route) {


        try {
            // haal route gegevens op
            CalculateRouteResponse response = TomTomClient.GetRoute(route.getFromLatitude(), route.getFromLongitude(), route.getToLatitude(), route.getToLongitude(), true);

            if (response.getRoutes().size() > 0) {
                be.ugent.verkeer4.verkeerdomain.provider.tomtom.Route tomtomRoute = response.getRoutes().get(0);

                RouteData rd = setRouteData(route, tomtomRoute.getSummary().getTravelTimeInSeconds());

                return rd;
            }

            return null;
        } catch (IOException ex) {
            Logger.getLogger(TomTomProvider.class.getName()).log(Level.SEVERE, null, ex);

            // something went wrong, bvb over rate limit of zo
            // fallback naar scrape
             scrape(route);
             
            return null;
        }
    }

    private RouteData setRouteData(Route route, int travelTimeSeconds) {
        RouteData rd = new RouteData();
        rd.setProvider(ProviderEnum.TomTom);
        rd.setTimestamp(new Date());
        rd.setTravelTime(travelTimeSeconds);
        rd.setRouteId(route.getId());
        return rd;
    }

    public RouteData scrape(Route route) {

        File baseDir = new File(Settings.getInstance().getScrapePath());

        File script = new File(baseDir, "tomtom.pl");

        try {
            ProcessBuilder pb = new ProcessBuilder("perl", script.toPath().toString(), route.getFromLatitude() + "", route.getFromLongitude() + "", 
                    route.getToLatitude() + "", route.getToLongitude()+"");
            
            pb.directory(baseDir);
            java.lang.Process p = pb.start();
            int errCode = p.waitFor();

            if (errCode == 0) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

                String headers = reader.readLine();
                String results = reader.readLine();

                // totalDistanceMeters;totalTimeSeconds;totalDelaySeconds
                String[] parts = results.split(";");

                int totalDistance = Integer.parseInt(parts[0]);
                int totalTimeSeconds = Integer.parseInt(parts[1]);
                int totalDelaySeconds = Integer.parseInt(parts[2]);

                setRouteData(route, totalTimeSeconds);
            }

        } catch (IOException ex) {
            Logger.getLogger(TomTomProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TomTomProvider.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
