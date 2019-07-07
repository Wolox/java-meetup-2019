package ar.com.wolox.javameetup2019.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

	@Component
	public class JerigonzaProcessor implements Processor {

		@Override
		public void process(Exchange exchange) throws Exception {

			exchange.setProperty("statusWasOk", exchange.getIn()
					.getHeader(Exchange.HTTP_RESPONSE_CODE).equals(HttpStatus.OK.value()));

			exchange.getOut().setBody(exchange.getIn().getBody(String.class)
					.replace("a", "apa")
					.replace("e", "epe")
					.replace("i", "ipi")
					.replace("o", "opo")
					.replace("u", "upu"));

		}
	}




