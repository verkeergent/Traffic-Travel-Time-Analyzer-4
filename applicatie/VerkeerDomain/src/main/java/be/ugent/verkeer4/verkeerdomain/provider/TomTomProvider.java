package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.CalculateRouteResponse;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.TomTomClient;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TomTomProvider implements IProvider {

    @Override
    public RouteData Poll(Route route) {
        try {
            // haal route gegevens op
            CalculateRouteResponse response = TomTomClient.GetRoute(route.getFromLatitude(), route.getFromLongitude(), route.getToLatitude(), route.getToLongitude(), true);

            if (response.getRoutes().size() > 0) {
                be.ugent.verkeer4.verkeerdomain.provider.tomtom.Route tomtomRoute = response.getRoutes().get(0);

                
                RouteData rd = new RouteData();
                rd.setProvider(ProviderEnum.TomTom);
                rd.setTimestamp(new Date());
                rd.setTravelTime(tomtomRoute.getSummary().getTravelTimeInSeconds());
                rd.setRouteId(route.getId());

                return rd;
            }

            return null;
        } catch (IOException ex) {
            Logger.getLogger(TomTomProvider.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }
    }
}
