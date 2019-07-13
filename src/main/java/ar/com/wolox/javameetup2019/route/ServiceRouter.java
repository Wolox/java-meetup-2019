package ar.com.wolox.javameetup2019.route;

import ar.com.wolox.javameetup2019.helpers.Constants;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
class ServiceRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from(Constants.VALIDATE_TEXT)
				// TODO
				.to(Constants.RESULT_ENDPOINT);

	}
}
