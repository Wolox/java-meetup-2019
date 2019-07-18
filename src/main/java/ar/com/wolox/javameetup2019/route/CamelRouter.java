package ar.com.wolox.javameetup2019.route;

import ar.com.wolox.javameetup2019.exception.NullBodyException;
import ar.com.wolox.javameetup2019.exception.InvalidCuilException;
import ar.com.wolox.javameetup2019.exception.InvalidDocumentTypeException;
import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import ar.com.wolox.javameetup2019.helper.MessagesConstants;
import ar.com.wolox.javameetup2019.helper.PropertiesConstants;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CamelRouter extends RouteBuilder {

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
				.apiProperty("api.description", "AMD Client")
				.apiProperty("api.version", "1.0-SNAPSHOT")
				.apiProperty("cors", "true")
				.apiProperty("base.path", "/")
				.apiProperty("api.path", "/")
				.apiProperty("host", "")
				.apiContextRouteId("doc-api")
				.port("8080")
				.host("localhost")
				.component("servlet")
				.bindingMode(RestBindingMode.json);

		onException(NullBodyException.class)
				.handled(true)
				.setProperty(PropertiesConstants.PROPERTY_DETAIL, simple(MessagesConstants.EMPTY_BODY))
				.setProperty(PropertiesConstants.PROPERTY_CODE, simple(PropertiesConstants.ERROR_CODE_EMPTY_BODY))
				.process(errorResponseProcessor)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.INTERNAL_SERVER_ERROR.value()))
				.setHeader(Exchange.CONTENT_TYPE, constant(PropertiesConstants.VALUE_CHARSET));

		onException(InvalidDocumentTypeException.class)
				.handled(true)
				.setProperty(PropertiesConstants.PROPERTY_DETAIL, simple(MessagesConstants.INCORRECT_DOCUMENT_TYPE))
				.setProperty(PropertiesConstants.PROPERTY_CODE,
						simple(PropertiesConstants.ERROR_CODE_INCORRECT_DOCUMENT_TYPE))
				.process(errorResponseProcessor)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.BAD_REQUEST.value()))
				.setHeader(Exchange.CONTENT_TYPE, constant(PropertiesConstants.VALUE_CHARSET));

		onException(InvalidInputException.class, InvalidCuilException.class)
				.handled(true)
				.setProperty(PropertiesConstants.PROPERTY_CODE, simple("-4"))
				.setProperty(PropertiesConstants.PROPERTY_DETAIL,
						simple("${property.CamelExceptionCaught.message}"))
				.process(errorResponseProcessor)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.BAD_REQUEST.value()))
				.setHeader(Exchange.CONTENT_TYPE, constant(PropertiesConstants.VALUE_CHARSET));

		rest("meetup2019/client")
				// GET
				.get("/name")
				.description("Search client by name")
				.param()
				.name(PropertiesConstants.PROPERTY_NAME)
				.description("Name to filter the search")
				.required(true)
				.type(RestParamType.query)
				.endParam()
				.responseMessage()
					.code(HttpStatus.OK.value())
					.message(MessagesConstants.RESPONSE_OK)
					.responseModel(StandardResponse.class)
					.endResponseMessage()
				.responseMessage()
					.code(HttpStatus.BAD_REQUEST.value())
					.message(MessagesConstants.RESPONSE_BAD_REQUEST)
					.endResponseMessage()
				.responseMessage()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message(MessagesConstants.RESPONSE_INTERNAL_ERROR)
					.responseModel(ErrorResponseProcessor.class)
					.endResponseMessage()
				.route()
				.process(setMetaPropertiesProcessor)
				.to("direct:get-client-name")
				.endRest()

				// GET
				.get("/document")
				.description("Search client by document")
				.param()
				.name(PropertiesConstants.PROPERTY_DOCUMENT_TYPE)
				.description("Document type to filter the search")
				.required(true)
				.type(RestParamType.query)
				.endParam()
				.param()
				.name(PropertiesConstants.PROPERTY_DOCUMENT_NUMBER)
				.description("Document to filter the search")
				.required(true)
				.type(RestParamType.query)
				.endParam()
				.responseMessage()
					.code(HttpStatus.OK.value())
					.message(MessagesConstants.RESPONSE_OK)
					.responseModel(StandardResponse.class)
					.endResponseMessage()
				.responseMessage()
					.code(HttpStatus.BAD_REQUEST.value())
					.message(MessagesConstants.RESPONSE_BAD_REQUEST)
					.endResponseMessage()
				.responseMessage()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message(MessagesConstants.RESPONSE_INTERNAL_ERROR)
					.responseModel(ErrorResponseProcessor.class)
					.endResponseMessage()
				.route()
				.process(setMetaPropertiesProcessor)
				.process(setTypePropertyProcessor)
				.choice()
					.when(exchangeProperty(PropertiesConstants.PROPERTY_DOCUMENT_TYPE)
							.isEqualTo(PropertiesConstants.VALUE_DOCUMENT_TYPE_DNI))
					.process(setDniNumberProcessor)
					.to("direct:get-client-document")
				.otherwise()
					.when(exchangeProperty(PropertiesConstants.PROPERTY_DOCUMENT_TYPE)
							.isEqualTo(PropertiesConstants.VALUE_DOCUMENT_TYPE_CUIL))
					.process(setCuilNumberProcessor)
					.to("direct:get-client-document")
				.otherwise()
					.throwException(InvalidDocumentTypeException.class, MessagesConstants.INCORRECT_DOCUMENT_TYPE)
				.endChoice()
				.endRest()

				// POST
				.post()
				.type(BodyInput.class)
				.description("Add client operation")
				.responseMessage()
					.code(HttpStatus.OK.value())
					.message(MessagesConstants.RESPONSE_OK)
					.responseModel(StandardResponse.class)
					.endResponseMessage()
				.responseMessage()
					.code(HttpStatus.BAD_REQUEST.value())
					.message(MessagesConstants.RESPONSE_BAD_REQUEST)
					.endResponseMessage()
				.responseMessage()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message(MessagesConstants.RESPONSE_INTERNAL_ERROR)
					.responseModel(ErrorResponseProcessor.class)
					.endResponseMessage()
				.route()
				.choice()
					.when(body().isNull())
					.throwException(NullBodyException.class, MessagesConstants.EMPTY_BODY)
				.otherwise()
					.process(setMetaPropertiesProcessor)
					.to("direct:save-client")
				.endChoice()
				.endRest();
	}
}
