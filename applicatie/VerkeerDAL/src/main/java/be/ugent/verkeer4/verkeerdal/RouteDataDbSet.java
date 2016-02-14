package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.sql2o.Sql2o;

public class RouteDataDbSet extends DbSet<RouteData> {

    public RouteDataDbSet(Sql2o sql2o) {
        super(RouteData.class, sql2o);
    }

    public List<RouteData> getItemsForRoute(int routeId, Date from, Date to) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("RouteId", routeId);
        map.put("From", from);
        map.put("To", to);
        
        return this.getItems("RouteId = :RouteId AND Timestamp BETWEEN :From and :To", map);
    }
}
