package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.Route;
import java.util.List;

public interface IRouteService {

    public List<Route> getRoutes() throws ClassNotFoundException;

    public Route getRoute(int id) throws ClassNotFoundException;

}
