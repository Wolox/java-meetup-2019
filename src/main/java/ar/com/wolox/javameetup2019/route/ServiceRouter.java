package ar.com.wolox.javameetup2019.route;

import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import ar.com.wolox.javameetup2019.helpers.CamelConstants;
import ar.com.wolox.javameetup2019.helpers.ErrorConstants;
import ar.com.wolox.javameetup2019.processor.ErrorProcessor;
import ar.com.wolox.javameetup2019.processor.InputToServiceProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
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
	private ErrorProcessor errorProcessor;

	@Override
	public void configure() throws Exception {

		onException(InvalidInputException.class)
				.handled(true)
				.setHeader(CamelConstants.CODE, constant(ErrorConstants.INVALID_INPUT_CODE))
				.setHeader(CamelConstants.DETAIL,
						simple("${property.CamelExceptionCaught.message}"))
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.BAD_REQUEST.value()))
				.process(errorProcessor);

		from(CamelConstants.VALIDATE_TEXT)
				.process(inputToServiceProcessor)
				.to(serviceUrl)
				.process(inputToServiceProcessor)
				.to(CamelConstants.RESULT_ENDPOINT);

	}
}
