package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import ar.com.wolox.javameetup2019.helper.MessagesConstants;
import ar.com.wolox.javameetup2019.helper.PropertiesConstants;
import ar.com.wolox.javameetup2019.helper.ValidationUtils;
import ar.com.wolox.javameetup2019.model.Client;
import ar.com.wolox.javameetup2019.repository.ClientRepository;
import java.util.List;
import java.util.Optional;

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
			throw new InvalidInputException(MessagesConstants.EMPTY_NAME);
		}

		Optional<List<Client>> optionalClients = clientRepository.findByName(name);

		if(optionalClients.isPresent()){
			exchange.getIn().setBody(optionalClients.get());
		}else{
			exchange.getIn().setBody(MessagesConstants.NONEXISTENT_CLIENT);
		}
	}
}

