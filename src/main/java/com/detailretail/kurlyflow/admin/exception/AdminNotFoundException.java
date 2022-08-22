package com.detailretail.kurlyflow.admin.exception;

import com.detailretail.kurlyflow.common.exception.NotFoundException;

public class AdminNotFoundException  extends NotFoundException {
    private static final String ADMIN_NOT_FOUND = "해당 관리자가 존재하지 않습니다.";

    public AdminNotFoundException  () {  super(ADMIN_NOT_FOUND);  }
}
