package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdal.Repository;
import be.ugent.verkeer4.verkeerdomain.data.Traject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrajectService implements ITrajectService {

    @Override
    public List<Traject> getTrajecten() throws ClassNotFoundException {
        Repository repo = new Repository();
        return repo.getTrajectSet().getItems();
    }

    @Override
    public Traject getTraject(int id) throws ClassNotFoundException {
        Repository repo = new Repository();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Id", id);
        return repo.getTrajectSet().getItem("Id = :Id", parameters);
    }
}
