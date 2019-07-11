package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.pojo.Error;
import ar.com.wolox.javameetup2019.pojo.Response;
import ar.com.wolox.javameetup2019.pojo.StandardResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ErrorProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		StandardResponse restResponse = new StandardResponse();

		Error error = new Error();
		error.setMessage(exchange.getProperty("detail", String.class));
		error.setCode(exchange.getProperty("code", String.class));

		Response response = new Response();
		response.setHttpCode(exchange.getProperty("", String.class));
		response.setStatus(exchange.getProperty("", String.class));
		response.setPath(exchange.getProperty("CamelHttpUri", String.class));
		response.setError(error);

		restResponse.setResponse(response);
		restResponse.setData(Arrays.asList());

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(restResponse);

		exchange.getOut().setBody(json);
	}
}

