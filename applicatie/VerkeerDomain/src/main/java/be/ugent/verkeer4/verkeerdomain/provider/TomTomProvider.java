package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.provider.tomtom.CalculateRouteResponse;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.TomTomClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TomTomProvider implements IProvider {

    @Override
    public boolean Poll() {

        try {
            // voor alle trajecten

            // haal route gegevens op
            CalculateRouteResponse response = TomTomClient.GetRoute(51.038663, 3.725996, 51.056146, 3.695183);

            // save naar database
            return true;
        } catch (IOException ex) {
            Logger.getLogger(TomTomProvider.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
    }
}
