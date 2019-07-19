package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import ar.com.wolox.javameetup2019.helpers.CamelConstants;
import ar.com.wolox.javameetup2019.helpers.ErrorConstants;
import ar.com.wolox.javameetup2019.pojo.Request;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.TypeConversionException;
import org.apache.camel.http.common.HttpMethods;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InputToServiceProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		try{
			Optional<String> optionalText = Optional.ofNullable(exchange.getIn().getBody(Request.class).getText());
			exchange.setProperty(CamelConstants.HEADER_TEXT, optionalText.orElseThrow(()-> new NullPointerException()));
		} catch(TypeConversionException | NullPointerException exception) {
			exchange.setProperty(CamelConstants.CODE, ErrorConstants.INVALID_INPUT_CODE);
			throw new InvalidInputException(ErrorConstants.INVALID_INPUT_DETAIL);
		}

		Optional<Boolean> optionalConvert = Optional.ofNullable(exchange.getIn()
				.getHeader(CamelConstants.HEADER_CONVERT, Boolean.class));
		Boolean convert = optionalConvert.orElse(Boolean.FALSE);

		exchange.setProperty(CamelConstants.HEADER_CONVERT, convert);
		exchange.getOut().setBody("");
		exchange.getOut().setHeader(Exchange.HTTP_METHOD, HttpMethods.POST);
	}
}
