package com.fse3.eauction.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(value = "bids")
@CompoundIndex(name = "productId_buyerEmail", def = "{'productId':1,'buyerEmailId':1}")
public class Bid {
	
	@Id
	private String bidId;
	@NotNull
	private String buyerId;
	@NotNull
	private String buyerEmailId;
	@NotNull
	private float bidAmount;
	@NotNull
	private String productId;

}
