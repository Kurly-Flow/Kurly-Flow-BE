package com.detailretail.kurlyflow.common.converter;


import com.detailretail.kurlyflow.common.vo.Phone;
import javax.persistence.AttributeConverter;

public class PhoneConverter implements AttributeConverter<Phone, String> {

  @Override
  public String convertToDatabaseColumn(Phone phone) {
    return phone == null ? null : phone.getNumber();
  }

  @Override
  public Phone convertToEntityAttribute(String number) {
    return number == null ? null : new Phone(number);
  }
}