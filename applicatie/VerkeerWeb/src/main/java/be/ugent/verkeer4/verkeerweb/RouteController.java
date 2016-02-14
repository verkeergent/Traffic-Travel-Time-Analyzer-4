package be.ugent.verkeer4.verkeerweb;

import be.ugent.verkeer4.verkeerdomain.RouteService;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import be.ugent.verkeer4.verkeerdomain.IRouteService;

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
        // voor complexere objecten best view models aanmaken ipv rechtstreeks het domain object aan de view engine door te geven
        Route t = routeService.getRoute(id);

        ModelAndView model = new ModelAndView("route/detail");
        model.addObject("detail", t);

        return model;
    }

}
