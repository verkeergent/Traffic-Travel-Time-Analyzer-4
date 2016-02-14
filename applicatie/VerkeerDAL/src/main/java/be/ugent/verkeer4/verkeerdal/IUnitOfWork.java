package be.ugent.verkeer4.verkeerdal;

public interface IUnitOfWork {

    RouteDbSet getRouteSet();    
    
    RouteDataDbSet getRouteDataSet();    
}
