package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import ar.com.wolox.javameetup2019.helpers.CamelConstants;
import ar.com.wolox.javameetup2019.helpers.ErrorConstants;
import ar.com.wolox.javameetup2019.pojo.Request;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.http.common.HttpMethods;
import org.springframework.stereotype.Component;

@Component
public class InputToServiceProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Request request = exchange.getIn().getBody(Request.class);
		Boolean convert = exchange.getIn()
				.getHeader(CamelConstants.HEADER_CONVERT, Boolean.class);

		if (request == null || request.getText() == null) {
			exchange.setProperty(CamelConstants.CODE, ErrorConstants.INVALID_INPUT_CODE);
			throw new InvalidInputException(ErrorConstants.INVALID_INPUT_DETAIL);
		}
		if (convert == null) {
			exchange.setProperty(CamelConstants.CODE, ErrorConstants.MISSING_HEADER_CODE);
			throw new InvalidInputException(ErrorConstants.MISSING_HEADER_DETAIL);
		}

		exchange.setProperty(CamelConstants.HEADER_CONVERT, convert);
		exchange.getOut().setBody("");
		exchange.setProperty(CamelConstants.HEADER_TEXT, request.getText());
		exchange.getOut().setHeader(Exchange.HTTP_METHOD, HttpMethods.POST);

	}
}
