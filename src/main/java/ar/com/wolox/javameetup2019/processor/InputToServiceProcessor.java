package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import ar.com.wolox.javameetup2019.helpers.CamelConstants;
import ar.com.wolox.javameetup2019.helpers.ErrorConstants;
import ar.com.wolox.javameetup2019.pojo.Request;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InputToServiceProcessor implements Processor {

	@Value(value = "${service-method}")
	private String serviceMethod;

	@Override
	public void process(Exchange exchange) throws Exception {
		Request request = exchange.getIn().getBody(Request.class);
		if (request == null) {
			throw new InvalidInputException(ErrorConstants.INVALID_INPUT_DETAIL);
		}

		exchange.getIn().setHeader(CamelConstants.HEADER_TEXT, request.getText());
		exchange.getIn().setHeader(CamelConstants.HEADER_LANG, CamelConstants.LANG_ES);
		exchange.getIn().setHeader(Exchange.HTTP_METHOD, serviceMethod);

	}
}
