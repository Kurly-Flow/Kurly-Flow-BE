package com.detailretail.kurlyflow.basket.query.application;

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

  private String basketId;
  private String invoiceId;
  private LocalDateTime endAt;
}
