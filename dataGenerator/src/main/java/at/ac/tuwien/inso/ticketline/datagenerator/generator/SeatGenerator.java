package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.RowDao;
import at.ac.tuwien.inso.ticketline.dao.SeatDao;
import at.ac.tuwien.inso.ticketline.model.Row;
import at.ac.tuwien.inso.ticketline.model.Seat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Carla on 15/05/2016.
 */
@Component
public class SeatGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeatGenerator.class);

    @Autowired
    private SeatDao dao;

    @Autowired
    private RowDao rowDao;

    @Override
    public void generate() {
        LOGGER.info("+++++ Generate Seat Data +++++");

        Seat seat = new Seat();

        /*************************************************/
        for (int i = 1; i < 13; i++) {
            Row row = rowDao.findOne(i);
            for (int j = 1; j < 16; j++) {
                if((j % 8 != 0) && (i % 7 != 0)) {
                    seat = new Seat();
                    seat.setName(row.getName());
                    seat.setOrder(j);
                    seat.setDescription("Comfy seat");
                    seat.setRow(row);
                    dao.save(seat);
                }
            }
        }
        dao.delete(1);
        dao.delete(2);
        dao.delete(3);
        dao.delete(4);
        dao.delete(5);
        dao.delete(10);
        dao.delete(11);
        dao.delete(12);
        dao.delete(13);
        dao.delete(14);

        dao.delete(15);
        dao.delete(16);
        dao.delete(17);
        dao.delete(26);
        dao.delete(27);
        dao.delete(28);

        dao.delete(29);
        dao.delete(30);
        dao.delete(41);
        dao.delete(42);

        dao.delete(43);
        dao.delete(56);
        /*************************************************/
        for (int i = 13; i < 25; i++) {
            Row row = rowDao.findOne(i);
            for (int j = 1; j < 16; j++) {
                if(i == 13 && j == 1 || i == 13 && j == 15) {
                    continue;
                }
                    seat = new Seat();
                    seat.setName(row.getName());
                    seat.setOrder(j);
                    seat.setDescription("Deluxe seat");
                    seat.setRow(row);
                    dao.save(seat);
                }

        }

        /*************************************************/
        for (int i = 25; i < 59; i++) {
            Row row = rowDao.findOne(i);
            for (int j = 1; j < 19; j++) {
                seat = new Seat();
                seat.setName(row.getName());
                seat.setOrder(j);
                seat.setDescription("Comfy seat");
                seat.setRow(row);
                dao.save(seat);
            }
        }
        /*************************************************/
        for (int i = 59; i < 90; i++) {
            Row row = rowDao.findOne(i);
            for (int j = 1; j < 10; j++) {
                if(j%5 != 0) {
                    seat = new Seat();
                    seat.setName(row.getName());
                    seat.setOrder(j);
                    seat.setDescription("Comfy cinema seat");
                    seat.setRow(row);
                    dao.save(seat);
                }
            }
        }
        /*************************************************/
        for (int i = 90; i < 266; i++) {
            Row row = rowDao.findOne(i);
            for (int j = 1; j < 18; j++) {
                if(j % 9 != 0) {
                    seat = new Seat();
                    seat.setName(row.getName());
                    seat.setOrder(j);
                    seat.setDescription("Deluxe opera seat");
                    seat.setRow(row);
                    dao.save(seat);
                }
            }
        }

        /*************************************************/
        for (int i = 266; i < 302; i++) {
            Row row = rowDao.findOne(i);
            for (int j = 1; j < 20; j++) {
                if(j % 5 != 0) {
                    seat = new Seat();
                    seat.setName(row.getName());
                    seat.setOrder(j);
                    seat.setDescription("Deluxe opera seat");
                    seat.setRow(row);
                    dao.save(seat);
                }
            }
        }

        /*************************************************/
        for (int i = 302; i < 350; i++) {
            Row row = rowDao.findOne(i);
            for (int j = 1; j < 18; j++) {
                if(j % 9 != 0) {
                    seat = new Seat();
                    seat.setName(row.getName());
                    seat.setOrder(j);
                    seat.setDescription("Deluxe theater seat");
                    seat.setRow(row);
                    dao.save(seat);
                }
            }
        }

        /************************ automatic seat generator ************************/

        for (int i = 350; i < 3160; i++) {
            Row row = rowDao.findOne(i);
            for (int j = 1; j < 18; j++) {
                if(j % 9 != 0) {
                    seat = new Seat();
                    seat.setName(row.getName());
                    seat.setOrder(j);
                    seat.setDescription("Comfy seat");
                    seat.setRow(row);
                    dao.save(seat);
                }
            }
        }

        for (int i = 3160; i < 3399; i++) {
            Row row = rowDao.findOne(i);
            for (int j = 1; j < 8; j++) {
                    seat = new Seat();
                    seat.setName(row.getName());
                    seat.setOrder(j);
                    seat.setDescription("Deluxe seat");
                    seat.setRow(row);
                    dao.save(seat);
                }
            }
        }

}
