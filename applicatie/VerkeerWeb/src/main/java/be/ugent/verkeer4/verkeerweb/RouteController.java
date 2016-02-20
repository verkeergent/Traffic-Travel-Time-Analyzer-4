package be.ugent.verkeer4.verkeerweb;

import be.ugent.verkeer4.verkeerdomain.IProviderService;
import be.ugent.verkeer4.verkeerdomain.RouteService;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import be.ugent.verkeer4.verkeerdomain.IRouteService;
import be.ugent.verkeer4.verkeerdomain.ProviderService;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.data.RouteWaypoint;
import be.ugent.verkeer4.verkeerdomain.data.composite.RouteSummary;
import be.ugent.verkeer4.verkeerweb.dataobjects.MapData;
import be.ugent.verkeer4.verkeerweb.dataobjects.MapRoute;
import be.ugent.verkeer4.verkeerweb.dataobjects.MapWaypoint;
import be.ugent.verkeer4.verkeerweb.viewmodels.RouteDetails;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RouteController {

    @RequestMapping(value = "route/list", method = RequestMethod.GET)
    public ModelAndView getList() throws ClassNotFoundException {

        IRouteService routeService = new RouteService(); // eventueel dependency injection
        List<Route> lst = routeService.getRoutes();

        
        List<RouteSummary> mostRecentRouteSummaries = routeService.getMostRecentRouteSummaries();
        // TODO maak van routes een map van route viewmodels en overloop alle summaries en lookup route in de map om de recente gegevens aan te vullen
        
        
        ModelAndView model = new ModelAndView("route/list");
        model.addObject("routes", lst);

        return model;
    }

    @RequestMapping(value = "route/detail", method = RequestMethod.GET)
    public ModelAndView getDetail(int id) throws ClassNotFoundException {

        IRouteService routeService = new RouteService();
        IProviderService providerService = new ProviderService(routeService);

        Route route = routeService.getRoute(id);

        // TODO valid range
        Calendar calendar = Calendar.getInstance();
        Date end = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date start = calendar.getTime();
        
        List<RouteData> data = providerService.getRouteDataForRoute(id, start,end);
        RouteDetails detail = new RouteDetails(route, data);
        ModelAndView model = new ModelAndView("route/detail");
        model.addObject("detail", detail);

        return model;
    }

    @RequestMapping(value = "route/map", method = RequestMethod.GET)
    public ModelAndView getMap() throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        List<Route> lst = routeService.getRoutes();

        ModelAndView model = new ModelAndView("route/map");
        model.addObject("routes", lst);

        return model;
    }

    @ResponseBody
    @RequestMapping(value = "route/mapdata", method = RequestMethod.GET)
    public MapData ajaxGetMapRoutes() throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        List<Route> routes = routeService.getRoutes();

        List<RouteWaypoint> waypoints = routeService.getRouteWaypoints();

        MapData data = new MapData();

        Map<Integer, MapRoute> mapRoutesPerId = new HashMap<>();
        for (Route r : routes) {
            MapRoute mr = new MapRoute();
            mr.setName(r.getName());
            mr.setDistance(r.getDistance());
            mr.setId(r.getId());

            // TODO bepaal adhv laatste gegevens
            mr.setTrafficDelayPercentage(Math.random());

            mapRoutesPerId.put(r.getId(), mr);
            data.getRoutes().add(mr);
        }

        for (RouteWaypoint waypoint : waypoints) {
            MapWaypoint wp = new MapWaypoint(waypoint.getLatitude(), waypoint.getLongitude());
            mapRoutesPerId.get(waypoint.getRouteId()).getWaypoints().add(wp);
        }

        return data;
    }

}
