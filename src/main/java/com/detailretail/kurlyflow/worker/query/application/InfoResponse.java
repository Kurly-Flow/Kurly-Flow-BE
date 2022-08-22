package com.detailretail.kurlyflow.worker.query.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoResponse {

  private String name;
  private String phone;
  private String employeeNumber;
  private String wishRegion;
  private String region;
  private String detailRegion;
  private Boolean isAttended;
}
