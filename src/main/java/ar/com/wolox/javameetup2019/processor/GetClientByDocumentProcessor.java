package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import ar.com.wolox.javameetup2019.helper.MessageConstants;
import ar.com.wolox.javameetup2019.helper.PropertiesConstants;
import ar.com.wolox.javameetup2019.helper.ValidationUtils;
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
		String document = (String) exchange.getProperty(PropertiesConstants.PROPERTY_DOCUMENT_NUMBER);

		if(ValidationUtils.isEmptyString(document)){
			throw new InvalidInputException(MessageConstants.EMPTY_DOCUMENT_NUMBER);
		}

		Client client =clientRepository.findOneByDocumentNumber(document);

		if(client == null){
			exchange.getIn().setBody(MessageConstants.NONEXISTENT_CLIENT);
		}else{
			exchange.getIn().setBody(client);
		}
	}
}
