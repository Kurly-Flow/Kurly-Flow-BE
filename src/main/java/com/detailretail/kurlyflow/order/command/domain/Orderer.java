package com.detailretail.kurlyflow.order.command.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Orderer {

  private String name;
  private String address;
}
