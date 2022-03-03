package com.fse3.eauction.dto;

import java.util.Date;

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
	private String startingPrice;
	private Date bidEndDate;
	private String sellerEmailId;
}
