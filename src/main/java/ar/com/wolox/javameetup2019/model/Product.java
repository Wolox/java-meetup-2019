package ar.com.wolox.javameetup2019.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Product {

	@ApiModelProperty
	private int id;

	@ApiModelProperty
	private String name;

	@ApiModelProperty
	private int stock;

	public Product() {
		/* Empty constructor for entity mapping */
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
