package com.detailretail.kurlyflow.admin.util;

public class CalculateConverter {

  public static int getSeventy(Integer workingNumbers) {
    return (int) Math.round(((double) workingNumbers / 100) * 70);
  }
}
