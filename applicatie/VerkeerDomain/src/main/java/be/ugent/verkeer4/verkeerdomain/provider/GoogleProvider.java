/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.Settings;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.provider.google.Element;
import be.ugent.verkeer4.verkeerdomain.provider.google.GoogleMapsClient;
import be.ugent.verkeer4.verkeerdomain.provider.google.Row;
import be.ugent.verkeer4.verkeerdomain.provider.here.HereMapsClient;
import be.ugent.verkeer4.verkeerdomain.provider.here.Response;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElementStatus;
import com.google.maps.model.LatLng;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.ReadWritableDateTime;
import org.joda.time.ReadableInstant;

/**
 *
 * @author Niels
 */
public class GoogleProvider extends BaseProvider implements IProvider {

    String appCode = Settings.getInstance().getGoogleRoutingAPPCode();
    GeoApiContext context = new GeoApiContext().setApiKey(appCode);
    DistanceMatrix matrix;


    public GoogleProvider() {
        super(ProviderEnum.GoogleMaps);
    }
   
    @Override
    public RouteData poll(Route route) {
        //return viaLibrary(route);
        return viaJson(route);      
    }   
    
    private RouteData viaJson(Route route)
    {
        try {
            // haal route gegevens op
            Row row = GoogleMapsClient.GetRoute(route.getFromLatitude(), route.getFromLongitude(), route.getToLatitude(), route.getToLongitude());
          
            if (row != null) {
                Element googleRoute = row.getElements().get(0);

                int delay = googleRoute.getDurationInTraffic().getValue() - googleRoute.getDuration().getValue();
                
                RouteData rd = setRouteData(route,
                        googleRoute.getDistance().getValue(),
                        googleRoute.getDurationInTraffic().getValue(),
                        delay);
                return rd;
            }

            return null;
        } catch (IOException ex) {
            Logger.getLogger(GoogleProvider.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public RouteData viaLibrary(Route route)
    {
        try {
            LatLng origin = new LatLng(route.getFromLatitude(),route.getFromLongitude());
            LatLng destination = new LatLng(route.getToLatitude(),route.getToLongitude());
            
            // haal route gegevens op
            matrix = DistanceMatrixApi.newRequest(context).origins(origin).destinations(destination).departureTime(new DateTime()).await();  
            
            
            
            if (matrix.rows.length > 0) {
             
                if(matrix.rows[0].elements[0].status == DistanceMatrixElementStatus.OK)
                {
                    RouteData rd = setRouteData(route,
                            (int)matrix.rows[0].elements[0].distance.inMeters,
                            (int)matrix.rows[0].elements[0].duration.inSeconds,
                            0);     //Not implemented

                    return rd;
                }
                else
                    return null;
            }

            return null;
        } catch (Exception ex) {
            Logger.getLogger(GoogleProvider.class.getName()).log(Level.SEVERE, null, ex);
            
            return null;
        }    
    }
}
