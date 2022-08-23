package com.detailretail.kurlyflow.worker.command.application;

import java.io.IOException;

public interface AdminCallService {

  public void sendMessageTo(String targetToken, String adminId) throws IOException;
}
