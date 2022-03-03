package com.fse3.eauction.model;

import javax.validation.constraints.NotNull;

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
@Document(value = "bids")
public class Bid {
	@Id
	private String bidId;
	@NotNull
	private String sellerId;
	@NotNull
	private String buyerId;
	@NotNull
	private String productId;
	@NotNull
	private boolean active;
}
