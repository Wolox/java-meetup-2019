package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.helpers.CamelConstants;
import ar.com.wolox.javameetup2019.pojo.Error;
import ar.com.wolox.javameetup2019.pojo.Response;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ErrorProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		Response response = new Response();

		Error error = new Error();
		error.setCode(exchange.getIn().getHeader(CamelConstants.CODE, Integer.class));
		error.setDetail(exchange.getIn().getHeader(CamelConstants.DETAIL, String.class));

		response.setData(new ArrayList<>());
		response.setErrors(Arrays.asList(error));

		exchange.getOut().setBody(response);

	}
}
