/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Niels
 */
public class GoogleProvider implements IProvider {

    GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDSFudE3RU-uIFK4ID8i32bnq-rGRRtpGw");
    DistanceMatrix matrix;
    
    @Override
    public RouteData Poll(Route route) {

        try {
            LatLng origin = new LatLng(route.getFromLatitude(),route.getFromLongitude());
            LatLng destination = new LatLng(route.getToLatitude(),route.getToLongitude());
            // haal route gegevens op
            matrix = DistanceMatrixApi.newRequest(context).origins(origin).destinations(destination).await();  
            
            if (matrix.rows.length > 0) {

                RouteData rd = new RouteData();
                rd.setProvider(ProviderEnum.GoogleMaps);
                rd.setTimestamp(new Date());
                rd.setTravelTime((int)matrix.rows[0].elements[0].duration.inSeconds);
                rd.setRouteId(route.getId());

                return rd;
            }

            return null;
        } catch (Exception ex) {
            Logger.getLogger(GoogleProvider.class.getName()).log(Level.SEVERE, null, ex);
            
            return null;
        }
    }  
}
