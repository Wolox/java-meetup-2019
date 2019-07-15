package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.exception.InvalidCuilException;
import ar.com.wolox.javameetup2019.exception.InvalidInputException;
import ar.com.wolox.javameetup2019.helper.MessagesConstants;
import ar.com.wolox.javameetup2019.helper.PropertiesConstants;
import ar.com.wolox.javameetup2019.helper.ValidationUtils;
import ar.com.wolox.javameetup2019.pojo.BodyInput;
import ar.com.wolox.javameetup2019.repository.ClientRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BodyInputValidationsProcessor implements Processor {

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
		BodyInput bodyInput = exchange.getIn().getBody(BodyInput.class);

		String name = bodyInput.getName().toLowerCase().trim();
		String lastName = bodyInput.getLastName().toLowerCase().trim();
		String documentType = bodyInput.getDocumentType().toLowerCase().trim();
		String documentNumber = bodyInput.getDocumentNumber().replaceAll("-","").trim();

		if(ValidationUtils.isEmptyString(name)){
			throw new InvalidInputException(MessagesConstants.EMPTY_NAME);
		}

		if(ValidationUtils.isEmptyString(lastName)){
			throw new InvalidInputException(MessagesConstants.EMPTY_LASTNAME);
		}

		if(ValidationUtils.isEmptyString(documentType)){
			throw new InvalidInputException(MessagesConstants.EMPTY_DOCUMENT_TYPE);
		}else if(!documentType.equals(PropertiesConstants.VALUE_DOCUMENT_TYPE_DNI) &&
				!documentType.equals(PropertiesConstants.VALUE_DOCUMENT_TYPE_CUIL)){
					throw new InvalidInputException(MessagesConstants.INCORRECT_DOCUMENT_TYPE);
		}

		if(ValidationUtils.isEmptyString(documentNumber)){
			throw new InvalidInputException(MessagesConstants.EMPTY_DOCUMENT_NUMBER);
		}else{
			if(documentType.equals(PropertiesConstants.VALUE_DOCUMENT_TYPE_DNI)){
				if ((documentNumber.length() != PropertiesConstants.DNI_LENGTH) ||
						(!NumberUtils.isCreatable(documentNumber))){
					throw new InvalidInputException(MessagesConstants.INCORRECT_DNI);
				}
			}else{
				if (!ValidationUtils.validateCuil(documentNumber)) {
					throw new InvalidCuilException(MessagesConstants.INCORRECT_CUIL);
				}
			}
		}

		bodyInput.setName(name);
		bodyInput.setLastName(lastName);
		bodyInput.setDocumentType(documentType);
		bodyInput.setDocumentNumber(documentNumber);

		exchange.getIn().setBody(bodyInput);

	}
}
