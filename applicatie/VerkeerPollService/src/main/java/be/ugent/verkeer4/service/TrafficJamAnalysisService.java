package be.ugent.verkeer4.service;

import be.ugent.verkeer4.verkeerdomain.IRouteService;
import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.RouteService;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TrafficJamAnalysisService extends BaseService {

    public TrafficJamAnalysisService() {
        super(60 * 60 * 24, "Traffic Jam Analysis");
    }

    

    @Override
    protected void action() {

        // today    
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date today = cal.getTime();

        try {
            IRouteService routeService = new RouteService();
            List<Route> routes = routeService.getRoutes();

            for (Route route : routes) {
                routeService.finalizeTrafficJams(route, today);
            }
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, TrafficJamAnalysisService.class.getName(), ex.getMessage());
        }
    }

}
