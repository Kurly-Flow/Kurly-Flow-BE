package com.detailretail.kurlyflow.admin.exception;

import com.detailretail.kurlyflow.common.exception.BadRequestException;

public class WorkingTeamNotMatchException extends BadRequestException {

  private static final String WORKING_TEAM_NOT_MATCH = "올바른 조 이름이 아닙니다.";

  public WorkingTeamNotMatchException() {
    super(WORKING_TEAM_NOT_MATCH);
  }
}
