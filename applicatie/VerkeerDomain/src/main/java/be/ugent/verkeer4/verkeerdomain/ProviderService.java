package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.provider.IProvider;
import be.ugent.verkeer4.verkeerdomain.provider.TomTomProvider;
import java.util.List;


public class ProviderService {
    
    private List<IProvider> providers;
    
    private IRouteService routeService;
    public ProviderService(IRouteService trajectService) {
        this.routeService = trajectService;
        
        providers.add(new TomTomProvider());
    }
    
    public void Poll() throws ClassNotFoundException {
        
        for (Route route : routeService.getRoutes()) {
            
        }
    }
}
