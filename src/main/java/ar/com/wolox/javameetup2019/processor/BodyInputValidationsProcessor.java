package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.exception.InvalidCuilException;
import ar.com.wolox.javameetup2019.exception.InvalidInputException;
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

		if(name.isEmpty() || name == null){
			throw new InvalidInputException("El campo name no fue ingresado.");
		}

		if(lastName.isEmpty() || lastName == null){
			throw new InvalidInputException("El campo last_name no fue ingresado.");
		}

		if(documentType.isEmpty() || documentType == null){
			throw new InvalidInputException("El campo document_type no fue ingresado.");
		}else{
			if(!documentType.equals("dni") && !documentType.equals("cuil")){
				throw new InvalidInputException("El campo document_type es incorrecto. El tipo solo puede ser dni o cuil");
			}
		}

		if(documentNumber.isEmpty() || documentNumber == null){
			throw new InvalidInputException("El campo document_number no fue ingresado.");
		}else{
			if(documentType.equals("dni")){
				if (documentNumber.length() != 8 || !NumberUtils.isCreatable(documentNumber)){
					throw new InvalidInputException("El dni ingresado es invalido.");
				}
			}else{
				if (!ValidationUtils.validateCuil(documentNumber)) {
					throw new InvalidCuilException("El cuil ingresado es invalido.");
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
