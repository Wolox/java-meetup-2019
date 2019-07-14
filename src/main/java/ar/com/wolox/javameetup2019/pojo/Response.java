package ar.com.wolox.javameetup2019.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"result", "errors"})
public class Response {

	@JsonProperty
	private String result;

	@JsonProperty
	private List<Error> errors;

	public Response() {
		/* Empty constructor for entity mapping */
	}

	public String getResult() {
		return result;
	}

	public void setResult(String data) {
		this.result = data;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

}
