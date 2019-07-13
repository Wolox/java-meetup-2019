package ar.com.wolox.javameetup2019.route;

import ar.com.wolox.javameetup2019.helpers.Constants;
import ar.com.wolox.javameetup2019.pojo.Request;
import ar.com.wolox.javameetup2019.pojo.Response;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RestConfigurationRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		restConfiguration()
				.apiContextPath("api-docs")
				.apiProperty("api.title", "Java Meetup")
				.apiProperty("api.description", "Demo project")
				.apiProperty("api.version", "1.0-SNAPSHOT")
				.apiProperty("cors", "true")
				.apiProperty("base.path", "/")
				.apiProperty("api.path", "/")
				.apiProperty("host", "")
				.apiContextRouteId("doc-api")
				.bindingMode(RestBindingMode.json);

		rest(Constants.PROCESS_TEXT)
				.post()
				.description("This endpoint receives a JSON body with a text to analyze (in "
						+ "Spanish), scans it for spelling mistakes, corrects them and "
						+ "provides the possibility to translate the result to jerigonza.")

				.type(Request.class)

				.param()
				.type(RestParamType.header)
				.name("convert")
				.description("If it's true, it converts the resulting text in jerigonza. "
						+ "If false or no value is provided, it returns the text with no "
						+ "processing. ")
				.allowableValues("true", "false")
				.required(false)
				.endParam()

				.responseMessage()
				.message("Success!")
				.code(HttpStatus.OK.value())
				.endResponseMessage()

				.responseMessage()
				.message("The request didn't have the correct structure.")
				.code(HttpStatus.BAD_REQUEST.value())
				.endResponseMessage()

				.responseMessage()
				.message("The server is not responding correctly.")
				.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.endResponseMessage()

				.outType(Response.class)

				.to(Constants.VALIDATE_TEXT);


		from(Constants.VALIDATE_TEXT)
				// TODO
				.to(Constants.RESULT_ENDPOINT);

	}
}
