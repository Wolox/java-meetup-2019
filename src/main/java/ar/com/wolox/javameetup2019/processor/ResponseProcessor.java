package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.pojo.Response;
import java.util.ArrayList;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Response response = new Response();

		response.setErrors(new ArrayList<>());
		response.setResult(exchange.getIn().getBody(String.class));

		exchange.getOut().setBody(response);
	}
}
