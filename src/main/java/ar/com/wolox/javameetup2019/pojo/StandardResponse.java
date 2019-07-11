package ar.com.wolox.javameetup2019.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonPropertyOrder({"response", "data"})
public class StandardResponse {

	@JsonProperty("response")
	private Response response;

	@JsonProperty("data")
	private List data;

	public StandardResponse() {
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}
}
