/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import be.ugent.verkeer4.verkeerdomain.IPOIService;
import be.ugent.verkeer4.verkeerdomain.IProviderService;
import be.ugent.verkeer4.verkeerdomain.IRouteService;
import be.ugent.verkeer4.verkeerdomain.POIService;
import be.ugent.verkeer4.verkeerdomain.ProviderService;
import be.ugent.verkeer4.verkeerdomain.RouteService;
import be.ugent.verkeer4.verkeerdomain.data.composite.BoundingBox;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class PollTest {
    
    public PollTest() {
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

//  
//     @Test
//    public void testPoll() throws ClassNotFoundException {
//         IRouteService routeService = new RouteService();
//         IProviderService providerService = new ProviderService(routeService);
//         
//         providerService.poll();
//    }
    
      
     @Test
    public void testPollPOI() throws ClassNotFoundException {
         IRouteService routeService = new RouteService();
         IPOIService poiService = new POIService(routeService);
         IProviderService providerService = new ProviderService(routeService, poiService);
         
         BoundingBox bbox = routeService.getBoundingBoxOfAllRoutes();
         providerService.pollPOI(bbox);
    }
}
