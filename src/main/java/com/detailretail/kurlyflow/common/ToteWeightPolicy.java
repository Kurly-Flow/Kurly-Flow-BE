package com.detailretail.kurlyflow.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ToteWeightPolicy {
  MAX_TOTE_WEIGHT(8000);

  private final Integer weight;
}
