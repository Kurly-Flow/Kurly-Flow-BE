package com.detailretail.kurlyflow.worker.command.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkingPlaceLoginResponse {

  private String accessToken;
  private String name;
  private String region;
  private String detailRegion;
}
