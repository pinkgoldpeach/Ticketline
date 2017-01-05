package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.LocationDao;
import at.ac.tuwien.inso.ticketline.model.Address;
import at.ac.tuwien.inso.ticketline.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Carla on 08/05/2016.
 */
@Component
public class LocationGenerator implements DataGenerator{
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationGenerator.class);

    @Autowired
    private LocationDao dao;

    /**
     * {@inheritDoc}
     */

    @Override
    public void generate() {
        //    public Address(String street, String postalCode, String city, String country) {
//(String name, String description, String owner, Address address)

        LOGGER.info("+++++ Generate Location Data +++++");

        // ---------------------------

        Address address1 = new Address("1 Graces Alley","E1 8JB","London","England");
        String name1 = "Wilton's Music Hall";
        String desc1 = "The oldest surviving Music Hall in the world, now home to original theatre. Productions, hire, mailing list, and friends.";
        String owner1 = "Wilton's Music Hall Trust";

        Location loc1 = new Location(name1, desc1, owner1, address1);
        dao.save(loc1);

        // ---------------------------

        address1 = new Address("Level 4 Westfield Westcity Edsel St","0612","Auckland","New Zealand");
        name1 = "Event Cinemas";
        desc1 = "Cinema chain in New Zealand";
        owner1 = "Event Cinemas";

        loc1 = new Location(name1, desc1, owner1, address1);
        dao.save(loc1);

        // ---------------------------

        address1 = new Address("845 Market St","CA 94103","San Francisco","USA");
        name1 = "Century San Francisco Centre 9 and XD";
        desc1 = "Cinema in the city centre of San Francisco";
        owner1 = "Cinemark";

        loc1 = new Location(name1, desc1, owner1, address1);
        dao.save(loc1);

        // ---------------------------

        address1 = new Address("Homburgstraße 37","78579","Neuhausen ob Eck","Germany");
        name1 = "Festival area";
        desc1 = "Former military airport, now festival area";
        owner1 = "FKP Scorpio";

        loc1 = new Location(name1, desc1, owner1, address1);
        dao.save(loc1);

        /********************************************/

        address1 = new Address("Nevada 447","89412","Black Rock City","USA");
        name1 = "Festival area";
        desc1 = "A festival area";
        owner1 = "Burning man peeps";

        loc1 = new Location(name1, desc1, owner1, address1);
        dao.save(loc1);

        /********************************************/

        address1 = new Address("Via Filodrammatici 2","20121","Milan", "Italy");
        name1 = "Teatro alla Scala";
        desc1 = "La Scala is an opera house in Milan, Italy.";
        owner1 = "City of Milan";
        loc1 = new Location(name1, desc1, owner1, address1);
        dao.save(loc1);

        /********************************************/

        address1 = new Address("Universitaetsring 2","1010","Vienna", "Austria");
        name1 = "Burgtheater";
        desc1 = "The Burgtheater is the Austrian National Theatre in Vienna and one of the most important German language theatres in the world.";
        owner1 = "Burgtheater GmbH";

        loc1 = new Location(name1, desc1, owner1, address1);
        dao.save(loc1);

        /********************************************/

        address1 = new Address("Roland Rainer Platz 1","1150","Vienna", "Austria");
        name1 = "Wiener Stadthalle";
        desc1 = "A venue for concerts";
        owner1 = "Stadt Wien";

        loc1 = new Location(name1, desc1, owner1, address1);
        dao.save(loc1);


        /************************ automatic location generator ************************/

        for (int i = 9; i < 201; i++) {
            Address addressGenerated = new Address("Generated Street " + i,""+(i+5)+""+(i-1)+""+(i+2)+""+i ,"City"+i,"Country"+i);
            String nameGenerated = "Locations name"+i;
            String descGenerated = "very informative description"+i;
            String ownerGenerated = "Great owner"+i;

            Location locGenerated = new Location(nameGenerated, descGenerated, ownerGenerated, addressGenerated);
            dao.save(locGenerated);
        }
    }
}
