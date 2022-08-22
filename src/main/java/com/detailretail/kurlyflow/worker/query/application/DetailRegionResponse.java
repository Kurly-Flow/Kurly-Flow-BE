package com.detailretail.kurlyflow.worker.query.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailRegionResponse {

  private String region;
  private String detailRegion;
}
