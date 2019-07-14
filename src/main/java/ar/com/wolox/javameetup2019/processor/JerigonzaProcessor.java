package ar.com.wolox.javameetup2019.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class JerigonzaProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		exchange.getOut().setBody(exchange.getIn().getBody(String.class)
				.replace("a", "apa")
				.replace("e", "epe")
				.replace("i", "ipi")
				.replace("o", "opo")
				.replace("u", "upu"));

	}
}
