package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.helper.PropertiesConstants;
import ar.com.wolox.javameetup2019.pojo.Error;
import ar.com.wolox.javameetup2019.pojo.Response;
import ar.com.wolox.javameetup2019.pojo.StandardResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		StandardResponse restResponse = new StandardResponse();
		List errors = new ArrayList();

		Error error = new Error();
		error.setMessage(exchange.getProperty(PropertiesConstants.PROPERTY_DETAIL, String.class));
		error.setCode(exchange.getProperty(PropertiesConstants.PROPERTY_CODE, String.class));

		errors.add(error);

		Response response = new Response();

		response.setMethod(exchange.getProperty(PropertiesConstants.PROPERTY_METHOD, String.class));

		response.setPath(exchange.getProperty(PropertiesConstants.PROPERTY_PATH, String.class));
		response.setErrors(errors);

		restResponse.setResponse(response);
		restResponse.setData(Arrays.asList());

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(restResponse);

		exchange.getOut().setBody(json);
	}
}

