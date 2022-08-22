package com.detailretail.kurlyflow.product.command.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "weight")
  private Double weight;

  @Column(name = "region")
  private String region;

  @Column(name = "location")
  private String location;

  @Enumerated(EnumType.STRING)
  @Column(name = "packaging")
  private Packaging packaging = Packaging.없음;
}