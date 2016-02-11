package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.Traject;
import java.util.List;

public interface ITrajectService {

    public List<Traject> getTrajecten() throws ClassNotFoundException;

    public Traject getTraject(int id) throws ClassNotFoundException;

}
