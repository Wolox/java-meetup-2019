package ar.com.wolox.javameetup2019.pojo.service;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"software",
		"warnings",
		"language",
		"matches"
})
public class ServiceResponse {

	@JsonProperty("software")
	private Software software;
	@JsonProperty("warnings")
	private Warnings warnings;
	@JsonProperty("language")
	private Language language;
	@JsonProperty("matches")
	private List<Match> matches = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("software")
	public Software getSoftware() {
		return software;
	}

	@JsonProperty("software")
	public void setSoftware(Software software) {
		this.software = software;
	}

	@JsonProperty("warnings")
	public Warnings getWarnings() {
		return warnings;
	}

	@JsonProperty("warnings")
	public void setWarnings(Warnings warnings) {
		this.warnings = warnings;
	}

	@JsonProperty("language")
	public Language getLanguage() {
		return language;
	}

	@JsonProperty("language")
	public void setLanguage(Language language) {
		this.language = language;
	}

	@JsonProperty("matches")
	public List<Match> getMatches() {
		return matches;
	}

	@JsonProperty("matches")
	public void setMatches(List<Match> matches) {
		this.matches = matches;
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