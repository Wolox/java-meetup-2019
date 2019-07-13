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
		"version",
		"buildDate",
		"apiVersion",
		"premium",
		"premiumHint",
		"status"
})
public class Software {

	@JsonProperty("name")
	private String name;
	@JsonProperty("version")
	private String version;
	@JsonProperty("buildDate")
	private String buildDate;
	@JsonProperty("apiVersion")
	private Integer apiVersion;
	@JsonProperty("premium")
	private Boolean premium;
	@JsonProperty("premiumHint")
	private String premiumHint;
	@JsonProperty("status")
	private String status;
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

	@JsonProperty("version")
	public String getVersion() {
		return version;
	}

	@JsonProperty("version")
	public void setVersion(String version) {
		this.version = version;
	}

	@JsonProperty("buildDate")
	public String getBuildDate() {
		return buildDate;
	}

	@JsonProperty("buildDate")
	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	@JsonProperty("apiVersion")
	public Integer getApiVersion() {
		return apiVersion;
	}

	@JsonProperty("apiVersion")
	public void setApiVersion(Integer apiVersion) {
		this.apiVersion = apiVersion;
	}

	@JsonProperty("premium")
	public Boolean getPremium() {
		return premium;
	}

	@JsonProperty("premium")
	public void setPremium(Boolean premium) {
		this.premium = premium;
	}

	@JsonProperty("premiumHint")
	public String getPremiumHint() {
		return premiumHint;
	}

	@JsonProperty("premiumHint")
	public void setPremiumHint(String premiumHint) {
		this.premiumHint = premiumHint;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
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
