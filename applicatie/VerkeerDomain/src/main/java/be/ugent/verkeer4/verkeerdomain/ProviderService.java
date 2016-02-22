package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.provider.BeMobileProvider;
import be.ugent.verkeer4.verkeerdomain.provider.CoyoteProvider;
import be.ugent.verkeer4.verkeerdomain.provider.HereMapsProvider;
import be.ugent.verkeer4.verkeerdomain.provider.IProvider;
import be.ugent.verkeer4.verkeerdomain.provider.ISummaryProvider;
import be.ugent.verkeer4.verkeerdomain.provider.TomTomProvider;
import be.ugent.verkeer4.verkeerdomain.provider.WazeProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProviderService extends BaseService implements IProviderService {

    private final List<IProvider> perRouteProviders;
    private final List<ISummaryProvider> summaryProviders;
    private final IRouteService routeService;

    public ProviderService(IRouteService routeService) throws ClassNotFoundException {
        super();
        this.routeService = routeService;

        this.perRouteProviders = new ArrayList<>();
        perRouteProviders.add(new TomTomProvider());
        perRouteProviders.add(new BeMobileProvider());
        perRouteProviders.add(new HereMapsProvider());
        perRouteProviders.add(new WazeProvider());

        this.summaryProviders = new ArrayList<>();
        summaryProviders.add(new CoyoteProvider());
    }

    @Override
    public void poll() throws ClassNotFoundException {
        for (Route route : routeService.getRoutes()) {
            for (IProvider provider : perRouteProviders) {
                RouteData data = provider.poll(route);
                if (data != null) {
                    repo.getRouteDataSet().insert(data);
                }
            }
        }

        for (ISummaryProvider provider : summaryProviders) {
            List<RouteData> lst = provider.poll();
            if (lst != null) {
                for (RouteData rd : lst) {
                    repo.getRouteDataSet().insert(rd);
                }
            }
        }
    }

    @Override
    public List<RouteData> getRouteDataForRoute(int routeId, Date from, Date to) {
        return repo.getRouteDataSet().getItemsForRoute(routeId, from, to);
    }
}
