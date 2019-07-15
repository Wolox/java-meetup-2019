package ar.com.wolox.javameetup2019

import ar.com.wolox.javameetup2019.processor.JerigonzaProcessor
import org.apache.camel.Exchange
import org.apache.camel.builder.ExchangeBuilder
import org.apache.camel.impl.DefaultCamelContext
import org.apache.commons.lang3.RandomStringUtils
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Specification

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = [JerigonzaProcessor.class])
@SpringBootTest
class JeringozaProcessorSpec  extends  Specification {

    @Autowired
    JerigonzaProcessor processor

    def "Translate a random text to jeringozo"() {
        given: "generate a random text"
        def text = RandomStringUtils.randomAlphanumeric(100)
        and: "create a exchange"
        def exchange = ExchangeBuilder.anExchange(new DefaultCamelContext())
                .withBody(text)
                .build()
        when: "call jeringozo process"
        processor.process(exchange)
        def jeringozoText = exchange.getIn().getBody(String.class)
        then: "check text"
        checkJeringozo(text, jeringozoText)
    }

    boolean checkJeringozo(String text, String jeringozoText) {
        jeringozoText
                .replace("apa", "a")
                .replace("epe", "e")
                .replace("ipi", "i")
                .replace("opo", "o")
                .replace("upu", "u")
        text == jeringozoText
    }
}
