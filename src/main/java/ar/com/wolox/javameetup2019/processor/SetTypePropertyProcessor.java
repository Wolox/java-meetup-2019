package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class SetTypePropertyProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String type= (String) exchange.getIn().getHeader("document_type");

		if(type.isEmpty() || type == null){
			throw new InvalidInputException("El tipo de documento no fue ingresado");
		}

		type = type.toLowerCase();
		exchange.setProperty("type",type);

	}
}
