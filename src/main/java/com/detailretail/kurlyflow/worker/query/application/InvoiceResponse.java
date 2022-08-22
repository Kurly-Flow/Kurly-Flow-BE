package com.detailretail.kurlyflow.worker.query.application;

import com.detailretail.kurlyflow.product.command.domain.Packaging;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {

  private Long id;
  private List<InvoiceProductResponse> products;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class InvoiceProductResponse {

    private Long invoiceProductId;
    private String name;
    private Integer quantity;
    private Packaging packaging;
  }
}
