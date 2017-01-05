package at.ac.tuwien.inso.ticketline.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;

/**
 * This class can be used to access resource bundles.
 */
public class BundleManager {

    private static final List<Locale> supportedLocales = Arrays.asList(GERMAN, ENGLISH);

    private static final String BASENAME = "localization.ticketlineClient";
    private static final String EXCEPTION_BASENAME = "localization.ticketlineClientExceptions";

    private static Locale LOCALE = Locale.getDefault();

    private static final Map<String, ResourceBundle> BUNDLES = new HashMap<>();
    private static final Map<String, ResourceBundle> EXCEPTION_BUNDLES = new HashMap<>();

    static {
        supportedLocales.forEach(locale -> {
            BUNDLES.put(locale.getLanguage(), ResourceBundle.getBundle(BASENAME, locale, new UTF8Control()));
            EXCEPTION_BUNDLES.put(locale.getLanguage(), ResourceBundle.getBundle(EXCEPTION_BASENAME, locale, new UTF8Control()));
        });
    }

    /**
     * Gets the bundle for the current locale or if not set the default locale.
     *
     * @return the bundle
     */
    public static ResourceBundle getBundle() {
        return BUNDLES.getOrDefault(LOCALE.getLanguage(), BUNDLES.get(supportedLocales.get(0).getLanguage()));
    }

    /**
     * Gets the exception bundle for the current locale or if not set the default local.
     *
     * @return the exception bundle
     */
    public static ResourceBundle getExceptionBundle() {
        return EXCEPTION_BUNDLES.getOrDefault(LOCALE.getLanguage(), EXCEPTION_BUNDLES.get(supportedLocales.get(0).getLanguage()));
    }

    /**
     * Changes the locale.
     *
     * @param locale the locale
     */
    public static void changeLocale(Locale locale) {
        if (!supportedLocales.contains(locale)) {
        	throw new IllegalArgumentException("Locale not supported");
        }
        LOCALE = locale;
    }

    /**
     * Check if current locale is german
     * @return true if locale is german, els no
     */
    public static boolean isGerman() {
        if (LOCALE.equals("GERMAN")) {
            return true;
        }
        return false;
    }

    /**
     * Gets the supported locales.
     *
     * @return the supported locales
     */
    public static List<Locale> getSupportedLocales() {
        return supportedLocales;
    }

    /**
     * UTF-8 resource bundle loader
     */
    private static class UTF8Control extends ResourceBundle.Control {

    	/**
    	 * {@inheritDoc}
    	 */
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");
            ResourceBundle bundle = null;
            InputStream stream = null;
            if (reload) {
                URL url = loader.getResource(resourceName);
                if (url != null) {
                    URLConnection connection = url.openConnection();
                    if (connection != null) {
                        connection.setUseCaches(false);
                        stream = connection.getInputStream();
                    }
                }
            } else {
                stream = loader.getResourceAsStream(resourceName);
            }
            if (stream != null) {
                try {
                    bundle = new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
                } finally {
                    stream.close();
                }
            }
            return bundle;
        }
    }

}
