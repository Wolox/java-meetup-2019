package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.exceptions.InvalidCuilException;
import ar.com.wolox.javameetup2019.exceptions.InvalidInputException;
import ar.com.wolox.javameetup2019.helpers.Utils;
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

		String name = bodyInput.getName();
		String lastName = bodyInput.getLastName();
		String documentType = bodyInput.getDocumentType();
		String documentNumber = bodyInput.getDocumentNumber();

		if(name.isEmpty() || name == null){
			throw new InvalidInputException("El campo name no fue ingresado.");
		}

		if(lastName.isEmpty() || lastName == null){
			throw new InvalidInputException("El campo last_name no fue ingresado.");
		}

		if(documentType.isEmpty() || documentType == null){
			throw new InvalidInputException("El campo document_type no fue ingresado.");
		}else{
			if(!documentType.toLowerCase().equals("dni") && !documentType.toLowerCase().equals("cuil")){
				throw new InvalidInputException("El campo document_type es incorrecto. El tipo solo puede ser dni o cuil");
			}
		}

		if(documentNumber.isEmpty() || documentNumber == null){
			throw new InvalidInputException("El campo document_number no fue ingresado.");
		}else{
			if(documentType.toLowerCase().equals("dni")){
				if (documentNumber.length() != 8 || !NumberUtils.isCreatable(documentNumber)){
					throw new InvalidInputException("El dni ingresado es invalido.");
				}
			}else{
				if (!Utils.validateCuil(documentNumber.replaceAll("-", "").trim())) {
					throw new InvalidCuilException("El cuil ingresado es invalido.");
				}
			}
		}

		exchange.getIn().setBody(bodyInput);

	}
}
