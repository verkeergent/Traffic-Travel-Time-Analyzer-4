package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.Traject;
import org.sql2o.Sql2o;


public class TrajectDbSet extends DbSet<Traject> {
    
    public TrajectDbSet(Sql2o sql2o) {
        super(Traject.class, sql2o);
    }
    
}
