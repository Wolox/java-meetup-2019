package ar.com.wolox.javameetup2019.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class SetMetaPropertiesProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		exchange.setProperty("path", exchange.getIn().getHeader("CamelHttpUri"));
		exchange.setProperty("method", exchange.getIn().getHeader("CamelHttpMethod"));
	}
}

