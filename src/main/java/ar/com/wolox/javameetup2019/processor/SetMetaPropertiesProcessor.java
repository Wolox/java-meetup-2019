package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.helper.PropertiesConstants;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class SetMetaPropertiesProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		exchange.setProperty(PropertiesConstants.PROPERTY_PATH, exchange.getIn()
				.getHeader(PropertiesConstants.VALUE_CAMEL_URI));
		exchange.setProperty(PropertiesConstants.PROPERTY_METHOD, exchange.getIn()
				.getHeader(PropertiesConstants.VALUE_CAMEL_METHOD));
	}
}

