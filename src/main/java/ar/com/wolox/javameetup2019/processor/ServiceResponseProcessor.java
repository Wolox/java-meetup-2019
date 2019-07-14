package ar.com.wolox.javameetup2019.processor;

import ar.com.wolox.javameetup2019.helpers.CamelConstants;
import ar.com.wolox.javameetup2019.pojo.service.Match;
import ar.com.wolox.javameetup2019.pojo.service.ServiceResponse;
import java.util.ArrayList;
import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ServiceResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		ServiceResponse serviceResponse = exchange.getIn().getBody(ServiceResponse.class);

		String correctedText = exchange.getProperty(CamelConstants.HEADER_TEXT, String.class);

		List<String> replacements = new ArrayList<>();

		for (Match match : serviceResponse.getMatches()) {
			String textToReplace = correctedText
					.substring(match.getOffset(), match.getOffset() + match.getLength());
			if (!match.getReplacements().isEmpty()) {
				String replacement = match.getReplacements().get(0).getValue();
				replacements.add(String
						.format("%s%s%s", textToReplace, CamelConstants.REPLACEMENT_TAG,
								replacement));
			}
		}

		for (String replacementPair : replacements) {
			String[] replacementPairStr = replacementPair.split(CamelConstants.REPLACEMENT_TAG);
			String textToReplace = replacementPairStr[0];
			String replacement = replacementPairStr[1];
			correctedText = correctedText.replace(textToReplace, replacement);
		}

		exchange.getOut().setBody(correctedText);
	}
}
