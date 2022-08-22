package com.detailretail.kurlyflow.order.query.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EndBasketInvoiceResponse {

  private String name;
  private Integer quantity;
}
