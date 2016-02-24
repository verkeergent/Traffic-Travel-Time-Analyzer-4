package be.ugent.verkeer4.verkeerweb;

import be.ugent.verkeer4.verkeerdomain.IRouteService;
import be.ugent.verkeer4.verkeerdomain.RouteService;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() throws ClassNotFoundException {
        ModelAndView model = new ModelAndView("home/index");
        return model;
    }
}