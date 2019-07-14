package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.helper.PropertiesConstants;
import ar.com.wolox.javameetup2019.pojo.Response;
import ar.com.wolox.javameetup2019.pojo.StandardResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class OkResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		StandardResponse standardResponse= new StandardResponse();
		Response response= new Response();
		response.setMethod(exchange.getProperty(PropertiesConstants.PROPERTY_METHOD, String.class));
		response.setPath(exchange.getProperty(PropertiesConstants.PROPERTY_PATH, String.class));
		response.setErrors(Arrays.asList());

		List data = new ArrayList();
		data.add(exchange.getIn().getBody());

		standardResponse.setResponse(response);
		standardResponse.setData(data);

		exchange.getIn().setBody(standardResponse);

	}
}

