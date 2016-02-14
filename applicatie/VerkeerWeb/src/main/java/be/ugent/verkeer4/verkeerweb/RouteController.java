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
import be.ugent.verkeer4.verkeerweb.viewmodels.RouteDetails;
import java.util.Date;

@Controller
public class RouteController {

    @RequestMapping(value = "route/list", method = RequestMethod.GET)
    public ModelAndView getdata() throws ClassNotFoundException {

        IRouteService routeService = new RouteService(); // eventueel dependency injection
        List<Route> lst = routeService.getRoutes();

        ModelAndView model = new ModelAndView("route/list");
        model.addObject("routes", lst);

        return model;
    }

    @RequestMapping(value = "/route/detail", method = RequestMethod.GET)
    public ModelAndView getDetail(int id) throws ClassNotFoundException {

        IRouteService routeService = new RouteService();
        IProviderService providerService = new ProviderService(routeService);

        Route route = routeService.getRoute(id);

        // TODO valid range
        Date d =  new Date();
        d.setTime(new Date().getTime() - (24*60*60*1000));
        
        List<RouteData> data = providerService.getRouteDataForRoute(id,d, new Date());
        RouteDetails detail = new RouteDetails(route, data);
        ModelAndView model = new ModelAndView("route/detail");
        model.addObject("detail", detail);

        return model;
    }

}
