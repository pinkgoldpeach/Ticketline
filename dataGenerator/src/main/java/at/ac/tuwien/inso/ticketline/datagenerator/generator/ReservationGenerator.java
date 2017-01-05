package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.CustomerDao;
import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.dao.ReservationDao;
import at.ac.tuwien.inso.ticketline.model.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Carla on 17/05/2016.
 */
@Component
public class ReservationGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationGenerator.class);

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public void generate() {
        /*Reservation reservation = new Reservation();
        reservation.setCustomer(customerDao.findOne(1));
        reservation.setEmployee(employeeDao.findOne(1));
        reservation.setReservationNumber("ABC123");

        reservationDao.save(reservation);*/
    }
}
