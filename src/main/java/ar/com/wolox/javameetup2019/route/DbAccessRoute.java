package ar.com.wolox.javameetup2019.route;

import ar.com.wolox.javameetup2019.exception.InvalidCuilException;
import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import ar.com.wolox.javameetup2019.helper.MessagesConstants;
import ar.com.wolox.javameetup2019.helper.PropertiesConstants;
import ar.com.wolox.javameetup2019.pojo.BodyInput;
import ar.com.wolox.javameetup2019.processor.BodyInputValidationsProcessor;
import ar.com.wolox.javameetup2019.processor.ErrorResponseProcessor;
import ar.com.wolox.javameetup2019.processor.GetClientByDocumentProcessor;
import ar.com.wolox.javameetup2019.processor.GetClientByNameProcessor;
import ar.com.wolox.javameetup2019.processor.OkResponseProcessor;
import ar.com.wolox.javameetup2019.processor.SaveClientProcesor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class DbAccessRoute extends RouteBuilder {

	@Autowired
	private ErrorResponseProcessor errorResponseProcessor;

	@Autowired
	private BodyInputValidationsProcessor bodyInputValidationsProcessor;

	@Autowired
	private OkResponseProcessor okResponseProcessor;

	@Autowired
	private SaveClientProcesor saveClientProcesor;

	@Autowired
	private GetClientByNameProcessor getClientByNameProcessor;

	@Autowired
	private GetClientByDocumentProcessor getClientByDocumentProcessor;

	@Override
	public void configure() throws Exception {

		onException(InvalidInputException.class, InvalidCuilException.class)
				.handled(true)
				.setProperty(PropertiesConstants.PROPERTY_CODE,
						simple("-4"))
				.setProperty(PropertiesConstants.PROPERTY_DETAIL,
						simple("${property.CamelExceptionCaught.message}"))
				.process(errorResponseProcessor)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.BAD_REQUEST.value()))
				.setHeader(Exchange.CONTENT_TYPE, constant(PropertiesConstants.VALUE_CHARSET));

		from("direct:get-client-name")
				.process(getClientByNameProcessor)
				.process(okResponseProcessor)
				.to("mock:result");

		from("direct:get-client-document")
				.process(getClientByDocumentProcessor)
				.process(okResponseProcessor)
				.to("mock:result");

		from("direct:save-client")
				.marshal().json(JsonLibrary.Jackson)
				.convertBodyTo(String.class)
				.unmarshal().json(JsonLibrary.Jackson, BodyInput.class)
				.onException(org.springframework.dao.DataIntegrityViolationException.class)
					.handled(true)
					.setProperty(PropertiesConstants.PROPERTY_DETAIL, simple(MessagesConstants.EXISTENT_DNI))
					.setProperty(PropertiesConstants.PROPERTY_CODE,
							simple(PropertiesConstants.ERROR_CODE_INCORRECT_DOCUMENT))
					.process(errorResponseProcessor)
					.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.INTERNAL_SERVER_ERROR.value()))
					.setHeader(Exchange.CONTENT_TYPE, constant(PropertiesConstants.VALUE_CHARSET))
				.end()
				.process(bodyInputValidationsProcessor)
				.process(saveClientProcesor)
				.process(okResponseProcessor)
				.to("mock:result");
	}
}
