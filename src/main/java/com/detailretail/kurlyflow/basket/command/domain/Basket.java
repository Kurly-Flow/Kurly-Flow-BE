package com.detailretail.kurlyflow.basket.command.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "basket")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Basket {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "worker_id")
  private Long workerId;

}