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
		"name",
		"code",
		"detectedLanguage"
})
public class Language {

	@JsonProperty("name")
	private String name;
	@JsonProperty("code")
	private String code;
	@JsonProperty("detectedLanguage")
	private DetectedLanguage detectedLanguage;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	@JsonProperty("code")
	public void setCode(String code) {
		this.code = code;
	}

	@JsonProperty("detectedLanguage")
	public DetectedLanguage getDetectedLanguage() {
		return detectedLanguage;
	}

	@JsonProperty("detectedLanguage")
	public void setDetectedLanguage(DetectedLanguage detectedLanguage) {
		this.detectedLanguage = detectedLanguage;
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