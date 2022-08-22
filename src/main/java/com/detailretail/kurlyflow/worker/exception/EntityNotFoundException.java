package com.detailretail.kurlyflow.worker.exception;


import com.detailretail.kurlyflow.common.exception.NotFoundException;

public class EntityNotFoundException extends NotFoundException {

  private static final String ENTITY_NOT_FOUND = "해당 엔티티가 존재하지 않습니다.";

  public EntityNotFoundException() {
    super(ENTITY_NOT_FOUND);
  }
}