package com.detailretail.kurlyflow.worker.infra;

public class FirebaseServerErrorException extends RuntimeException {

  private static final String FIREBASE_SERVER_ERROR = "Firebase server에 문제가 있습니다.";


  public FirebaseServerErrorException() {
    super(FIREBASE_SERVER_ERROR);
  }
}
