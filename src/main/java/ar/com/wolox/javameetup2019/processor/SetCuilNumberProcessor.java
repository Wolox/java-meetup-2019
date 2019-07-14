package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.exception.InvalidCuilException;
import ar.com.wolox.javameetup2019.helper.MessageConstants;
import ar.com.wolox.javameetup2019.helper.PropertiesConstants;
import ar.com.wolox.javameetup2019.helper.ValidationUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class SetCuilNumberProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String cuil = exchange.getIn().getHeader(PropertiesConstants.PROPERTY_DOCUMENT_NUMBER, String.class).trim();
		cuil = cuil.replaceAll("-", "");

		if (!ValidationUtils.validateCuil(cuil)) {
			throw new InvalidCuilException(MessageConstants.INVALID_CUIL);
		}

		exchange.setProperty(PropertiesConstants.PROPERTY_DOCUMENT_NUMBER, cuil);
	}
}



