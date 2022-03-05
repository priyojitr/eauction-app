package com.fse3.eauction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BidDTO {
	
	private String buyerEmailId;
	private String productId;
	private float bidAmount;

}
