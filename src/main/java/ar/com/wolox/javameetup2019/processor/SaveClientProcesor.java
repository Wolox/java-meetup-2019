package ar.com.wolox.javameetup2019.processor;

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

		Client client = new Client(bodyInput.getName().toLowerCase(), bodyInput.getLastName().toLowerCase(),
				bodyInput.getDocumentType(), bodyInput.getDocumentNumber().replaceAll("-", "").trim());

		clientRepository.save(client);

		exchange.getIn().setBody("El cliente fue guardado con exito.");

	}
}