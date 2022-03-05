package com.fse3.eauction.dto;

import com.fse3.eauction.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductBidDTO {
	
	private Product product;
	private float bidAmount;
}
