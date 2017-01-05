package at.ac.tuwien.inso.ticketline.datagenerator.generator;

/**
 * This is a common interface for all data generators
 */
public interface DataGenerator {
    
    /**
     * Generate data and saves it in the database.
     */
    public void generate();
}
