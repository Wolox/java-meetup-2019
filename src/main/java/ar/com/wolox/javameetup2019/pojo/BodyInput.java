package ar.com.wolox.javameetup2019.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"name", "last_name", "document_type", "document_number"})
public class BodyInput {

	@JsonProperty("name")
	private String name;
	@JsonProperty("last_name")
	private String lastName;
	@JsonProperty("document_type")
	private String documentType;
	@JsonProperty("document_number")
	private String documentNumber;

	public BodyInput() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
}
