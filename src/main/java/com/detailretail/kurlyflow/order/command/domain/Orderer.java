package com.detailretail.kurlyflow.order.command.domain;

import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Orderer {

  private String name;
  private String address;
}
