package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.exceptions.InvalidCuilException;
import ar.com.wolox.javameetup2019.helpers.Utils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class SetCuilNumberProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String cuil = exchange.getIn().getHeader("document_number", String.class).trim();
		cuil = cuil.replaceAll("-", "");

		if (!Utils.validateCuil(cuil)) {
			throw new InvalidCuilException("El cuil ingresado es invalido.");
		}

		exchange.setProperty("document_number", cuil);
	}
}



