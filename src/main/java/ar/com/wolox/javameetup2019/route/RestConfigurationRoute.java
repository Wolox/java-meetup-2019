package ar.com.wolox.javameetup2019.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("dev")
public class RestConfigurationRoute extends RouteBuilder {

    @Autowired
    Environment environment;

    @Override
    public void configure() throws Exception {


        restConfiguration()
                .bindingMode(RestBindingMode.json)
                .port("8080")
                .host("localhost")
                .component("servlet");

        rest()
            .get("/hello").to("direct:hello")
            .get("/bye").to("direct:bye")
            .get("/test-env").to("direct:test-environment")
            .get("/move-files").to("direct:move-files")
            .get("/students").to("direct:get-students-db");

        from("direct:hello")
            .transform().constant("Hello World!");
        from("direct:bye")
            .transform().constant("Bye World");
        from("direct:test-environment")
            .transform().constant("Environment: " + environment.getProperty("message"));

    }
}
