package com.detailretail.kurlyflow.basket.query.application;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EndBasketInvoiceResponse {

  private String invoiceId;
  private String ordererName;
  private String ordererAddress;
  private List<InvoiceProductResponse> products;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class InvoiceProductResponse {

    private Long invoiceProductId;
    private String name;
    private Integer quantity;
  }
}
