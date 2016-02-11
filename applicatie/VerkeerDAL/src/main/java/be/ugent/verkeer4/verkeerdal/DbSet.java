package be.ugent.verkeer4.verkeerdal;


import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.sql2o.Query;
import org.sql2o.Sql2o;

public class DbSet<T> {

    private Sql2o sql2o;
    private Class<T> type;

    public DbSet(Class<T> type, Sql2o sql2o) {
        this.type = type;
        this.sql2o = sql2o;
    }

    public List<T> getItems() {
        try (org.sql2o.Connection con = sql2o.open()) {
            List<T> lst = con.createQuery("SELECT * from " + this.type.getSimpleName())
                    .executeAndFetch(this.type);
            return lst;
        }
    }

    public T getItem(String condition, Map<String, Object> parameters) {
        try (org.sql2o.Connection con = sql2o.open()) {
            Query q = con.createQuery("SELECT * from " + this.type.getSimpleName() + " WHERE " + condition);

            for (Entry<String, Object> parameter : parameters.entrySet()) {
                q.addParameter(parameter.getKey(), parameter.getValue());
            }
            T obj = q.executeAndFetchFirst(this.type);
            return obj;
        }
    }

}
