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
		"message",
		"shortMessage",
		"replacements",
		"offset",
		"length",
		"context",
		"sentence",
		"type",
		"rule",
		"ignoreForIncompleteSentence",
		"contextForSureMatch"
})
public class Match {

	@JsonProperty("message")
	private String message;
	@JsonProperty("shortMessage")
	private String shortMessage;
	@JsonProperty("replacements")
	private List<Replacement> replacements = null;
	@JsonProperty("offset")
	private Integer offset;
	@JsonProperty("length")
	private Integer length;
	@JsonProperty("context")
	private Context context;
	@JsonProperty("sentence")
	private String sentence;
	@JsonProperty("type")
	private Type type;
	@JsonProperty("rule")
	private Rule rule;
	@JsonProperty("ignoreForIncompleteSentence")
	private Boolean ignoreForIncompleteSentence;
	@JsonProperty("contextForSureMatch")
	private Integer contextForSureMatch;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty("shortMessage")
	public String getShortMessage() {
		return shortMessage;
	}

	@JsonProperty("shortMessage")
	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}

	@JsonProperty("replacements")
	public List<Replacement> getReplacements() {
		return replacements;
	}

	@JsonProperty("replacements")
	public void setReplacements(List<Replacement> replacements) {
		this.replacements = replacements;
	}

	@JsonProperty("offset")
	public Integer getOffset() {
		return offset;
	}

	@JsonProperty("offset")
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	@JsonProperty("length")
	public Integer getLength() {
		return length;
	}

	@JsonProperty("length")
	public void setLength(Integer length) {
		this.length = length;
	}

	@JsonProperty("context")
	public Context getContext() {
		return context;
	}

	@JsonProperty("context")
	public void setContext(Context context) {
		this.context = context;
	}

	@JsonProperty("sentence")
	public String getSentence() {
		return sentence;
	}

	@JsonProperty("sentence")
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	@JsonProperty("type")
	public Type getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(Type type) {
		this.type = type;
	}

	@JsonProperty("rule")
	public Rule getRule() {
		return rule;
	}

	@JsonProperty("rule")
	public void setRule(Rule rule) {
		this.rule = rule;
	}

	@JsonProperty("ignoreForIncompleteSentence")
	public Boolean getIgnoreForIncompleteSentence() {
		return ignoreForIncompleteSentence;
	}

	@JsonProperty("ignoreForIncompleteSentence")
	public void setIgnoreForIncompleteSentence(Boolean ignoreForIncompleteSentence) {
		this.ignoreForIncompleteSentence = ignoreForIncompleteSentence;
	}

	@JsonProperty("contextForSureMatch")
	public Integer getContextForSureMatch() {
		return contextForSureMatch;
	}

	@JsonProperty("contextForSureMatch")
	public void setContextForSureMatch(Integer contextForSureMatch) {
		this.contextForSureMatch = contextForSureMatch;
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