package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import ar.com.wolox.javameetup2019.model.Client;
import ar.com.wolox.javameetup2019.repository.ClientRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetClientByDocumentProcessor implements Processor {

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
		String document = (String) exchange.getProperty("document_number");

		if(document.isEmpty() || document == null){
			throw new InvalidInputException("El parametro document_number no fue ingresado.");
		}

		Client client =clientRepository.findOneByDocumentNumber(document);

		if(client == null){
			exchange.getIn().setBody("El documento ingresado no pertenece a un cliente registrado.");
		}else{
			exchange.getIn().setBody(client);
		}
	}
}
