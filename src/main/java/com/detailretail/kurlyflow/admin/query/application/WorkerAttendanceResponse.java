package com.detailretail.kurlyflow.admin.query.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkerAttendanceResponse {
  private String employeeNumber;
  private String name;
  private Boolean isAttended;
}
