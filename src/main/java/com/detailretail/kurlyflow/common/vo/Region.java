package com.detailretail.kurlyflow.common.vo;

import java.util.Arrays;

public enum Region {
  PICKING, QPS, END, PACKING, UNASSIGNED;

  public static Region of(String input) {
    return Arrays.stream(Region.values()).filter(region -> region.name().equals(input)).findFirst()
        .orElseThrow(RegionNotMatchException::new);
  }
}
