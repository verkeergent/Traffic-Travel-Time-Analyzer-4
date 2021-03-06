package be.ugent.verkeer4.verkeerweb;

import be.ugent.verkeer4.verkeerweb.viewmodels.RouteSummaryEntryVM;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import be.ugent.verkeer4.verkeerdomain.*;
import be.ugent.verkeer4.verkeerdomain.data.*;
import be.ugent.verkeer4.verkeerdomain.data.composite.POIWithDistanceToRoute;
import be.ugent.verkeer4.verkeerdomain.data.composite.GroupedRouteTrafficJamCause;
import be.ugent.verkeer4.verkeerweb.dataobjects.*;
import be.ugent.verkeer4.verkeerweb.viewmodels.RouteDataVM;
import be.ugent.verkeer4.verkeerweb.viewmodels.RouteDetailsVM;
import be.ugent.verkeer4.verkeerweb.viewmodels.RouteEditVM;
import be.ugent.verkeer4.verkeerweb.viewmodels.RouteOverviewVM;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class RouteController {

    /**
     * Toont het overzicht van alle routes
     *
     * @return
     * @throws ClassNotFoundException
     */
    @RequestMapping(value = "route/list", method = RequestMethod.GET)
    public ModelAndView getList() throws ClassNotFoundException {

        IRouteService routeService = new RouteService(); // eventueel dependency injection
        // route overview model opbouwen
        RouteOverviewVM overview = getRouteOverviewModel(routeService);

        // geef mee als model aan view
        ModelAndView model = new ModelAndView("route/list");
        model.addObject("overview", overview);

        return model;
    }

    /**
     * Bouwt een route overview viewmodel op om te gebruiken bij de route list
     *
     * @param routeService
     * @return
     * @throws ClassNotFoundException
     */
    private RouteOverviewVM getRouteOverviewModel(IRouteService routeService) throws ClassNotFoundException {
        // haal routes op
        List<Route> lst = routeService.getRoutes();
        // haal meest recentste gegevens op voor alle routes
        List<RouteData> mostRecentRouteSummaries = routeService.getMostRecentRouteSummaries(null);
        RouteOverviewVM overview = new RouteOverviewVM();
        // hou per route id een RouteSummaryEntry bij
        Map<Integer, RouteSummaryEntryVM> entries = new HashMap<>();
        // overloop alle routes en maak een nieuw routeSummaryEntry en steek het in de entries map
        for (Route r : lst) {
            RouteSummaryEntryVM entry = new RouteSummaryEntryVM();
            entry.setRoute(r);

            overview.getSummaries().add(entry);
            entries.put(r.getId(), entry);
        }
        Date maxDate = mostRecentRouteSummaries.stream().map(rs -> rs.getTimestamp()).max(Date::compareTo).get();
        overview.setRecentRouteDateFrom(maxDate);

        // overloop alle recente route data en steek vul de routesummaryentry object aan
        for (RouteData sum : mostRecentRouteSummaries) {

            Map<ProviderEnum, RouteDataVM> summaryPerProvider = entries.get(sum.getRouteId()).getRecentSummaries();
            summaryPerProvider.put(sum.getProvider(), new RouteDataVM(sum));
        }
        // nu dat alle summary per provider per route entry toegevoegd zijn overlopen
        // we nogmaals alle routes om de gemiddelden te berekenen
        for (Route r : lst) {
            RouteSummaryEntryVM entry = entries.get(r.getId());
            Map<ProviderEnum, RouteDataVM> summaryPerProvider = entry.getRecentSummaries();

            List<RouteDataVM> items = summaryPerProvider.values().stream().filter(rvm -> rvm.getDelay() >= 0).collect(Collectors.toList());
            if (items.size() > 0) {
                // bereken gemiddelde van delay
                int totalDelay = items.stream().mapToInt(RouteDataVM::getDelay).sum();
                double avgDelay = totalDelay / (float) items.size();
                entry.setDelay(avgDelay);

                // bereken traffic delay percentage ( travelTime / baseTime) - 1
                double delayPercentage = getTrafficDelayPercentage(r, summaryPerProvider.values().stream().toArray(RouteDataVM[]::new));
                entry.setTrafficDelayPercentage(delayPercentage);

                // bereken gemiddelde travel time (met traffic)
                int totalTravelTime = items.stream().mapToInt(RouteDataVM::getTravelTime).sum();
                double avgCurrentTravelTime = totalTravelTime / (float) items.size();
                entry.setAverageCurrentTravelTime(avgCurrentTravelTime);
            }
            else {
                entry.setAverageCurrentTravelTime(-1);
                entry.setDelay(-1);
            }
        }
        return overview;
    }

    /**
     * Geeft de details van een route in json terug
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     * @throws ClassNotFoundException
     */
    @ResponseBody
    @RequestMapping(value = "route/routedata", method = RequestMethod.GET)
    public RouteDataAggregate restRouteData(@RequestParam("id") int id, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate)
            throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        IPOIService poiService = new POIService(routeService);
        IProviderService providerService = new ProviderService(routeService, poiService);

        List<RouteData> routeData = providerService.getRouteDataForRoute(id, startDate, endDate, "Timestamp");
        // combine data per provider
        HashMap<String, RouteRestData> travelTimeMap = new HashMap<>();
        HashMap<String, RouteRestData> delayMap = new HashMap<>();
        for (RouteData routeEle : routeData) {
            String providerName = routeEle.getProvider().name();
            // travel time data
            if (!travelTimeMap.containsKey(providerName)) {
                travelTimeMap.put(providerName, new RouteRestData(providerName));
            }
            long[] travelTimeEle = {routeEle.getTimestamp().getTime(), routeEle.getTravelTime()};
            travelTimeMap.get(providerName).addDataElement(travelTimeEle);

            // delay data
            if (!delayMap.containsKey(providerName)) {
                delayMap.put(providerName, new RouteRestData(providerName));
            }
            long[] delayEle = {routeEle.getTimestamp().getTime(), routeEle.getDelay()};
            delayMap.get(providerName).addDataElement(delayEle);
        }

        // sort by provider name
        List<RouteRestData> travelTime = new ArrayList<>(travelTimeMap.values());
        Collections.sort(travelTime, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        List<RouteRestData> delay = new ArrayList<>(delayMap.values());
        Collections.sort(delay, (o1, o2) -> o1.getName().compareTo(o2.getName()));

        RouteDataAggregate aggregate = new RouteDataAggregate(travelTime, delay);
        return aggregate;
    }

    /**
     * Geeft de traffic data van een route in json terug
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     * @throws ClassNotFoundException
     */
    @ResponseBody
    @RequestMapping(value = "route/trafficdata", method = RequestMethod.GET)
    public List<RouteDetailTrafficJam> restTrafficData(@RequestParam("id") int id, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate)
            throws ClassNotFoundException {
        IRouteService routeService = new RouteService();

        // vraag alle files op voor de route & de oorzaken
        List<RouteTrafficJam> jams = routeService.getRouteTrafficJamsForRouteBetween(id, startDate, endDate);
        List<GroupedRouteTrafficJamCause> causes = routeService.getRouteTrafficJamCausesForRouteBetween(id, startDate, endDate);
        // groepeer de oorzaken per file
        Map<Integer, List<GroupedRouteTrafficJamCause>> causesByTrafficJamId = causes.stream().collect(Collectors.groupingBy(c -> c.getRouteTrafficJamId()));

        // bouw per file de oorzaken op in de data
        List<RouteDetailTrafficJam> detailJams = new ArrayList<>();
        for (RouteTrafficJam j : jams) {
            RouteDetailTrafficJam detailJam = new RouteDetailTrafficJam(j);

            List<GroupedRouteTrafficJamCause> lst = causesByTrafficJamId.get(j.getId());
            if (lst != null) {
                detailJam.setCauses(lst);
            }

            detailJams.add(detailJam);
        }
        return detailJams;
    }

    /**
     * Geeft de detailpagina van een route
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     */
    @RequestMapping(value = "route/detail/{id}", method = RequestMethod.GET)
    public ModelAndView getDetail(@PathVariable("id") int id) throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        Route route = routeService.getRoute(id);

        // vraag de laatste provider gegevens op uit de database
        List<RouteData> summaries = routeService.getMostRecentRouteSummariesForRoute(id);
        List<RouteDataVM> summariesVM = summaries.stream().map(RouteDataVM::new).collect(Collectors.toList());

        // vindt extreme providers
        double mean = RouteDataStatistics.mean(summaries);
        double stdev = Math.sqrt(RouteDataStatistics.variance(mean, summaries));
        for (int i = 0; i < summariesVM.size(); i++) {
            if(!RouteDataStatistics.withinStd(summariesVM.get(i).getTravelTime(), mean, stdev, 1)){
                summariesVM.get(i).setExtreme(true);
            }
        }

        // bouw view model op
        ModelAndView model = new ModelAndView("route/detail");
        model.addObject("route", new RouteDetailsVM(route));
        model.addObject("summaries", summariesVM);
        return model;
    }

    /**
     * Toont de kaartweergave
     *
     * @return
     * @throws ClassNotFoundException
     */
    @RequestMapping(value = "route/map", method = RequestMethod.GET)
    public ModelAndView getMap() throws ClassNotFoundException {
        IRouteService routeService = new RouteService();

        // geef het overzicht van alle routes
        RouteOverviewVM overview = getRouteOverviewModel(routeService);
        // en voeg het toe als model
        ModelAndView model = new ModelAndView("route/map");
        model.addObject("overview", overview);

        return model;
    }

    /**
     * Geeft de map gegevens als json terug
     *
     * @param req
     * @return
     * @throws ClassNotFoundException
     */
    @ResponseBody
    @RequestMapping(value = "route/mapdata", method = RequestMethod.GET)
    public MapData ajaxGetMapRoutes(HttpServletRequest req) throws ClassNotFoundException, ParseException {
        // als id leeg is geef alle routes gegevens terug
        if (req.getParameter("id") == null || req.getParameter("id").equalsIgnoreCase("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            
            Date before = req.getParameter("before") == null ? null : f.parse(req.getParameter("before"));
            return getAllRouteMapData(before);
        } else {
            // anders geef maar de map gegevens voor 1 route terug
            int id = Integer.parseInt(req.getParameter("id"));
            return getRouteMapData(id);
        }
    }

    /**
     * Toont de edit pagine van een route
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     */
    @RequestMapping(value = "route/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Integer id) throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        Route r = routeService.getRoute(id);

        // maak route edit view model
        RouteEditVM re = new RouteEditVM();
        re.setId(r.getId());
        re.setName(r.getName());
        re.setFromAddress(r.getFromAddress());
        re.setToAddress(r.getToAddress());
        re.setFromLatLng(r.getFromLatitude() + "," + r.getFromLongitude());
        re.setToLatLng(r.getToLatitude() + "," + r.getToLongitude());
        re.setAvoidHighwaysOrUseShortest(r.getAvoidHighwaysOrUseShortest());
        ModelAndView model = new ModelAndView("route/edit");
        model.addObject("routeEdit", re);

        return model;
    }

    /**
     * Valideert en slaat de gewijzigde route gegevens op
     *
     * @param route
     * @param result
     * @return
     * @throws ClassNotFoundException
     */
    @RequestMapping(value = "route/edit/{id}", method = RequestMethod.POST)
    public ModelAndView edit(@Valid
            @ModelAttribute("routeEdit") RouteEditVM route, BindingResult result) throws ClassNotFoundException {

        // validation, moet manueel aangezien geen hibernate-validators toegevoegd is
        if (route.getName() == null || route.getName().isEmpty()) {
            result.rejectValue("name", "error.name", "Naam is verplicht");
        }

        // parse de start lat & lng
        double fromLat = 0;
        double fromLng = 0;
        try {
            fromLat = Double.parseDouble(route.getFromLatLng().split(",")[0]);
            fromLng = Double.parseDouble(route.getFromLatLng().split(",")[1]);
        } catch (Exception ex) {
            result.rejectValue("fromLatLng", "error.fromLatLng", "Ongeldige van positie");
        }

        // parse de end lat & lng
        double toLat = 0;
        double toLng = 0;
        try {
            toLat = Double.parseDouble(route.getToLatLng().split(",")[0]);
            toLng = Double.parseDouble(route.getToLatLng().split(",")[1]);
        } catch (Exception ex) {
            result.rejectValue("toLatLng", "error.toLatLng", "Ongeldige naar positie");
        }

        // als er validation erros zijn toon de errors bij de input velden
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("route/edit", result.getModel());
            model.addObject("errors", result);
            return model;
        } else {
            // vraag de bestaande route op
            IRouteService routeService = new RouteService();
            Route r = routeService.getRoute(route.getId());

            // pas de gegevens aan
            r.setName(route.getName());

            // als de waypoints of kortste/snelste route is geupdate
            // dan moet de waypoints geupdate worden
            boolean updateWaypoints = r.getFromLatitude() != fromLat
                    || r.getFromLongitude() != fromLng
                    || r.getToLatitude() != toLat
                    || r.getToLongitude() != toLng
                    || r.getAvoidHighwaysOrUseShortest() != route.getAvoidHighwaysOrUseShortest();

            r.setFromAddress(route.getFromAddress());
            r.setToAddress(route.getToAddress());
            r.setFromLatitude(fromLat);
            r.setFromLongitude(fromLng);

            r.setToLatitude(toLat);
            r.setToLongitude(toLng);
            r.setAvoidHighwaysOrUseShortest(route.getAvoidHighwaysOrUseShortest());

            // update het route object
            routeService.updateRoute(r, updateWaypoints);

            return new ModelAndView(
                    "redirect:/route/detail/" + r.getId());
        }
    }

    /**
     * Geeft de route kaart gegevens terug van een bepaald id
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     */
    private MapData getRouteMapData(int id) throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        Route r = routeService.getRoute(id);
        IPOIService poiService = new POIService(routeService);

        // vraag alle waypoints van de route
        List<RouteWaypoint> waypoints = routeService.getRouteWaypointsForRoute(id);
        // vraag de laatste provider gegevens van de route
        List<RouteData> summaries = routeService.getMostRecentRouteSummariesForRoute(id);
        List<RouteDataVM> summariesVM = summaries.stream().map(RouteDataVM::new).collect(Collectors.toList());

        MapData data = new MapData();
        // verzamel de map route gegevens op basis van de route en de laatste provider gegevens
        MapRoute mr = getMapRoute(r, summariesVM);

        // vraag alle POI gegevens op van de laatste 10min
        Date from = Date.from(java.time.LocalDateTime.now().minusMinutes(5).toInstant(ZoneOffset.UTC));
        Date to = Date.from(java.time.LocalDateTime.now().plusMinutes(5).toInstant(ZoneOffset.UTC));
        List<POIWithDistanceToRoute> pois = poiService.getPOIsNearRoute(r.getId(), from, to);
        // bouw een map poi object van het poi object op
        for (POI poi : pois) {
            MapPOI mp = getMapPOIFromPOI(poi);
            data.getPois().add(mp);
        }

        data.getRoutes().add(mr);

        // maak van alle waypoints map waypoints objecten
        for (RouteWaypoint waypoint : waypoints) {
            MapWaypoint wp = new MapWaypoint(waypoint.getLatitude(), waypoint.getLongitude());
            mr.getWaypoints().add(wp);
        }

        return data;
    }

    /**
     * Berekent de percentage van de vertraging van de route aan de hand van de
     * laatste provider gegevens
     *
     * @param r
     * @param summaries
     * @return
     */
    private double getTrafficDelayPercentage(Route r, RouteDataVM[] summaries) {
        if (summaries.length <= 0) {
            return 0;
        }

        // tel alle base travel times op
        double baseTravelTime = Arrays.stream(summaries).mapToDouble(rd -> rd.getBaseTime()).sum();
        // bereken gemiddelde travel time
        double avgBaseTravelTime = baseTravelTime / (float) summaries.length;

        // tel alle travel times (die delay bevatten)
        int totalTravelTime = Arrays.stream(summaries).mapToInt(rd -> rd.getTravelTime()).sum();
        double avgTravelTime = totalTravelTime / (float) summaries.length;

        // bereken percentage
        double percentage = (avgTravelTime / avgBaseTravelTime) - 1;
        return percentage;
    }

    /**
     * Verzamelt alle map gegevens voor alle routes
     *
     * @return
     * @throws ClassNotFoundException
     */
    private MapData getAllRouteMapData(Date before) throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        IPOIService poiService = new POIService(routeService);
        // vraag alle trajecten met hun waypoints op
        List<Route> routes = routeService.getRoutes();
        List<RouteWaypoint> waypoints = routeService.getRouteWaypoints();

        MapData data = new MapData();
        Map<Integer, MapRoute> mapRoutesPerId = new HashMap<>();
        Map<Integer, List<RouteDataVM>> summariesPerRouteId = new HashMap<>();

        
        // vraag alle meest recente route data op 
        // en voeg ze toe in de map
        for (RouteData summary : routeService.getMostRecentRouteSummaries(before)) {
            List<RouteDataVM> lst;
            if (!summariesPerRouteId.containsKey(summary.getRouteId())) {
                summariesPerRouteId.put(summary.getRouteId(), lst = new ArrayList<>());
            } else {
                lst = summariesPerRouteId.get(summary.getRouteId());
            }

            lst.add(new RouteDataVM(summary));
        }

        // vraag alle actieve pois op en map ze op map poi objectne
        List<POI> pois = poiService.getActivePOIs(before);
        for (POI poi : pois) {
            MapPOI mp = getMapPOIFromPOI(poi);
            data.getPois().add(mp);
        }

        // overloop alle trajecten en bereken aan de hand van de verzamelde route data's in
        // de map de gemiddelde delay & percentage
        for (Route r : routes) {
            List<RouteDataVM> routeSummaries;
            if (!summariesPerRouteId.containsKey(r.getId())) {
                routeSummaries = new ArrayList<>();
            } else {
                routeSummaries = summariesPerRouteId.get(r.getId());
            }

            // map de route en laatste gegevens op een map route object
            MapRoute mr = getMapRoute(r, routeSummaries);

            // en voeg het object toe
            mapRoutesPerId.put(r.getId(), mr);
            data.getRoutes().add(mr);
        }

        // voeg waypoints toe aan elk traject
        for (RouteWaypoint waypoint : waypoints) {
            MapWaypoint wp = new MapWaypoint(waypoint.getLatitude(), waypoint.getLongitude());
            mapRoutesPerId.get(waypoint.getRouteId()).getWaypoints().add(wp);
        }

        return data;
    }

    /**
     * Mapped een POI object op een MapPOI
     *
     * @param poi
     * @return
     */
    private MapPOI getMapPOIFromPOI(POI poi) {
        MapPOI mp = new MapPOI();
        mp.setId(poi.getId());
        mp.setLatitude(poi.getLatitude());
        mp.setLongitude(poi.getLongitude());
        mp.setInfo(poi.getInfo());
        mp.setCategory(poi.getCategory().getValue());
        mp.setSince(poi.getSince().toString());
        mp.setSource(poi.getProvider().toString());
        return mp;
    }

    /**
     * Bouwt een map route object op adhv van een route en de recenste provider
     * gegevens
     *
     * @param r
     * @param routeSummaries
     * @return
     */
    private MapRoute getMapRoute(Route r, List<RouteDataVM> routeSummaries) {
        MapRoute mr = new MapRoute();
        mr.setName(r.getName());
        mr.setDistance(r.getDistance());
        mr.setId(r.getId());
        double traficDelayPercentage = getTrafficDelayPercentage(r, routeSummaries.stream().toArray(RouteDataVM[]::new));
        mr.setTrafficDelayPercentage(traficDelayPercentage);
        int totalDelay = routeSummaries.stream().mapToInt(RouteDataVM::getDelay).sum();
        double avgDelay = totalDelay / (float) routeSummaries.size();
        mr.setCurrentDelay(avgDelay);
        int totalTravelTime = routeSummaries.stream().mapToInt(rd -> rd.getTravelTime()).sum();
        double avgTravelTime = totalTravelTime / (float) routeSummaries.size();
        mr.setAverageCurrentTravelTime(avgTravelTime);
        return mr;
    }
}
