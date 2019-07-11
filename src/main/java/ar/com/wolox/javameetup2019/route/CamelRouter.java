package ar.com.wolox.javameetup2019.route;

import ar.com.wolox.javameetup2019.exceptions.BodyNullException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

@Component
public class CamelRouter extends RouteBuilder {

	private static final String CHARSET = "application/json; charset=utf-8";
	private static final String BODY_ERROR_MESSAGE = "Body inexistente. Por favor cargue los datos necesarios para dar de alta al cliente. ";
	private static final String RESPONSE_OK = "OK";
	private static final String RESPONSE_ERROR = "Server error";
	private static final String INPUT_PARAM_ERROR = "Bad input param";

	@Override
	public void configure() throws Exception {
		restConfiguration()
				.apiContextPath("/api-docs")
				.apiProperty("api.title", "pom-people-query-adapter")
				.apiProperty("api.description",
						"REST API exposed by Springboot & Fuse compatible with RHOAR")
				.apiProperty("api.version", "1.0-SNAPSHOT")
				.apiProperty("cors", "true")
				.apiProperty("base.path", "/")
				.apiProperty("api.path", "/")
				.apiProperty("host", "")
				.apiContextRouteId("doc-api")
				.component("servlet")
				.bindingMode(RestBindingMode.json);

		/*onException(BodyNullException.class)
				.handled(true)
				.setProperty("detail", )
				.setProperty("code", )
				.process()*/

		rest("client")
				// GET
				.get("/name")
				.description("Busqueda de clientes por nombre")
				.param()
				.name("name")
				.description("Nombre por el que se quiere filtrar la busqueda")
				.required(true)
				.type(RestParamType.query)
				.endParam()
				.responseMessage().code(200).message(RESPONSE_OK).endResponseMessage()
				.responseMessage().code(400).message(INPUT_PARAM_ERROR).endResponseMessage()
				.responseMessage().code(500).message(RESPONSE_ERROR).endResponseMessage()
				.route()
				.to("direct:get-scoring")
				.endRest()
				// GET
				.get("/document")
				.description("Búsqueda de un cliente por documento")
				.param()
				.name("document")
				.description("Documento por el que se quiere filtrar la busqueda")
				.required(true)
				.type(RestParamType.query)
				.endParam()
				.responseMessage().code(200).message(RESPONSE_OK).endResponseMessage()
				.responseMessage().code(400).message(INPUT_PARAM_ERROR).endResponseMessage()
				.responseMessage().code(500).message(RESPONSE_ERROR).endResponseMessage()
				.route()
				.to("direct:get-client-document")
				.endRest()
				// POST save client
				.post()
				//.type(BodyGetClient.class)
				.description("Carga o registro de un cliente")
				.responseMessage().code(200).message(RESPONSE_OK).endResponseMessage()
				.responseMessage().code(400).message(INPUT_PARAM_ERROR).endResponseMessage()
				.responseMessage().code(500).message(RESPONSE_ERROR).endResponseMessage()
				//.responseModel(ErrorProcessor.class)
				.route()
				.choice()
				.when(body().isNull())
				.throwException(BodyNullException.class, BODY_ERROR_MESSAGE)
				.otherwise()
				.to("direct:save-client")
				.endChoice()
				.endRest();
	}
}
