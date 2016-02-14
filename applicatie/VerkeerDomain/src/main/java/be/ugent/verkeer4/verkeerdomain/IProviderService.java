/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Drake7707
 */
public interface IProviderService {

    List<RouteData> getRouteDataForRoute(int routeId, Date from, Date to);

    void poll() throws ClassNotFoundException;
    
}
