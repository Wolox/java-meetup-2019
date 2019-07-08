package ar.com.wolox.javameetup2019.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MovingFilesRoute extends RouteBuilder {

    @Autowired
    Environment environment;

    @Override
    public void configure() throws Exception {
        from("direct:move-files")
                .log("Moving files on: " + environment.getProperty("message"))
                .pollEnrich("{{fromRoute}}")
                .to("{{toRoute}}")
                .transform().constant("Files were moved!");
    }
}