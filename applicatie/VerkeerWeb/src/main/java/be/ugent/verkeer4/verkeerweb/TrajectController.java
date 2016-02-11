package be.ugent.verkeer4.verkeerweb;

import be.ugent.verkeer4.verkeerdomain.ITrajectService;
import be.ugent.verkeer4.verkeerdomain.TrajectService;
import be.ugent.verkeer4.verkeerdomain.data.Traject;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TrajectController {

    @RequestMapping(value = "traject/list", method = RequestMethod.GET)
    public ModelAndView getdata() throws ClassNotFoundException {

        ITrajectService trajectService = new TrajectService(); // eventueel dependency injection

        List<Traject> lst = trajectService.getTrajecten();

        ModelAndView model = new ModelAndView("traject/list");
        model.addObject("trajecten", lst);

        return model;
    }

    @RequestMapping(value = "/traject/detail", method = RequestMethod.GET)
    public ModelAndView getDetail(int id) throws ClassNotFoundException {

        
        ITrajectService trajectService = new TrajectService(); // eventueel dependency injection

        // voor complexere objecten best view models aanmaken ipv rechtstreeks het domain object aan de view engine door te geven
        Traject t = trajectService.getTraject(id);

        ModelAndView model = new ModelAndView("traject/detail");
        model.addObject("detail", t);

        return model;
    }

}
