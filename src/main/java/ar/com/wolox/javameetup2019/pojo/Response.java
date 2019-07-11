package ar.com.wolox.javameetup2019.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"path", "status", "http_code", "error"})
public class Response {

	@JsonProperty("path")
	private String path;

	@JsonProperty("status")
	private String status;

	@JsonProperty("http_code")
	private String httpCode;

	@JsonProperty("error")
	private Error error;

	public Response() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(String httpCode) {
		this.httpCode = httpCode;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
