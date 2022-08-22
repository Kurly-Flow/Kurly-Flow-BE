package com.detailretail.kurlyflow.worker.command.application;

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
  private List<BatchResponse> batchResponses;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class BatchResponse {

    private Long batchId;
    private String name;
    private Integer quantity;
    private Double weight;
  }
}
