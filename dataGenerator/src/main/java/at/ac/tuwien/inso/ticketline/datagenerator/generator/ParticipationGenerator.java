package at.ac.tuwien.inso.ticketline.datagenerator.generator;

/**
 * Created by Carla on 08/05/2016.
 */

import at.ac.tuwien.inso.ticketline.dao.ArtistDao;
import at.ac.tuwien.inso.ticketline.dao.ParticipationDao;
import at.ac.tuwien.inso.ticketline.dao.PerformanceDao;
import at.ac.tuwien.inso.ticketline.model.Artist;
import at.ac.tuwien.inso.ticketline.model.Participation;
import at.ac.tuwien.inso.ticketline.model.Performance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * This class generates data for news
 *
 * //@see at.ac.tuwien.inso.ticketline.model.at.ac.tuwien.inso.ticketline.model.Participation
 */
@Component

public class ParticipationGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipationGenerator.class);

    @Autowired
    private ParticipationDao participationDao;

    @Autowired
    private PerformanceDao performanceDao;

    @Autowired
    private ArtistDao artistDao;

    /**
     * {@inheritDoc}
     */

    @Override
    public void generate() {
        LOGGER.info("+++++ Generate News Data +++++");
        //    public Participation(String artistRole, String description, Performance performance, Artist artist) {

        String artistRole1 = "Lead singer";
        String desc1 = "sings songs";
        Performance performance1 = performanceDao.findOne(1);
        Artist artist1 = artistDao.findOne(1);
        Participation participation1 = new Participation(artistRole1, desc1, performance1, artist1);
        participationDao.save(participation1);

        /************************ automatic participation generator random ************************/


        for (int i = 1; i < 101; i++) {
            //change this for better testing
            //participation will be created randomly
            int rand = 1 + (int)(Math.random() * ((100 - 1) + 1));
            String artistRoleGenerated = "artistRole"+i;
            String descGenerated = "very useful description"+i;
            //Performance performanceGenerated = performanceDao.findOne(rand);
            Performance performanceGenerated = performanceDao.findOne(i);
            //Artist artistGenerated = artistDao.findOne(rand);
            Artist artistGenerated = artistDao.findOne(i);
            Participation participationGenerated = new Participation(artistRoleGenerated, descGenerated, performanceGenerated, artistGenerated);
            participationDao.save(participationGenerated);

        }
    }
}
