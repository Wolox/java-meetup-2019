package ar.com.wolox.javameetup2019.route;

import ar.com.wolox.javameetup2019.model.Product;
import ar.com.wolox.javameetup2019.processor.JerigonzaProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


@Component
public class SimpleCamelRoute extends RouteBuilder {

	private static final String A = "mock:result";

	@Autowired
	private Environment environment;

	@Autowired
	private JerigonzaProcessor jerigonzaProcessor;

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
				.component("servlet")
				.bindingMode(RestBindingMode.json);

		rest("products")
				.get("{id}")
				.description("Obtiene los datos de un producto")

				.param()
				.name("id")
				.description("Id del producto")
				.required(true)
				.type(RestParamType.path)
				.endParam()

				.responseMessage()
				.code(HttpStatus.OK.value())
				.message("Successful")
				.endResponseMessage()

				.responseMessage()
				.code(HttpStatus.BAD_REQUEST.value())
				.message("The request didn't have the correct structure")
				.endResponseMessage()

				.responseMessage()
				.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message("The server is not responding correctly")
				.endResponseMessage()

				.outType(Product.class)

				.route()
				.to(A);

		rest()
				.get("/hello").to("direct:hello")
				.get("/bye").to("direct:bye")
				.get("/test-environment").to("direct:test-environment");

		from("direct:hello")
				.transform().constant("Hello World!");

		from("direct:bye")
				.transform().constant("Bye World");

		from("direct:test-environment")
				.transform().constant("Environment: " + environment.getProperty("message"));


	}
}



