package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.AreaDao;
import at.ac.tuwien.inso.ticketline.dao.RoomDao;
import at.ac.tuwien.inso.ticketline.dao.RowDao;
import at.ac.tuwien.inso.ticketline.model.Area;
import at.ac.tuwien.inso.ticketline.model.AreaType;
import at.ac.tuwien.inso.ticketline.model.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Carla on 15/05/2016.
 */
@Component
public class RowAndAreaGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RowAndAreaGenerator.class);

    @Autowired
    RowDao rowDao;

    @Autowired
    RoomDao roomDao;

    @Autowired
    AreaDao areaDao;

    /**
     * {@inheritDoc}
     */

    @Override
    public void generate() {
        LOGGER.info("+++++ Generate Row and Area Data +++++");

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        double price = 0;
        for (int j = 1; j < 3; j++) {
            price = 20.5;
            for (int i = 0; i < 12; i++) {
                Row row1 = new Row();
                row1.setRoom(roomDao.findOne(j));
                row1.setDescription("description of the row");
                row1.setName(String.valueOf(alphabet[(i % 26)]));
                if (i > 0 && i % 2 == 0) {
                    price += 1;
                }
                row1.setPrice(price);

                rowDao.save(row1);
            }
        }

        price = 128.0;
        for (int i = 0; i < 8; i++) {
            Row row1 = new Row();
            row1.setRoom(roomDao.findOne(3));
            row1.setDescription("description of the row");
            if (i > 25) {
                row1.setName(String.valueOf(alphabet[(i % 26)]) + "" + String.valueOf(alphabet[(i % 26)]));
            } else {
                row1.setName(String.valueOf(alphabet[(i % 26)]));
            }
            if (i > 0 && i % 4 == 0) {
                price += 2;
            }
            row1.setPrice(price);

            rowDao.save(row1);
        }

        price = 189.0;
        for (int i = 0; i < 13; i++) {
            Row row1 = new Row();
            row1.setRoom(roomDao.findOne(4));
            row1.setDescription("description of the row");
            if (i > 25) {
                row1.setName(String.valueOf(alphabet[(i % 26)]) + "" + String.valueOf(alphabet[(i % 26)]));
            } else {
                row1.setName(String.valueOf(alphabet[(i % 26)]));
            }

            if (i > 0 && i % 4 == 0) {
                price += 4;
            }
            row1.setPrice(price);

            rowDao.save(row1);
        }

        price = 35.8;
        for (int j = 5; j < 7; j++) {
            price += 10;
            for (int i = 0; i < 13; i++) {
                Row row1 = new Row();
                row1.setRoom(roomDao.findOne(j));
                row1.setDescription("description of the row");
                if (i > 25) {
                    row1.setName(String.valueOf(alphabet[(i % 26)]) + "" + String.valueOf(alphabet[(i % 26)]));
                } else {
                    row1.setName(String.valueOf(alphabet[(i % 26)]));
                }

                if (i > 0 && i % 4 == 0) {
                    price += 2;
                }
                row1.setPrice(price);

                rowDao.save(row1);
            }
        }

        price = 10.0;
        int roomsize = 0;

        for (int j = 7; j < 17; j++) {
            int max = 6;
            if (roomsize % 3 == 1) {
                max = 12;
            } else if (roomsize % 3 == 2) {
                max = 10;
            }
            roomsize++;
            for (int i = 0; i < max; i++) {
                Row row1 = new Row();
                row1.setRoom(roomDao.findOne(j));
                row1.setDescription("description of the row");
                String name = "";
                for (int k = 0; k < (i / 25) + 1; k++) {
                    name += String.valueOf(alphabet[(i % 26)]);
                }
                row1.setName(name);

                if (i > 0 && i % 4 == 0) {
                    price += 1;
                }
                row1.setPrice(price);

                rowDao.save(row1);
            }
        }

        price = 10.5;
        roomsize = 0;
        for (int j = 17; j < 27; j++) {
            int max = 5;
            if (roomsize % 3 == 1) {
                max = 12;
            } else if (roomsize % 3 == 2) {
                max = 8;
            }
            roomsize++;
            for (int i = 0; i < max; i++) {
                Row row1 = new Row();
                row1.setRoom(roomDao.findOne(j));
                row1.setDescription("description of the row");
                row1.setName(String.valueOf(alphabet[(i % 26)]));
                if (i > 0 && i % 4 == 0) {
                    price += 1;
                }
                row1.setPrice(price);

                rowDao.save(row1);
            }
        }

        price = 220;
        for (int j = 29; j < 35; j++) {

            for (int i = 0; i < 12; i++) {
                Row row1 = new Row();
                row1.setRoom(roomDao.findOne(j));
                row1.setDescription("description of the row");
                String name = "";
                for (int k = 0; k < (i / 25) + 1; k++) {
                    name += String.valueOf(alphabet[(i % 26)]);
                }
                row1.setName(name);

                if (i > 0 && i % 4 == 0) {
                    price += 10;
                }
                row1.setPrice(price);

                rowDao.save(row1);
            }
        }

        price = 70;
        for (int j = 35; j < 39; j++) {

            for (int i = 0; i < 12; i++) {
                Row row1 = new Row();
                row1.setRoom(roomDao.findOne(j));
                row1.setDescription("description of the row");
                String name = "";
                for (int k = 0; k < (i / 25) + 1; k++) {
                    name += String.valueOf(alphabet[(i % 26)]);
                }
                row1.setName(name);

                if (i > 0 && i % 10 == 0) {
                    price += 10;
                }
                row1.setPrice(price);

                rowDao.save(row1);
            }
        }

        Area area = new Area();
        area.setRoom(roomDao.findOne(1));
        area.setPrice(34);
        area.setTicketAmount(40);
        area.setAreaType(AreaType.VIP);

        areaDao.save(area);
        AreaType[] areaTypes = {AreaType.STANCE, AreaType.FREE_SEATING_CHOICE, AreaType.BARRIER_FREE, AreaType.VIP};
        double priceArea = 120.0;
        for (int j = 27; j < 29; j++) {
            price += 40;
            for (int i = 0; i < 4; i++) {
                Area area1 = new Area();
                priceArea += 10 * i;
                area1.setRoom(roomDao.findOne(j));
                area1.setPrice(priceArea);
                area1.setTicketAmount(800);
                if (i % 4 == 1) {
                    area1.setTicketAmount(200);
                } else if (i % 4 == 2) {
                    area1.setTicketAmount(20);
                } else if (i % 4 == 3) {
                    area1.setTicketAmount(90);
                }
                area1.setAreaType(areaTypes[i]);
                areaDao.save(area1);
            }
        }

        /**************************************/

        area = new Area();
        area.setRoom(roomDao.findOne(39));
        area.setPrice(50);
        area.setTicketAmount(200);
        area.setAreaType(AreaType.STANCE);
        areaDao.save(area);

        area = new Area();
        area.setRoom(roomDao.findOne(39));
        area.setPrice(30);
        area.setTicketAmount(12);
        area.setAreaType(AreaType.BARRIER_FREE);
        areaDao.save(area);

        /****************************************/
        area = new Area();
        area.setRoom(roomDao.findOne(40));
        area.setPrice(65);
        area.setTicketAmount(180);
        area.setAreaType(AreaType.STANCE);
        areaDao.save(area);

        area = new Area();
        area.setRoom(roomDao.findOne(40));
        area.setPrice(120);
        area.setTicketAmount(40);
        area.setAreaType(AreaType.VIP);
        areaDao.save(area);

        /***********************************************/
        area = new Area();
        area.setRoom(roomDao.findOne(41));
        area.setPrice(45.6);
        area.setTicketAmount(350);
        area.setAreaType(AreaType.STANCE);
        areaDao.save(area);

        /***********************************************/
        area = new Area();
        area.setRoom(roomDao.findOne(42));
        area.setPrice(45.6);
        area.setTicketAmount(350);
        area.setAreaType(AreaType.STANCE);
        areaDao.save(area);

        area = new Area();
        area.setRoom(roomDao.findOne(42));
        area.setPrice(30);
        area.setTicketAmount(12);
        area.setAreaType(AreaType.BARRIER_FREE);
        areaDao.save(area);

        area = new Area();
        area.setRoom(roomDao.findOne(42));
        area.setPrice(60);
        area.setTicketAmount(12);
        area.setAreaType(AreaType.FREE_SEATING_CHOICE);
        areaDao.save(area);

        /***********************************************/

        area = new Area();
        area.setRoom(roomDao.findOne(1));
        area.setPrice(50);
        area.setTicketAmount(40);
        area.setAreaType(AreaType.BARRIER_FREE);

        areaDao.save(area);


        /************************ automatic row and area generator ************************/

        double rowPrice = 0;
        for (int i = 43; i < 250; i++) {
            rowPrice = 20.8;
            for (int j = 0; j < 12; j++) {
                if(((j+1)%6) != 0) {
                    Row rowGenerated = new Row();
                    rowGenerated.setRoom(roomDao.findOne(i));
                    rowGenerated.setDescription("row" + i);
                    rowGenerated.setName(String.valueOf(alphabet[(j % 26)]));
                    rowGenerated.setPrice(rowPrice);
                    rowPrice += 5;
                    rowDao.save(rowGenerated);
                }
            }
        }

        for (int i = 250; i < 371; i++) {
            rowPrice = 15.5;
            for (int j = 0; j < 8; j++) {
                Row rowGenerated = new Row();
                rowGenerated.setRoom(roomDao.findOne(i));
                rowGenerated.setDescription("row" + i);
                rowGenerated.setName(String.valueOf(alphabet[(j % 26)]));
                rowGenerated.setPrice(rowPrice);
                rowPrice++;
                rowDao.save(rowGenerated);
            }
        }

        for (int i = 371; i < 470; i++) {
            for (int j = 0; j < 2; j++) {
                Area areaGenerated = new Area();
                areaGenerated.setPrice(55 + j*10);
                areaGenerated.setAreaType(areaTypes[j]);
                areaGenerated.setTicketAmount(300);
                if(j == 1){
                    areaGenerated.setAreaType(areaTypes[j+1]);
                    areaGenerated.setTicketAmount(50);
                }
                areaGenerated.setRoom(roomDao.findOne(i));

                areaDao.save(areaGenerated);
            }
        }
    }
}
