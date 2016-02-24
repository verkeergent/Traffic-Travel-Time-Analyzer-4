package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import java.util.Date;
import java.util.List;

public interface IProviderService {

    List<RouteData> getRouteDataForRoute(int routeId, Date from, Date to);

    void poll() throws ClassNotFoundException;
    
}