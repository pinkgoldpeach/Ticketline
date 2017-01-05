package at.ac.tuwien.inso.ticketline.datagenerator;

import at.ac.tuwien.inso.ticketline.datagenerator.generator.*;

import at.ac.tuwien.inso.ticketline.model.Seat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Main class of the Data Generator
 */
@Component
public class DataGeneratorMain implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataGeneratorMain.class);
    
    private ApplicationContext context;

    /**
     * The main method to launch the data generator.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring/datagenerator-context.xml");
        DataGeneratorMain generator = context.getBean(DataGeneratorMain.class);
        generator.generateData();
        context.close();
    }
    
    /**
     * Generates the data.
     * It calls the {@link at.ac.tuwien.inso.ticketline.datagenerator.generator.DataGenerator#generate()} method
     */
    public void generateData() {
        LOGGER.info("---------- START DATA GENERATION ----------");
        
        context.getBean(EmployeeGenerator.class).generate();
        context.getBean(NewsGenerator.class).generate();
        context.getBean(CustomerGenerator.class).generate();

        context.getBean(PerformanceGenerator.class).generate();
        context.getBean(ArtistGenerator.class).generate();
        context.getBean(LocationGenerator.class).generate();

        context.getBean(RoomGenerator.class).generate();
        context.getBean(ParticipationGenerator.class).generate();

        context.getBean(ShowGenerator.class).generate();

        context.getBean(RowAndAreaGenerator.class).generate();
        context.getBean(SeatGenerator.class).generate();

        context.getBean(ReservationGenerator.class).generate();
        context.getBean(TicketGenerator.class).generate();

        context.getBean(ReceiptGenerator.class).generate();
        context.getBean(ReceiptEntryGenerator.class).generate();

        LOGGER.info("---------- DATA GENERATION COMPLETE ----------");
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

}
