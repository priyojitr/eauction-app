package com.fse3.eauction.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(value = "products")
public class Product {
	@Id
	private String productId;
	@NotNull
	@Size(min = 5,max = 30,message = "{validation.product.name.length}")
	private String productName;
	private String shortDescription;
	private String detailDescription;
	private String category;
	private String startingPrice;
	private Date bidEndDate;
	private String sellerEmailId;
}
