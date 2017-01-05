package at.ac.tuwien.inso.ticketline.server.rest.swagger;

import com.mangofactory.swagger.paths.SwaggerPathProvider;

/**
 * This class provides the base paths for the Swagger API documentation
 */
public class TicketlinePathProvider extends SwaggerPathProvider {
	  public static final String ROOT = "/";
	  public static final String APP_PATH = "/ticketline/service";

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String applicationPath() {
		return APP_PATH;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getDocumentationPath() {
		return ROOT;
	}
}
