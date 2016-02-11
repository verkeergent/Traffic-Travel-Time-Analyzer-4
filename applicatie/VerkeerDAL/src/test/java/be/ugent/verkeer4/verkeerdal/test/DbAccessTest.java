package be.ugent.verkeer4.verkeerdal.test;

import be.ugent.verkeer4.verkeerdal.Repository;
import be.ugent.verkeer4.verkeerdomain.data.Traject;
import java.util.List;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class DbAccessTest  extends TestCase {
    
    
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
  
}
