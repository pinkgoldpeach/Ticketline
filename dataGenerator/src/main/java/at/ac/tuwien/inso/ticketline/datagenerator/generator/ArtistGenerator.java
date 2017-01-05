package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.ArtistDao;
import at.ac.tuwien.inso.ticketline.model.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Carla on 08/05/2016.
 */
@Component
public class ArtistGenerator implements DataGenerator{
    private static final Logger LOGGER = LoggerFactory.getLogger(ArtistGenerator.class);

    @Autowired
    private ArtistDao dao;

    /**
     * {@inheritDoc}
     */

    @Override
    public void generate() {
//    public Artist(String firstname, String lastname, String description) {

        LOGGER.info("+++++ Generate Artist Data +++++");

        // ---------------------------
        String firstname1 = "Anthony";
        String lastname1 = "Kiedis";
        String desc1 = "An American singer and songwriter best known as the lead vocalist of the band Red Hot Chili Peppers";
        Artist artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Maria";
        lastname1 = "Langasch";
        desc1 = "French singer who loves to swing her hips on smooth Jazz";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);


        // ---------------------------
        firstname1 = "Billy";
        lastname1 = "Talent";
        desc1 = "Billy Talent is a Canadian rock band from Mississauga, Ontario. ";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Michael";
        lastname1 = "Jackson";
        desc1 = "Michael Joseph Jackson was an American singer, songwriter, record producer, dancer, and actor.";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);


        // ---------------------------
        firstname1 = "Sarah";
        lastname1 = "Welsch";
        desc1 = "Swedish pop singer who won 1980 at the European Song Contest";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);


        // ---------------------------
        firstname1 = "Kelly";
        lastname1 = "Clarkson";
        desc1 = "Kelly Brianne Clarkson is an American singer, songwriter and children's book author. ";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Jared";
        lastname1 = "Leto";
        desc1 = "Jared Joseph Leto is an American actor, singer-songwriter, and director.";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);


        // ---------------------------
        firstname1 = "Christina";
        lastname1 = "Stürmer";
        desc1 = "Christina Stürmer is an Austrian pop/rock singer.";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);


        // ---------------------------
        firstname1 = "Conchita";
        lastname1 = "Wurst";
        desc1 = "Conchita Wurst is an Austrian pop recording artist and drag queen portrayed by Thomas \"Tom\" Neuwirth. ";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Celine";
        lastname1 = "Dion";
        desc1 = "Céline Marie Claudette Dion, is a Canadian singer and businesswoman. ";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);


        // ---------------------------
        firstname1 = "Eros";
        lastname1 = "Ramazotti";
        desc1 = "Eros Luciano Walter Ramazzotti, known simply as Eros Ramazzotti, is an Italian musician and singer-songwriter. ";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Beyonce";
        lastname1 = "Knowles";
        desc1 = "Beyoncé Giselle Knowles was born in Houston, Texas";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "David";
        lastname1 = "Guetta";
        desc1 = "Pierre David Guetta (French pronunciation: ; born 7 November 1967) is a French DJ, record producer and remixer.";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Martin";
        lastname1 = "Garrix";
        desc1 = "In 2015, Garrix experimented with progressive house and started producing progressive style.";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Mary J";
        lastname1 = "Bling";
        desc1 = "Mary Jane Blige is an American singer, songwriter, model, record producer and actress.";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Kanye";
        lastname1 = "West";
        desc1 = "Kanye Omari West is an American hip hop recording artist, songwriter, record producer and fashion designer";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Arnold";
        lastname1 = "Schwarzenegger";
        desc1 = "Schwarzenegger was born in Thal, a village in Styria, and christened Arnold Alois.";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Cameron";
        lastname1 = "Diaz";
        desc1 = "Cameron Michelle Diaz is an American actress, producer, and former fashion model.";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Til";
        lastname1 = "Schweiger";
        desc1 = "Tilman Valentin \"Til\" Schweiger is a German actor, director, and producer";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Sia";
        lastname1 = "Furler";
        desc1 = "Sia Kate Isobelle Furler, referred to mononymously as Sia, is an Australian singer and songwriter.";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Jimmy";
        lastname1 = "Fallon";
        desc1 = "James Thomas \"Jimmy\" Fallon is an American comedian, television host, actor, singer, writer, and producer.";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Simon";
        lastname1 = "Baker";
        desc1 = "Simon Baker (born 30 July 1969) is an Australian actor and director";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Robyn Rihanna";
        lastname1 = "Fenty";
        desc1 = "Robyn Rihanna Fenty,known professionally as Rihanna, is a Barbadian singer and songwriter. ";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Phil";
        lastname1 = "Collins";
        desc1 = "Philip David Charles \"Phil\" Collins is an English singer, songwriter,record producer and actor.";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        // ---------------------------
        firstname1 = "Agnes";
        lastname1 = "Obel";
        desc1 = "Agnes Caroline Thaarup Obel is a Danish singer/songwriter. ";
        artist1 = new Artist(firstname1, lastname1, desc1);
        dao.save(artist1);

        /************************ automatic artist generator ************************/

        for(int i = 19; i < 101; i++){
            String firstnameGenerated = "firstname"+i;
            String lastnameGenerated = "lastname"+i;
            String descGenerated = "description for " + firstnameGenerated + " " + lastnameGenerated;
            Artist artistGenerated = new Artist(firstnameGenerated, lastnameGenerated, descGenerated);
            dao.save(artistGenerated);
        }

    }
}
