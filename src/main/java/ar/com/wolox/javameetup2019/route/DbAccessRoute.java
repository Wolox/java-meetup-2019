package ar.com.wolox.javameetup2019.route;

import ar.com.wolox.javameetup2019.exceptions.InvalidCuilException;
import ar.com.wolox.javameetup2019.exceptions.InvalidInputException;
import ar.com.wolox.javameetup2019.pojo.BodyInput;
import ar.com.wolox.javameetup2019.processor.BodyInputValidationsProcessor;
import ar.com.wolox.javameetup2019.processor.ErrorProcessor;
import ar.com.wolox.javameetup2019.processor.GetClientByDocumentProcessor;
import ar.com.wolox.javameetup2019.processor.GetClientByNameProcessor;
import ar.com.wolox.javameetup2019.processor.OkResponseProcessor;
import ar.com.wolox.javameetup2019.processor.SaveClientProcesor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbAccessRoute extends RouteBuilder {

	private static final String CHARSET = "application/json; charset=utf-8";
	private static final String DOCUMENT_ERROR_MESSAGE = "El documento ingresado ya existe.";
	private static final String DOCUMENT_ERROR_CODE = "-2";

	private static final String CODE = "code";
	private static final String DETAIL = "detail";

	@Autowired
	private ErrorProcessor errorProcessor;

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
				.setProperty(CODE, simple("-4"))
				.setProperty(DETAIL, simple("${property.CamelExceptionCaught.message}"))
				.process(errorProcessor)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
				.setHeader(Exchange.CONTENT_TYPE, constant(400));

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
					.setProperty(DETAIL, simple(DOCUMENT_ERROR_MESSAGE))
					.setProperty(CODE, simple(DOCUMENT_ERROR_CODE))
					.process(errorProcessor)
					.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
					.setHeader(Exchange.CONTENT_TYPE, constant(CHARSET))
				.end()
				.process(bodyInputValidationsProcessor)
				.process(saveClientProcesor)
				.process(okResponseProcessor)
				.to("mock:result");
	}
}
