package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.ac.tuwien.inso.ticketline.dao.NewsDao;
import at.ac.tuwien.inso.ticketline.model.News;

/**
 * This class generates data for news
 * 
 * @see at.ac.tuwien.inso.ticketline.model.News
 */
@Component
public class NewsGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsGenerator.class);

    @Autowired
    private NewsDao dao;

	/**
	 * {@inheritDoc}
	 */
    public void generate() {
        LOGGER.info("+++++ Generate News Data +++++");
        
        String crlf = System.getProperty("line.separator");
        
        // ---------------------------
        Date date_1 = GregorianCalendar.from(ZonedDateTime.now().minusDays(5)).getTime();
        String title_1 = "Neuer Guide";
        String newsText_1 = "Die neueste Ausgabe des \"The Hitchhiker's Guide to the Galaxy\" ist ab heute verfügbar. Aufgrund der Beliebtheit des Buches ist mit erhöhtem Andrang in allen Filialen zu rechnen. Bitte teilen Sie den seit letztem Jahr vor den Filialen campenden Fans mit, dass sie nicht in Panik ausbrechen müssen. In weiser Voraussicht hat die Geschäftsleilung 18 Milliarden Exemplare bestellt, sodass bis morgen nachmittag kein Engpass zu erwarten ist."
         + crlf + crlf + "gez. Arthur Dent," + crlf + "Leiter Verkauf, Marketing, Technik, Human Resources und einiger anderer Dinge, die niemanden interessieren";
        News n1 = new News(date_1, title_1, newsText_1);
        dao.save(n1);
        
        // ---------------------------
        Date date_2 = GregorianCalendar.from(ZonedDateTime.now().minusDays(4)).getTime();
        String title_2 = "Mitarbeitermotivation";
        String newsText_2 = "Die Geschäftsführung der Ticketline GmbH ist stets bemüht die Arbeitsbedingungen für ihre Mitarbeiter zu verbessern. Daher freuen wir uns Ihnen heute unseren neuen Mitarbeiter Marvin vorstellen zu dürfen. Marvin wird sich zukünftig um die Mitarbeitermotivation kümmern, sowie die Leitung der anonymen Selbsthilfegruppe für depressive Mitarbeiter übernehmen." + crlf + "Um die Wünsche und Anregungen der Mitarbeiter zu sammeln und umsetzen zu können, wurde bereits ein neuer Kommunikationskanal eingerichtet: Bitte schicken Sie Ihre Nachrichten an /dev/null. Wir kümmern uns dann umgehend um Ihr Anliegen und versprechen uns binnen 10 Jahren bei Ihnen zu melden." + crlf + crlf + "gez. Ford Prefect," + crlf + "Chief Executive for Nothing";
        News n2 = new News(date_2, title_2, newsText_2);
        dao.save(n2);
        
        // ---------------------------
        Date date_3 = GregorianCalendar.from(ZonedDateTime.now().minusDays(3)).getTime();
        String title_3 = "Hausverbot für Vogonen";
        String newsText_3 = "Bitte beachten Sie, dass Vogonen von heute bis zum Ende des Universums Hausverbot bei allen öffentlichen Veranstaltungen haben. Anlass ist die Massenpanik beim Konzert der Band \"Don't Panic\", die durch einen mitsingenden Vogonen ausgelöst wurde. Wir werden ewig um die beiden Luftballons trauern, die bei dieser Massenpanik geplatzt sind!" + crlf + crlf + "gez. Zaphod Beeblebrox," + crlf + "ehemaliger Präsident der Galaxis, jetziger CEO der Ticketline GmbH";
        News n3 = new News(date_3, title_3, newsText_3);
        dao.save(n3);
        
        // ---------------------------
        Date date_4 = GregorianCalendar.from(ZonedDateTime.now().minusDays(2)).getTime();
        String title_4 = "Neue Kooperation";
        String newsText_4 = "Wir freuen uns Ihnen mitteilen zu können, dass die Ticketline GmbH eine Kooperation mit dem Restaurant am Ende des Universums eingegangen ist. Mitarbeiter von Ticketline erhalten zukünftig einen Rabatt von 0,01 Prozent auf alle Speisen, Getränke und Handtücher, die zwischen 12:21 und 12:22 Uhr bestellt werden. Verwenden Sie bei der Zahlung bitte das Codewort \"42\", um in den Genuss dieses großzügigen Rabatts zu kommen." + crlf + "Bitte beachten Sie, dass die vorgeschriebene Mittagspause von 5,7 Minuten keinesfalls überschritten werden darf und wir auch weiterhin Ausreden wie \"Die Mittagspause hat so lange gedauert, weil das Essen erst gekommen ist, nachdem das Universum untergegangen ist\" nicht akzeptieren werden!" + crlf + crlf + "gez. Marvin," + crlf + "Chief Motivation Officer";
        News n4 = new News(date_4, title_4, newsText_4);
        dao.save(n4);
        
    }

}
