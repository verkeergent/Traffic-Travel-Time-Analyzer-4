package be.ugent.verkeer4.verkeerdal;

public class Repository extends BaseRepository {

    private TrajectDbSet trajects;
    
    public Repository() throws ClassNotFoundException {
        // TODO: save db , user & ww in settings
        super("//localhost:3306/verkeer", "root", "");
    }

    @Override
    protected void initializeSets() {
        this.trajects = new TrajectDbSet(sql2o);
    }

    public TrajectDbSet getTrajectSet() {
        return this.trajects;
    }
}
