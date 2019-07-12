package ar.com.wolox.javameetup2019.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENT_SEQ")
	@SequenceGenerator(name = "CLIENT_SEQ", sequenceName = "CLIENT_SEQ")
	private long clientId;

	@Column
	private String name;
	@Column
	private String lastName;
	@Column
	private String documentType;
	@Column(unique = true)
	private String documentNumber;


	public Client() {
	}


	public Client(String name, String lastName, String documentType, String documentNumber) {
		this.name = name;
		this.lastName = lastName;
		this.documentType = documentType;
		this.documentNumber = documentNumber;
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
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
