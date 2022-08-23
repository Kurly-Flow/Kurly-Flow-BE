package com.detailretail.kurlyflow.order.query.application;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MultiBatchResponse {

  private Integer recommendToteCount;
  private Long batchId;
  private List<InvoiceProductResponse> invoiceProductResponses;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class InvoiceProductResponse {

    private Long invoiceProductId;
    private String name;
    private Integer quantity;
    private Double weight;
    private String imageUrl;
    private String region;
    private String location;
  }
}
