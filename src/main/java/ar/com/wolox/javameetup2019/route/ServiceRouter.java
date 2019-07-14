package ar.com.wolox.javameetup2019.route;

import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import ar.com.wolox.javameetup2019.helpers.CamelConstants;
import ar.com.wolox.javameetup2019.helpers.ErrorConstants;
import ar.com.wolox.javameetup2019.helpers.LogConstants;
import ar.com.wolox.javameetup2019.pojo.service.ServiceResponse;
import ar.com.wolox.javameetup2019.processor.ErrorProcessor;
import ar.com.wolox.javameetup2019.processor.InputToServiceProcessor;
import ar.com.wolox.javameetup2019.processor.JerigonzaProcessor;
import ar.com.wolox.javameetup2019.processor.ResponseProcessor;
import ar.com.wolox.javameetup2019.processor.ServiceResponseProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
class ServiceRouter extends RouteBuilder {

	@Autowired
	Environment environment;

	@Value(value = "${service-url}")
	private String serviceUrl;

	@Autowired
	private InputToServiceProcessor inputToServiceProcessor;

	@Autowired
	private ServiceResponseProcessor serviceResponseProcessor;

	@Autowired
	private JerigonzaProcessor jerigonzaProcessor;

	@Autowired
	private ErrorProcessor errorProcessor;

	@Autowired
	private ResponseProcessor responseProcessor;

	private String getServiceUrl(String text) {
		return String.format("%s?%s=%s&%s=%s", serviceUrl, CamelConstants.HEADER_TEXT, text,
				CamelConstants.HEADER_LANG, CamelConstants.LANG_ES);
	}

	@Override
	public void configure() throws Exception {

		onException(Exception.class)
				.handled(true)
				.setProperty(CamelConstants.CODE, constant(ErrorConstants.SERVER_ERROR_CODE))
				.setProperty(CamelConstants.DETAIL,
						simple(ErrorConstants.SERVER_ERROR_DETAIL))
				.process(errorProcessor)
				.setHeader(Exchange.HTTP_RESPONSE_CODE,
						constant(HttpStatus.SC_INTERNAL_SERVER_ERROR))
				.log(LoggingLevel.ERROR, String.format("${property.%s}", CamelConstants.DETAIL));

		onException(InvalidInputException.class)
				.handled(true)
				.setProperty(CamelConstants.DETAIL,
						simple("${property.CamelExceptionCaught.message}"))
				.process(errorProcessor)
				.setHeader(Exchange.HTTP_RESPONSE_CODE,
						constant(HttpStatus.SC_BAD_REQUEST))
				.log(LoggingLevel.ERROR, String.format("${property.%s}", CamelConstants.DETAIL));

		from(CamelConstants.VALIDATE_TEXT)
				.log(LoggingLevel.INFO, LogConstants.PROCESSING_INPUT)
				.process(inputToServiceProcessor)
				.log(LoggingLevel.INFO, LogConstants.EXTERNAL_SERVICE)
				.toD(getServiceUrl(String.format("${property.%s}", CamelConstants.HEADER_TEXT)))
				.unmarshal().json(JsonLibrary.Jackson, ServiceResponse.class)
				.log(LoggingLevel.INFO, LogConstants.PROCESSING_RESPONSE)
				.process(serviceResponseProcessor)
				.choice()
				.when(exchangeProperty(CamelConstants.HEADER_CONVERT).isEqualTo(true))
				.log(LoggingLevel.INFO, LogConstants.JERIGONZA)
				.process(jerigonzaProcessor)
				.end()
				.process(responseProcessor)
				.log(LoggingLevel.INFO, LogConstants.SUCCESS)
				.to(CamelConstants.RESULT_ENDPOINT);

	}
}
