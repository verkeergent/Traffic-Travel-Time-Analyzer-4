package be.ugent.verkeer4.verkeerdomain.provider.tomtom.test;

import be.ugent.verkeer4.verkeerdomain.provider.tomtom.CalculateRouteResponse;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.Route;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.TomTomClient;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class TomTomClientTest {

    public TomTomClientTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testRoute() {
        CalculateRouteResponse response;
        try {
            response = TomTomClient.GetRoute(51.038663, 3.725996, 51.056146, 3.695183, true);
            
            if(response.getRoutes().size() > 0 ) {
                
                Route r= response.getRoutes().get(0);
                
                int travelTime = r.getSummary().getTravelTimeInSeconds();
                int trafficDelay = r.getSummary().getTrafficDelayInSeconds();
                int length = r.getSummary().getLengthInMeters();
                
                if(length == 0)
                    fail("Route heeft geen lengte");
                 
                if(travelTime == 0)
                    fail("Route heeft geen travel time");
                
                
            }
            else 
                fail("Geen routes gevonden");
            
        } catch (IOException ex) {
            fail("Error: " + ex.getMessage());
        }
        
    }
}
