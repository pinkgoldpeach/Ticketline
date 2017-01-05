package at.ac.tuwien.inso.ticketline.server.rest.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import at.ac.tuwien.inso.ticketline.dto.MessageDto;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.models.dto.ResponseMessage;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

/**
 * This class creates and configures the Swagger Spring MVC plugin
 *
 */
@Configuration
@EnableSwagger
public class SwaggerConfig {

	@Autowired
	private SpringSwaggerConfig springSwaggerConfig;
	
	private ApiInfo info = new ApiInfo(
		"Ticketline REST API",
		"Description for the Ticketline REST services",
		null,
		null,
		null,
		null
	);

	/**
	 * Creates the Swagger Spring MVC plugin
	 * 
	 * @return the Swagger Spring MVC plugin
	 */
	@Bean
	public SwaggerSpringMvcPlugin createSwaggerSpringMvcPlugin(){
		List<ResponseMessage> globalResponseMessages = new ArrayList<ResponseMessage>();
		
		globalResponseMessages.add(new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An internal error occured in the server", MessageDto.class.getSimpleName()));
		globalResponseMessages.add(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Invalid request", MessageDto.class.getSimpleName()));
		globalResponseMessages.add(new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), "Unauthorized request, login first", MessageDto.class.getSimpleName()));
		globalResponseMessages.add(new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), "REGEX Validation failed", MessageDto.class.getSimpleName()));

		return new SwaggerSpringMvcPlugin(springSwaggerConfig)
			.apiInfo(info)
			.apiVersion("2.4")
			.globalResponseMessage(RequestMethod.GET, globalResponseMessages)
			.globalResponseMessage(RequestMethod.POST, globalResponseMessages)
			.pathProvider(new TicketlinePathProvider())
			.build();
	}
	


}
