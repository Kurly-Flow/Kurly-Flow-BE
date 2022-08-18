package com.detailretail.kurlyflow.common.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Phone {

  private static final String PHONE_REGX = "^\\d{3}-\\d{3,4}-\\d{4}$";
  private String number;

  public Phone(String number) {
    validationPhone(number);
    this.number = number;
  }

  public String getNumber() {
    return number;
  }

  public void validationPhone(String number) {
    if (StringUtils.isBlank(number) || !number.matches(PHONE_REGX)) {
      throw new IllegalArgumentException();
    }
  }

}
