package com.detailretail.kurlyflow.order.query.application;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EndCompleteResponse {

  private Long basketId;
  private Long invoiceId;
  private LocalDateTime endAt;
}
