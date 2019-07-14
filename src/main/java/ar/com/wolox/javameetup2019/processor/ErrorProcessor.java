package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.helpers.CamelConstants;
import ar.com.wolox.javameetup2019.pojo.Error;
import ar.com.wolox.javameetup2019.pojo.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ErrorProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		Response response = new Response();

		Error error = new Error();
		error.setCode(exchange.getProperty(CamelConstants.CODE, Integer.class));
		error.setDetail(exchange.getProperty(CamelConstants.DETAIL, String.class));

		response.setResult("");
		response.setErrors(Collections.singletonList(error));

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(response);

		exchange.getOut().setBody(json);

	}
}
