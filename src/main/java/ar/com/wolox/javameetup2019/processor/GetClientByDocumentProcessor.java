package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import ar.com.wolox.javameetup2019.helper.MessagesConstants;
import ar.com.wolox.javameetup2019.helper.PropertiesConstants;
import ar.com.wolox.javameetup2019.helper.ValidationUtils;
import ar.com.wolox.javameetup2019.model.Client;
import ar.com.wolox.javameetup2019.repository.ClientRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetClientByDocumentProcessor implements Processor {

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
		String document = (String) exchange.getProperty(PropertiesConstants.PROPERTY_DOCUMENT_NUMBER);

		if(ValidationUtils.isEmptyString(document)){
			throw new InvalidInputException(MessagesConstants.EMPTY_DOCUMENT_NUMBER);
		}

		Optional<Client> optionalClient = clientRepository.findOneByDocumentNumber(document);

		if(optionalClient.isPresent()){
			exchange.getIn().setBody(optionalClient.get());
		}else{
			exchange.getIn().setBody(MessagesConstants.NONEXISTENT_CLIENT);
		}
	}
}
