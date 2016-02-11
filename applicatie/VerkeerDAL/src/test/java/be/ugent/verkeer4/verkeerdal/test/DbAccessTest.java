package be.ugent.verkeer4.verkeerdal.test;

import be.ugent.verkeer4.verkeerdal.Repository;
import be.ugent.verkeer4.verkeerdomain.data.Traject;
import java.util.List;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class DbAccessTest extends TestCase {

    private Repository repo;

    public DbAccessTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws ClassNotFoundException {
        repo = new Repository();
    }

    @After
    public void tearDown() {
    }

    public void testGetTrajecten() {
        try {
            List<Traject> trajecten = repo.getTrajectSet().getItems();
            assertFalse("Geen trajecten gevonden", trajecten.isEmpty());
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }

    public void testGetSingleTraject() {
        try {
            Traject traject = repo.getTrajectSet().getItem(1);
            assertNotNull("Traject niet gevonden", traject);
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }

    public void testInsertTraject() {
        Traject t = new Traject();
        t.setNaam("Test traject");
        t.setAfstand(1234);

        int id = repo.getTrajectSet().insert(t);
        assertNotSame("Id is 0", 0, id);

        t.setId(id);

        Traject storedTraject = repo.getTrajectSet().getItem(id);

        assertEquals("Naam is niet hetzelfde", t.getNaam(), storedTraject.getNaam());
        assertEquals("Afstand is niet hetzelfde", t.getAfstand(), storedTraject.getAfstand());
    }

    public void testUpdateTraject() {

        Traject t = repo.getTrajectSet().getItem(1);
        t.setId(0);
        int id = repo.getTrajectSet().insert(t); // copy
        t.setId(id);
        t.setNaam("Updated naam" + t.getNaam());
        try {
            repo.getTrajectSet().update(t);
        } catch (Exception ex) {
            fail("Error: " + ex.getMessage());
        }

        Traject storedTraject = repo.getTrajectSet().getItem(id);
        assertEquals("Naam is niet hetzelfde", t.getNaam(), storedTraject.getNaam());
    }
    
    public void testDeleteTraject() {
        List<Traject> trajecten = repo.getTrajectSet().getItems();
        Traject lastTraject = trajecten.get(trajecten.size()-1);
        repo.getTrajectSet().delete(lastTraject.getId());
        
        Traject storedTraject = repo.getTrajectSet().getItem(lastTraject.getId());
        assertNull("Traject is niet verwijderd", storedTraject);
        
        
    }

}
