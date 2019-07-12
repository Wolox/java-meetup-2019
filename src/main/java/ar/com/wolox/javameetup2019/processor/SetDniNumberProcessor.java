package ar.com.wolox.javameetup2019.processor;


import ar.com.wolox.javameetup2019.exceptions.InvalidInputException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

@Component
public class SetDniNumberProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String dni = exchange.getIn().getHeader("document_number", String.class).trim();

		if (dni.length() != 8 || !NumberUtils.isCreatable(dni)){
			throw new InvalidInputException("El dni ingresado es invalido.");
		}

		exchange.setProperty("document_number",dni);
	}
}
