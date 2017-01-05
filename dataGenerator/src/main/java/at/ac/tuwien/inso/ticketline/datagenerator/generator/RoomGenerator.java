package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.LocationDao;
import at.ac.tuwien.inso.ticketline.dao.RoomDao;
import at.ac.tuwien.inso.ticketline.model.Location;
import at.ac.tuwien.inso.ticketline.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Carla on 08/05/2016.
 */
@Component
public class RoomGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomGenerator.class);

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private LocationDao locationDao;

    /**
     * {@inheritDoc}
     */

    @Override
    public void generate() {
        LOGGER.info("+++++ Generate Room Data +++++");

        // ---------------------------
        Location location1 = locationDao.findOne(1);
        String name1 = "Hall_1";
        String desc1 = "Beautiful hall";
        boolean seatChoice1 = true;
        Room room1 = new Room(name1, desc1, seatChoice1, location1);
        roomDao.save(room1);
        //    public Room(String name, String description, Location location) {

        /****************************************/
        String[] letters = {"A", "B", "C", "D", "E"};
        String[] descs = {"Beautiful", "Gorgeous", "Fabulous", "Fantastic", "Superior"};
        for (int i = 0; i < 5; i++) {
            Location location2 = locationDao.findOne(1);
            String name2 = "Hall_" + letters[i];
            String desc2 = descs[i] + " hall";
            boolean seatChoice2 = true;
            Room room2 = new Room(name2, desc2, seatChoice2, location2);
            roomDao.save(room2);
        }

        /****************************************/

        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",};
        String[] descs2 = {"Comfy", "Big", "Middle"};
        for (int i = 0; i < 10; i++) {
            Location location2 = locationDao.findOne(2);
            String name2 = "Hall_" + numbers[i];
            String desc2 = descs2[i % 3] + " hall";
            boolean seatChoice2 = true;
            Room room2 = new Room(name2, desc2, seatChoice2, location2);
            roomDao.save(room2);
        }

        /****************************************/

        for (int i = 0; i < 10; i++) {
            Location location2 = locationDao.findOne(3);
            String name2 = "Hall_" + numbers[i];
            String desc2 = descs2[i % 3] + " hall";
            boolean seatChoice2 = true;
            Room room2 = new Room(name2, desc2, seatChoice2, location2);
            roomDao.save(room2);
        }

        /****************************************/

        location1 = locationDao.findOne(4);
        name1 = "Outdoor stages";
        desc1 = "4 stages that are outdoors";
        seatChoice1 = false;
        room1 = new Room(name1, desc1, seatChoice1, location1);
        roomDao.save(room1);

        /****************************************/

        location1 = locationDao.findOne(5);
        name1 = "Outdoor stages";
        desc1 = "Various stages that are outdoors";
        seatChoice1 = false;
        room1 = new Room(name1, desc1, seatChoice1, location1);
        roomDao.save(room1);

        /****************************************/

        for (int j = 6; j < 8; j++) {
            for (int i = 0; i < 5; i++) {
                Location location2 = locationDao.findOne(j);
                String name2 = "Hall_" + letters[i];
                String desc2 = descs[i % 5] + " hall";
                boolean seatChoice2 = true;
                Room room2 = new Room(name2, desc2, seatChoice2, location2);
                roomDao.save(room2);
            }
        }

        /****************************************/

        for (int i = 0; i < 4; i++) {
            Location location2 = locationDao.findOne(8);
            String name2 = "Hall_" + letters[i];
            String desc2 = descs2[i % 3] + " hall";
            boolean seatChoice2 = false;
            Room room2 = new Room(name2, desc2, seatChoice2, location2);
            roomDao.save(room2);
        }


/************************ automatic room generator ************************/


        for (int i = 9; i < 91; i++) {
            Location locationGenerated;
            locationGenerated = locationDao.findOne(i);
            for (int j = 1; j < 5; j++) {
                String nameGenerated = "Roomname " + j;
                String descriptionGenerated = descs[j % 5] + " hall";
                boolean seatChoiceGenerated = true;
                Room roomGenerated = new Room(nameGenerated, descriptionGenerated, seatChoiceGenerated, locationGenerated);
                roomDao.save(roomGenerated);
            }
        }

        for (int i = 91; i < 141; i++) {
            Location locationGenerated;
            locationGenerated = locationDao.findOne(i);
            for (int j = 1; j < 3; j++) {
                String nameGenerated = "Roomname " + j;
                String descriptionGenerated = descs2[j % 3] + " hall";
                boolean seatChoiceGenerated = false;
                Room roomGenerated = new Room(nameGenerated, descriptionGenerated, seatChoiceGenerated, locationGenerated);
                roomDao.save(roomGenerated);
            }
        }
    }

}

