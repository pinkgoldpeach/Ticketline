package at.ac.tuwien.inso.ticketline.client.util;

import at.ac.tuwien.inso.ticketline.client.gui.controller.NewEditCustomerController;
import javafx.fxml.FXMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * Bridge between Spring and JavaFX which allows to inject Spring beans in JavaFX controller
 * <p>
 * Based on http://www.javacodegeeks.com/2013/03/javafx-2-with-spring.html
 */
@Component
public class SpringFxmlLoader implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringFxmlLoader.class);

    private ApplicationContext applicationContext;

    /**
     * Generate fxml loader based on the given URL.
     *
     * @param url the url
     * @return the FXML loader
     */
    private FXMLLoader generateFXMLLoader(String url) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(SpringFxmlLoader.class.getResource(url));
        fxmlLoader.setResources(BundleManager.getBundle());
        fxmlLoader.setControllerFactory(clazz -> {
            LOGGER.debug("Retrieving Spring bean for class {}", clazz.getName());
            return applicationContext.getBean(clazz);
        });
        return fxmlLoader;
    }

    /**
     * Loads object hierarchy from the FXML document given in the URL.
     *
     * @param url the url
     * @return the object
     */
    public Object load(String url) {
        try (InputStream fxmlStream = SpringFxmlLoader.class.getResourceAsStream(url)) {
            return generateFXMLLoader(url).load(fxmlStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads and wraps an object hierarchy.
     *
     * @param url the url
     * @return the load wrapper
     */
    public LoadWrapper loadAndWrap(String url) {
        try (InputStream fxmlStream = SpringFxmlLoader.class.getResourceAsStream(url)) {
            FXMLLoader fxmlLoader = generateFXMLLoader(url);
            Object loadedObject = fxmlLoader.load(fxmlStream);
            return new LoadWrapper(fxmlLoader.getController(), loadedObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**
     * The Class LoadWrapper.
     */
    public final class LoadWrapper {

        private final Object controller;
        private final Object loadedObject;

        /**
         * Instantiates a new load wrapper.
         *
         * @param controller the controller
         * @param loadedObject the loaded object
         */
        public LoadWrapper(Object controller, Object loadedObject) {
            this.controller = controller;
            this.loadedObject = loadedObject;
        }

        /**
         * Gets the controller.
         *
         * @return the controller
         */
        public Object getController() {
            return controller;
        }

        /**
         * Gets the loaded object.
         *
         * @return the loaded object
         */
        public Object getLoadedObject() {
            return loadedObject;
        }

    }

}