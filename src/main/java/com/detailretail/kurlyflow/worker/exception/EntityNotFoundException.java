package com.detailretail.kurlyflow.worker.exception;


import com.detailretail.kurlyflow.common.exception.NotFoundException;

public class WorkerNotFoundException extends NotFoundException {

  private static final String WORKER_NOT_FOUND = "해당 작업자가 존재하지 않습니다.";

  public WorkerNotFoundException  () {
    super(WORKER_NOT_FOUND);
  }
}