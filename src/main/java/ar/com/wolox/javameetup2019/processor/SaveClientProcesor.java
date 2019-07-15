package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.helper.MessagesConstants;
import ar.com.wolox.javameetup2019.model.Client;
import ar.com.wolox.javameetup2019.pojo.BodyInput;
import ar.com.wolox.javameetup2019.repository.ClientRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveClientProcesor implements Processor {

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
		BodyInput bodyInput = exchange.getIn().getBody(BodyInput.class);

		Client client = new Client(bodyInput.getName(), bodyInput.getLastName(),
				bodyInput.getDocumentType(), bodyInput.getDocumentNumber());

		clientRepository.save(client);

		exchange.getIn().setBody(MessagesConstants.SAVED_CLIENT);

	}
}