package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HereMapsProvider extends BaseProvider implements IProvider {

    public HereMapsProvider() {
        super(ProviderEnum.HereMaps);
    }
    
    @Override
    public RouteData poll(Route route) {

        try {
            return scrape(route, "here.pl");
        } catch (Exception ex) {
            Logger.getLogger(HereMapsProvider.class.getName()).log(Level.SEVERE, null, ex);

            return useAPI(route);
        }

    }

    private RouteData useAPI(Route route) {
        //TODO call api
        return null;
    }
   
}
