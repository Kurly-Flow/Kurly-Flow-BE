package com.detailretail.kurlyflow.admin.util;

public class CalculateConverter {

  public static long getSeventy(Integer workingNumbers) {
    return Math.round(((double) workingNumbers / 100) * 70);
  }
}
