package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Participation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Carla on 17/05/2016.
 */
public class ParticipationDaoTest extends AbstractDaoTest{

    @Autowired
    private ParticipationDao participationDao;


    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testFindAll() {
        List<Participation> participations = participationDao.findAll();
        assertEquals("Check DB initial data - is 3 first", 3, participations.size());
    }

    @Test
    public void findOne() {
        Participation participation = participationDao.findOne(1);
        assertNotNull(participation);
        assertEquals("Check findOne on first element", 1, (int) participation.getId());

    }
}
