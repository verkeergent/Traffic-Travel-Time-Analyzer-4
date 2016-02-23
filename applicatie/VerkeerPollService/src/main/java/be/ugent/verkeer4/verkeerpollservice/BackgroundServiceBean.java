package be.ugent.verkeer4.verkeerpollservice;

import be.ugent.verkeer4.verkeerdomain.IProviderService;
import be.ugent.verkeer4.verkeerdomain.IRouteService;
import be.ugent.verkeer4.verkeerdomain.ProviderService;
import be.ugent.verkeer4.verkeerdomain.RouteService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.Schedule;

@Stateless
public class BackgroundServiceBean {

    IRouteService routeService;
    IProviderService providerService;

    public BackgroundServiceBean() {
        try {
            this.providerService = new ProviderService(routeService);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BackgroundServiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.routeService = new RouteService();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BackgroundServiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Schedule(minute = "*/5", hour = "*")
    public void poll() {

        try {
            this.providerService.poll();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BackgroundServiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
