package ar.com.wolox.javameetup2019.route;

import ar.com.wolox.javameetup2019.exception.NullBodyException;
import ar.com.wolox.javameetup2019.exception.InvalidCuilException;
import ar.com.wolox.javameetup2019.exception.InvalidDocumentTypeException;
import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import ar.com.wolox.javameetup2019.pojo.BodyInput;
import ar.com.wolox.javameetup2019.pojo.StandardResponse;
import ar.com.wolox.javameetup2019.processor.ErrorResponseProcessor;
import ar.com.wolox.javameetup2019.processor.SetCuilNumberProcessor;
import ar.com.wolox.javameetup2019.processor.SetDniNumberProcessor;
import ar.com.wolox.javameetup2019.processor.SetMetaPropertiesProcessor;
import ar.com.wolox.javameetup2019.processor.SetTypePropertyProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamelRouter extends RouteBuilder {

	private static final String CHARSET = "application/json; charset=utf-8";
	private static final String BODY_ERROR_MESSAGE = "Body inexistente. Por favor cargue los datos necesarios para dar de alta al cliente. ";
	private static final String BODY_ERROR_CODE = "-1";
	private static final String DOCUMENT_TYPE_ERROR_MESSAGE = "Tipo de documento invalido. La busqueda se filtra por dni o cuil.";
	private static final String DOCUMENT_TYPE_ERROR_CODE = "-3";
	private static final String RESPONSE_OK = "OK";
	private static final String RESPONSE_ERROR = "Internal Server error";
	private static final String BAD_REQUEST = "Bad request";
	private static final String CODE = "code";
	private static final String DETAIL = "detail";

	@Autowired
	private ErrorResponseProcessor errorResponseProcessor;

	@Autowired
	private SetMetaPropertiesProcessor setMetaPropertiesProcessor;

	@Autowired
	private SetCuilNumberProcessor setCuilNumberProcessor;

	@Autowired
	private SetTypePropertyProcessor setTypePropertyProcessor;

	@Autowired
	private SetDniNumberProcessor setDniNumberProcessor;

	@Override
	public void configure() throws Exception {
		restConfiguration()
				.apiContextPath("/api-docs")
				.apiProperty("api.title", "java-meetup-2019")
				.apiProperty("api.description", "ABM de un cliente")
				.apiProperty("api.version", "1.0-SNAPSHOT")
				.apiProperty("cors", "true")
				.apiProperty("base.path", "/")
				.apiProperty("api.path", "/")
				.apiProperty("host", "")
				.apiContextRouteId("doc-api")
				.port("8085")
				.host("localhost")
				.component("servlet")
				.bindingMode(RestBindingMode.json);

		onException(NullBodyException.class)
				.handled(true)
				.setProperty(DETAIL, simple(BODY_ERROR_MESSAGE))
				.setProperty(CODE, simple(BODY_ERROR_CODE))
				.process(errorResponseProcessor)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
				.setHeader(Exchange.CONTENT_TYPE, constant(CHARSET));

		onException(InvalidDocumentTypeException.class)
				.handled(true)
				.setProperty(DETAIL, simple(DOCUMENT_TYPE_ERROR_MESSAGE))
				.setProperty(CODE, simple(DOCUMENT_TYPE_ERROR_CODE))
				.process(errorResponseProcessor)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
				.setHeader(Exchange.CONTENT_TYPE, constant(CHARSET));

		onException(InvalidInputException.class, InvalidCuilException.class)
				.handled(true)
				.setProperty(CODE, simple("-4"))
				.setProperty(DETAIL, simple("${property.CamelExceptionCaught.message}"))
				.process(errorResponseProcessor)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
				.setHeader(Exchange.CONTENT_TYPE, constant(CHARSET));

		rest("meetup2019/client")
				// GET
				.get("/name")
				.description("Busqueda de clientes por nombre")
				.param()
				.name("name")
				.description("Nombre por el que se quiere filtrar la busqueda")
				.required(true)
				.type(RestParamType.query)
				.endParam()
				.responseMessage().code(200).message(RESPONSE_OK)
				.responseModel(StandardResponse.class).endResponseMessage()
				.responseMessage().code(400).message(BAD_REQUEST).endResponseMessage()
				.responseMessage().code(500).message(RESPONSE_ERROR)
				.responseModel(ErrorResponseProcessor.class).endResponseMessage()
				.route()
				.process(setMetaPropertiesProcessor)
				.to("direct:get-client-name")
				.endRest()

				// GET
				.get("/document")
				.description("Búsqueda de un cliente por documento")
				.param()
				.name("document_type")
				.description("Tipo del documento que se ingresara para filtrar la busqueda")
				.required(true)
				.type(RestParamType.query)
				.endParam()
				.param()
				.name("document_number")
				.description("Documento por el que se quiere filtrar la busqueda")
				.required(true)
				.type(RestParamType.query)
				.endParam()
				.responseMessage().code(200).message(RESPONSE_OK)
				.responseModel(StandardResponse.class).endResponseMessage()
				.responseMessage().code(400).message(BAD_REQUEST).endResponseMessage()
				.responseMessage().code(500).message(RESPONSE_ERROR)
				.responseModel(ErrorResponseProcessor.class).endResponseMessage()
				.route()
				.process(setMetaPropertiesProcessor)
				.process(setTypePropertyProcessor)
				.choice()
					.when(exchangeProperty("type").isEqualTo("dni"))
					.process(setDniNumberProcessor)
					.to("direct:get-client-document")
				.otherwise()
					.when(exchangeProperty("type").isEqualTo("cuil"))
					.process(setCuilNumberProcessor)
					.to("direct:get-client-document")
				.otherwise()
					.throwException(InvalidDocumentTypeException.class, DOCUMENT_TYPE_ERROR_MESSAGE)
				.endChoice()
				.endRest()

				// POST
				.post()
				.type(BodyInput.class)
				.description("Carga o registro de un cliente")
				.responseMessage().code(200).message(RESPONSE_OK)
				.responseModel(StandardResponse.class).endResponseMessage()
				.responseMessage().code(400).message(BAD_REQUEST).endResponseMessage()
				.responseMessage().code(500).message(RESPONSE_ERROR)
				.responseModel(ErrorResponseProcessor.class).endResponseMessage()
				.route()
				.choice()
					.when(body().isNull())
					.throwException(NullBodyException.class, BODY_ERROR_MESSAGE)
				.otherwise()
					.process(setMetaPropertiesProcessor)
					.to("direct:save-client")
				.endChoice()
				.endRest();
	}
}
