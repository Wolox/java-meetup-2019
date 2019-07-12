package ar.com.wolox.javameetup2019.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonPropertyOrder({"path", "method", "error"})
public class Response {

	@JsonProperty("path")
	private String path;

	@JsonProperty("method")
	private String method;

	@JsonProperty("error")
	private List<Error> errors;

	public Response() {
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> error) {
		this.errors = error;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
