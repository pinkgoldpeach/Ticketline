package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.*;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Carla on 17/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
@Component
public class TicketGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketGenerator.class);

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private SeatDao seatDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private ShowDao showDao;

    @Autowired
    private ReservationDao reservationDao;

    @Override
    public void generate() {


        // ---------------------------
/*
        for (int i = 1; i < 15 ; i++) {
            Ticket ticket1 = new Ticket();
            ticket1.setDescription("Ticket description1");
            ticket1.setSeat(seatDao.findOne(i));
            ticket1.setPrice(seatDao.getPrice(seatDao.findOne(i)));
            ticket1.setShow(showDao.findOne(1));
            ticket1.setValid(true);
            ticketDao.save(ticket1);
        }

        for (int i = 501; i < 536 ; i++) {
            Ticket ticket1 = new Ticket();
            ticket1.setDescription("Ticket description1");
            ticket1.setSeat(seatDao.findOne(i));
            ticket1.setPrice(seatDao.getPrice(seatDao.findOne(i)));
            ticket1.setShow(showDao.findOne(4));
            ticket1.setValid(true);
            ticketDao.save(ticket1);
        }


        Ticket ticket2 = new Ticket();

        ticket2.setPrice(30.5);
        ticket2.setDescription("Ticket description1");
        ticket2.setArea(areaDao.findOne(1));
        ticket2.setShow(showDao.findOne(2));
        ticket2.setValid(true);
       // ticket.setReservation(reservationDao.findOne(1));
        ticketDao.save(ticket2);

        // ---------------------------

        Ticket ticket3 = new Ticket();

        ticket3.setPrice(60.5);
        ticket3.setDescription("Ticket description1");
        ticket3.setArea(areaDao.findOne(1));
        ticket3.setShow(showDao.findOne(2));
        ticket3.setValid(true);
        // ticket.setReservation(reservationDao.findOne(1));
        ticketDao.save(ticket3);

        // ---------------------------

        Ticket ticket4 = new Ticket();

        ticket4.setPrice(24.5);
        ticket4.setDescription("Ticket description1");
        ticket4.setArea(areaDao.findOne(1));
        ticket4.setShow(showDao.findOne(2));
        ticket4.setValid(false);
        // ticket.setReservation(reservationDao.findOne(1));
        ticketDao.save(ticket4);

        // ---------------------------
        for (int i = 0; i < 10; i++) {
            Ticket ticket6 = new Ticket();

            ticket6.setPrice(96.0);
            ticket6.setDescription("Ticket description 5");
            ticket6.setArea(areaDao.findOne(4));
            ticket6.setShow(showDao.findOne(7));
            ticket6.setValid(true);
            // ticket.setReservation(reservationDao.findOne(1));
            ticketDao.save(ticket6);

        }

        // ---------------------------
        for (int i = 0; i < 8; i++) {
            Ticket ticket7 = new Ticket();

            ticket7.setPrice(96.0);
            ticket7.setDescription("Ticket description 6");
            ticket7.setArea(areaDao.findOne(2));
            ticket7.setShow(showDao.findOne(3));
            ticket7.setValid(true);
            // ticket.setReservation(reservationDao.findOne(1));
            ticketDao.save(ticket7);

        }

        // ---------------------------
        for (int i = 0; i < 15; i++) {
            Ticket ticket8 = new Ticket();

            ticket8.setPrice(150.0);
            ticket8.setDescription("Ticket description7");
            ticket8.setArea(areaDao.findOne(5));
            ticket8.setShow(showDao.findOne(6));
            ticket8.setValid(true);
            // ticket.setReservation(reservationDao.findOne(1));
            ticketDao.save(ticket8);

        }*/
        /************************ automatic ticket generator ************************/
/*
        for (int i = 8; i < 100; i++) {
            Ticket ticket5 = new Ticket();
            ticket5.setPrice((50.0+i));
            ticket5.setDescription("description"+i);
            ticket5.setArea(areaDao.findOne(i));
            ticket5.setShow(showDao.findOne(i));
            ticket5.setValid(true);
            ticketDao.save(ticket5);

        }

    */}
}
