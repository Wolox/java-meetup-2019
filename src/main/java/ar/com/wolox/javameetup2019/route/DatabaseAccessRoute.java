package ar.com.wolox.javameetup2019.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DatabaseAccessRoute extends RouteBuilder {

    @Autowired
    Environment environment;

    @Override
    public void configure() throws Exception {
        from("direct:get-students-db")
                .to("sql:select * from students")
                .to("log:query")
                .to("mock:query");
    }
}