package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.provider.IProvider;
import be.ugent.verkeer4.verkeerdomain.provider.TomTomProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ProviderService extends BaseService implements IProviderService {
    
    private final List<IProvider> providers;
    private final IRouteService routeService;
    public ProviderService(IRouteService routeService) throws ClassNotFoundException {
        super();
        this.routeService = routeService;
        
        this.providers = new ArrayList<>();
        providers.add(new TomTomProvider());
    }
    
    @Override
    public void poll() throws ClassNotFoundException {
        for (Route route : routeService.getRoutes()) {    
            for (IProvider provider : providers) {
                RouteData data = provider.Poll(route);
                repo.getRouteDataSet().insert(data);
            }
        }
    }
    
    @Override
    public List<RouteData> getRouteDataForRoute(int routeId, Date from, Date to) {
        return repo.getRouteDataSet().getItemsForRoute(routeId, from, to);
    }
}
