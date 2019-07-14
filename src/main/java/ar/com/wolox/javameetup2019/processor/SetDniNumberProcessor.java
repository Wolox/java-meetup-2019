package ar.com.wolox.javameetup2019.processor;


import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import ar.com.wolox.javameetup2019.helper.MessageConstants;
import ar.com.wolox.javameetup2019.helper.PropertiesConstants;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

@Component
public class SetDniNumberProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String dni = exchange.getIn().getHeader(PropertiesConstants.PROPERTY_DOCUMENT_NUMBER, String.class).trim();

		if (dni.length() != 8 || !NumberUtils.isCreatable(dni)){
			throw new InvalidInputException(MessageConstants.INCORRECT_DNI);
		}

		exchange.setProperty(PropertiesConstants.PROPERTY_DOCUMENT_NUMBER,dni);
	}
}
