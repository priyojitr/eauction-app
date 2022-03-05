package com.fse3.eauction.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductDTO {
	private String productName;
	private String shortDescription;
	private String detailDescription;
	private String category;
	private float startingPrice;
	private Date bidEndDate;
	@NotNull
	@Email
	private String sellerEmailId;
}
