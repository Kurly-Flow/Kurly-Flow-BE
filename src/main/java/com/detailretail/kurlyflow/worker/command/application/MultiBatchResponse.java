package com.detailretail.kurlyflow.worker.command.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MultiBatchResponse {

  private Long batchId;
  private String name;
  private Integer quantity;
  private Double weight;
}
