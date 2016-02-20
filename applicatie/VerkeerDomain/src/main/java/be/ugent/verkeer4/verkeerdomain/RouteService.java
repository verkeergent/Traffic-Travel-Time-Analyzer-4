package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteWaypoint;
import be.ugent.verkeer4.verkeerdomain.data.composite.RouteSummary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RouteService extends BaseService implements IRouteService {

    public RouteService() throws ClassNotFoundException {
        super();
    }
    
    @Override
    public List<Route> getRoutes() throws ClassNotFoundException {
        
        return repo.getRouteSet().getItems();
    }

    @Override
    public Route getRoute(int id) throws ClassNotFoundException {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Id", id);
        return repo.getRouteSet().getItem("Id = :Id", parameters);
    }

    @Override
    public void insertRouteWaypoint(RouteWaypoint wp) {
        repo.getRouteWaypointSet().insert(wp);
    }

    @Override
    public void updateRoute(Route r) {
        try {
            repo.getRouteSet().update(r);
        } catch (Exception ex) {
            Logger.getLogger(RouteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<RouteWaypoint> getRouteWaypoints() {
        return repo.getRouteWaypointSet().getItems();
    }
    
    public List<RouteSummary> getMostRecentRouteSummaries() {
        return repo.getRouteDataSet().getMostRecentSummaries();
    }
}
