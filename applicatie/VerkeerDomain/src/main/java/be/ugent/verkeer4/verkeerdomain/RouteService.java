package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.Route;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
