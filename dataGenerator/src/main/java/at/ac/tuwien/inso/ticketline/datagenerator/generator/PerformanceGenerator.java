package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.PerformanceDao;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.PerformanceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static at.ac.tuwien.inso.ticketline.model.PerformanceType.*;

/**
 * Created by Carla on 08/05/2016.
 */
@Component
public class PerformanceGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceGenerator.class);

    @Autowired
    private PerformanceDao dao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void generate() {
        LOGGER.info("+++++ Generate PErformance Data +++++");
//String name, String description, Integer duration, PerformanceType type
        String name1 = "RHCP";
        String desc1 = "Red Hot Chili Peppers, also sometimes shortened to \"The Chili Peppers\" or abbreviated as \"RHCP\", are an American rock band formed in Los Angeles in 1983.";
        Integer duration1 = 120;
        PerformanceType type1 = CONCERT;

        Performance p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        //-------------------------------------------------------

        name1 = "Katy Perry";
        desc1 = "A Canadian singer who love to entertain teenagers all over the world with her kitty-attitude.";
        duration1 = 200;
        type1 = CONCERT;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        //-------------------------------------------------------

        name1 = "The Avengers";
        desc1 = "Political interference in the Avengers' activities causes a rift between former allies Captain America and Iron Man.";
        duration1 = 220;
        type1 = MOVIE;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "Zootopia";
        desc1 = "Animated movie about animals";
        duration1 = 108;
        type1 = MOVIE;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "Brave";
        desc1 = "Brave is a 2012 American 3D computer-animated fantasy film produced by Pixar Animation Studios and released by Walt Disney Pictures. ";
        duration1 = 93;
        type1 = MOVIE;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "Wreck it Ralph";
        desc1 = "Wreck-It Ralph is a 2012 American 3D computer-animated fantasy-comedy film[4] produced by Walt Disney Animation Studios and released by Walt Disney Pictures.";
        duration1 = 101;
        type1 = MOVIE;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "Wall-E";
        desc1 = "WALL-E (stylized with an interpunct as WALL·E) is a 2008 American computer-animated science-fiction comedy film produced by Pixar Animation Studios and released by Walt Disney Pictures.";
        duration1 = 98;
        type1 = MOVIE;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "Cats";
        desc1 = "Cats is a musical composed by Andrew Lloyd Webber";
        duration1 = 180;
        type1 = MUSICAL;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "Les Misérables";
        desc1 = "A sung-through musical based on the novel Les Misérables by French poet and novelist Victor Hugo.";
        duration1 = 170;
        type1 = MUSICAL;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "Chicago";
        desc1 = "  Chicago (1975) is an American musical with music by John Kander";
        duration1 = 165;
        type1 = MUSICAL;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "Cabaret";
        desc1 = "Cabaret is a musical based on a book written by Christopher Isherwood, music by John Kander and lyrics by Fred Ebb.";
        duration1 = 150;
        type1 = MUSICAL;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "Southside";
        desc1 = "The Southside Festival (or, simply, Southside) is an annual music festival that takes place near Tuttlingen, southern Germany, usually every June.";
        duration1 = 4320;
        type1 = FESTIVAL;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "Burning Man";
        desc1 = "Burning Man is an annual gathering that takes place at Black Rock City—a temporary community erected in the Black Rock Desert in Nevada.";
        duration1 = 10080;
        type1 = FESTIVAL;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "Lollapalooza";
        desc1 = "Lollapalooza is an annual music festival featuring popular alternative rock, heavy metal, punk rock, hip hop, and EDM bands and artists, dance and comedy performances and craft booths. ";
        duration1 = 5760;
        type1 = FESTIVAL;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "Carmen";
        desc1 = "Carmen is an opera in four acts by French composer Georges Bizet. ";
        duration1 = 180;
        type1 = OPERA;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "La traviata";
        desc1 = "La traviata is an opera in three acts by Giuseppe Verdi set to an Italian libretto by Francesco Maria Piave.";
        duration1 = 195;
        type1 = OPERA;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "Così fan tutte";
        desc1 = "Così fan tutte is an Italian-language opera buffa in two acts by Wolfgang Amadeus Mozart first performed on 26 January 1790, at the Burgtheater in Vienna, Austria. ";
        duration1 = 175;
        type1 = OPERA;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "Saint Joans";
        desc1 = "Saint Joan of the Stockyards is a play written by the German modernist playwright Bertolt Brecht between 1929 and 1931.";
        duration1 = 110;
        type1 = THEATER;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "The Physicists";
        desc1 = "The Physicists (German: Die Physiker) is a satiric drama written in 1961 by Swiss writer Friedrich Duerrenmatt.";
        duration1 = 120;
        type1 = THEATER;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /***************************************************/

        name1 = "Macbeth";
        desc1 = "Macbeth is a tragedy written by William Shakespeare. Set mainly in Scotland, the play dramatises the damaging physical and psychological effects of political ambition on those who seek power for its own sake. ";
        duration1 = 113;
        type1 = THEATER;

        p1 = new Performance(name1, desc1, duration1, type1);
        dao.save(p1);

        /************************ automatic performance generator ************************/

        ArrayList<PerformanceType> performanceTypes = new ArrayList<>();
        performanceTypes.add(MOVIE);
        performanceTypes.add(FESTIVAL);
        performanceTypes.add(CONCERT);
        performanceTypes.add(MUSICAL);
        performanceTypes.add(OPERA);
        performanceTypes.add(THEATER);

        for (int i = 21; i < 121; i++) {
            String nameGenerated = "performance" + i;
            String descGenerated = "description for perfomance: " + nameGenerated;
            Integer durationGenerated = 30 * (i % 8) + 20;
            PerformanceType typeGenerated = performanceTypes.get((i % 6));

            Performance performanceGenerated = new Performance(nameGenerated, descGenerated, durationGenerated, typeGenerated);
            dao.save(performanceGenerated);
        }
    }
}
