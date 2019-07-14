package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import ar.com.wolox.javameetup2019.helper.MessageConstants;
import ar.com.wolox.javameetup2019.helper.PropertiesConstants;
import ar.com.wolox.javameetup2019.helper.ValidationUtils;
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
		String name = (String) exchange.getIn().getHeader(PropertiesConstants.PROPERTY_NAME);

		if(ValidationUtils.isEmptyString(name)){
			throw new InvalidInputException(MessageConstants.EMPTY_NAME);
		}

		name = name.toLowerCase();

		List<Client> clients = clientRepository.findByName(name);

		if(clients == null || clients.isEmpty()){
			exchange.getIn().setBody(MessageConstants.NONEXISTENT_CLIENT);
		}else{
			exchange.getIn().setBody(clientRepository.findByName(name));
		}
	}
}

