package ar.com.wolox.javameetup2019;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class WoloxJavaMeetupApplication {

	public static void main(String[] args) {
		SpringApplication.run(WoloxJavaMeetupApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(),
				"/*");
		registration.setName("CamelServlet");
		return registration;
	}

}
