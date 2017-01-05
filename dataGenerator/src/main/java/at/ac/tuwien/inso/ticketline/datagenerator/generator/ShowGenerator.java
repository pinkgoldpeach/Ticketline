package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.PerformanceDao;
import at.ac.tuwien.inso.ticketline.dao.RoomDao;
import at.ac.tuwien.inso.ticketline.dao.ShowDao;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Show;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * Created by Carla on 08/05/2016.
 */
@Component
public class ShowGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowGenerator.class);

    @Autowired
    private ShowDao showDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private PerformanceDao performanceDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void generate() {
        LOGGER.info("+++++ Generate Show Data +++++");

        // ---------------------------

        boolean canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        Date date = getUtilDate("2016-07-26 20:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        Room room = roomDao.findOne(40);
        Performance performance = performanceDao.findOne(1);

        Show show = new Show(canceled, date, room, performance);
        showDao.save(show);

        // ---------------------------

        canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        date = getUtilDate("2016-07-28 20:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        room = roomDao.findOne(2);
        performance = performanceDao.findOne(2);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        // ---------------------------

        canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        date = getUtilDate("2016-09-26 21:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        room = roomDao.findOne(7);
        performance = performanceDao.findOne(3);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        // ---------------------------

        canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        date = getUtilDate("2016-07-12 20:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        room = roomDao.findOne(8);
        performance = performanceDao.findOne(4);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        // ---------------------------

        canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        date = getUtilDate("2016-06-30 20:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        room = roomDao.findOne(1);
        performance = performanceDao.findOne(4);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        // ---------------------------

        canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        date = getUtilDate("2016-07-25 20:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        room = roomDao.findOne(17);
        performance = performanceDao.findOne(5);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);



        // ---------------------------

        canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        date = getUtilDate("2016-07-26 20:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        room = roomDao.findOne(16);
        performance = performanceDao.findOne(5);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        /****************************************************/

        canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        date = getUtilDate("2016-08-25 20:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        room = roomDao.findOne(24);
        performance = performanceDao.findOne(4);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        /****************************************************/

        canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        date = getUtilDate("2016-05-30 20:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        room = roomDao.findOne(22);
        performance = performanceDao.findOne(5);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        /****************************************************/

        canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        date = getUtilDate("2016-06-01 19:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        room = roomDao.findOne(13);
        performance = performanceDao.findOne(6);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        /****************************************************/

        canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        date = getUtilDate("2016-06-24 19:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        room = roomDao.findOne(21);
        performance = performanceDao.findOne(7);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        /****************************************************/

        canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        date = getUtilDate("2016-06-25 19:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        room = roomDao.findOne(17);
        performance = performanceDao.findOne(7);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        /****************************************************/

        canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        date = getUtilDate("2016-06-25 15:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        room = roomDao.findOne(27);
        performance = performanceDao.findOne(12);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        /****************************************************/

        canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        date = getUtilDate("2016-07-25 19:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        room = roomDao.findOne(6);
        performance = performanceDao.findOne(8);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);


        /****************************************************/

        canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        date = getUtilDate("2016-06-12 19:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        room = roomDao.findOne(3);
        performance = performanceDao.findOne(9);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        /****************************************************/

        canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        date = getUtilDate("2016-06-15 18:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        room = roomDao.findOne(4);
        performance = performanceDao.findOne(10);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        /****************************************************/

        canceled = false;
        //Date date1 = GregorianCalendar.from(ZonedDateTime.now().plusDays(35)).getTime();
        date = getUtilDate("2016-06-11 21:00:00");
        //Timestamp date1 = Timestamp.valueOf("2016-07-26 20:00:00");
        room = roomDao.findOne(5);
        performance = performanceDao.findOne(11);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        /****************************************************/

        canceled = false;
        date = getUtilDate("2016-08-05 15:00:00");
        room = roomDao.findOne(28);
        performance = performanceDao.findOne(13);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        /****************************************************/

        canceled = false;
        date = getUtilDate("2016-08-12 15:00:00");
        room = roomDao.findOne(28);
        performance = performanceDao.findOne(14);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        /****************************************************/

        canceled = false;
        date = getUtilDate("2016-08-12 19:00:00");
        room = roomDao.findOne(30);
        performance = performanceDao.findOne(15);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        /****************************************************/

        canceled = false;
        date = getUtilDate("2016-06-22 18:30:00");
        room = roomDao.findOne(31);
        performance = performanceDao.findOne(16);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        /****************************************************/

        canceled = false;
        date = getUtilDate("2016-06-28 20:00:00");
        room = roomDao.findOne(32);
        performance = performanceDao.findOne(17);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        /****************************************************/

        canceled = false;
        date = getUtilDate("2016-06-17 18:00:00");
        room = roomDao.findOne(34);
        performance = performanceDao.findOne(18);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);
        /****************************************************/

        canceled = false;
        date = getUtilDate("2016-06-08 19:30:00");
        room = roomDao.findOne(35);
        performance = performanceDao.findOne(19);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);
        /****************************************************/

        canceled = false;
        date = getUtilDate("2016-06-27 20:00:00");
        room = roomDao.findOne(36);
        performance = performanceDao.findOne(20);

        show = new Show(canceled, date, room, performance);
        showDao.save(show);

        /************************ automatic show generator ************************/

        for(int i = 43; i < 371; i++) {
            boolean canceledGenerated = false;
            Date dateGenerated = getUtilDate("2016-"+"0"+((i%9)+1)+"-"+((i%27)+1)+" 20:00:00");
            Room roomGenerated = roomDao.findOne(i);

            Performance performanceGenerated = performanceDao.findOne((i%100)+21);
            int iterator = (i%100)+21;
            while ((performanceGenerated.getPerformanceType().name().equals("FESTIVAL") || performanceGenerated.getPerformanceType().name().equals("CONCERT"))){
                if(iterator > 120){
                    iterator = 21;
                }
                performanceGenerated = performanceDao.findOne(iterator);
                iterator++;
            }
            Show showGenerated = new Show(canceledGenerated, dateGenerated, roomGenerated, performanceGenerated);
            showDao.save(showGenerated);
        }

        for(int i = 371; i < 470; i++) {
            boolean canceledGenerated = false;
            Date dateGenerated = getUtilDate("2016-"+"0"+((i%9)+1)+"-"+((i%27)+1)+" 20:00:00");
            Room roomGenerated = roomDao.findOne(i);
            Performance performanceGenerated = performanceDao.findOne((i%100)+21);
            int iterator = (i%100)+21;
            while (!((performanceGenerated.getPerformanceType().name().equals("FESTIVAL") || performanceGenerated.getPerformanceType().name().equals("CONCERT")))){
                if(iterator > 120){
                    iterator = 21;
                }
                performanceGenerated = performanceDao.findOne(iterator);
                iterator++;
            }
            Show showGenerated = new Show(canceledGenerated, dateGenerated, roomGenerated, performanceGenerated);
            showDao.save(showGenerated);
        }



    }

    public Date getUtilDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        java.util.Date dateUtil = null;
        try {
            dateUtil = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateUtil;
    }

    public java.sql.Date getSQLDate(String yyyy_mm_dd){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        java.util.Date dateUtil = null;
        try {
            dateUtil = formatter.parse(yyyy_mm_dd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date date = new java.sql.Date(dateUtil.getTime());
        return date;
    }

}
