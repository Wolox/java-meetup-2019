package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import ar.com.wolox.javameetup2019.helper.MessagesConstants;
import ar.com.wolox.javameetup2019.helper.PropertiesConstants;
import ar.com.wolox.javameetup2019.helper.ValidationUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class SetTypePropertyProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String type= (String) exchange.getIn().getHeader(PropertiesConstants.PROPERTY_DOCUMENT_TYPE);

		if(ValidationUtils.isEmptyString(type)){
			throw new InvalidInputException(MessagesConstants.EMPTY_DOCUMENT_TYPE);
		}

		type = type.toLowerCase();
		exchange.setProperty(PropertiesConstants.PROPERTY_DOCUMENT_TYPE, type);

	}
}
