package com.detailretail.kurlyflow.tote.command.application;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToteMoveRequest {

  private Long batchId;
  private String oldToteId;
  private String newToteId;
  private List<Long> invoiceProductIds;
}
