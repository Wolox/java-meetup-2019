package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.exceptions.InvalidInputException;
import ar.com.wolox.javameetup2019.model.Client;
import ar.com.wolox.javameetup2019.repository.ClientRepository;
import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetClientByNameProcessor implements Processor {

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
		String name = (String) exchange.getIn().getHeader("name");

		if(name.isEmpty() || name == null){
			throw new InvalidInputException("El parametro name no fue ingresado.");
		}

		name = name.toLowerCase();

		List<Client> clients = clientRepository.findByName(name);

		if(clients.isEmpty() || clients == null){
			exchange.getIn().setBody("No existen clientes registrados con el nombre ingresado.");
		}else{
			exchange.getIn().setBody(clientRepository.findByName(name));
		}
	}
}

