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
		"id",
		"description",
		"issueType",
		"category"
})
public class Rule {

	@JsonProperty("id")
	private String id;
	@JsonProperty("description")
	private String description;
	@JsonProperty("issueType")
	private String issueType;
	@JsonProperty("category")
	private Category category;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("issueType")
	public String getIssueType() {
		return issueType;
	}

	@JsonProperty("issueType")
	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	@JsonProperty("category")
	public Category getCategory() {
		return category;
	}

	@JsonProperty("category")
	public void setCategory(Category category) {
		this.category = category;
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