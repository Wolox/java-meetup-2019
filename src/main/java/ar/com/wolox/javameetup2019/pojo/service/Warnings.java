package ar.com.wolox.javameetup2019.pojo.service;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"incompleteResults"
})
public class Warnings {

	@JsonProperty("incompleteResults")
	private Boolean incompleteResults;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("incompleteResults")
	public Boolean getIncompleteResults() {
		return incompleteResults;
	}

	@JsonProperty("incompleteResults")
	public void setIncompleteResults(Boolean incompleteResults) {
		this.incompleteResults = incompleteResults;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}