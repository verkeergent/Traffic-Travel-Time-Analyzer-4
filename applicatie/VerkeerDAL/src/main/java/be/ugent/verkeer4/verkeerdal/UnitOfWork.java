package be.ugent.verkeer4.verkeerdal;

public class UnitOfWork extends BaseRepository implements IUnitOfWork {

    private RouteDbSet routes;
    
    public UnitOfWork(String connectionString, String user, String password) throws ClassNotFoundException {
        super(connectionString, user,password);
    }

    @Override
    protected void initializeSets() {
        this.routes = new RouteDbSet(sql2o);
    }

    @Override
    public RouteDbSet getRouteSet() {
        return this.routes;
    }
}
