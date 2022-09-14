package com.detailretail.kurlyflow.basket.command.domain;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

  @ElementCollection
  @CollectionTable(name = "basket_invoice", joinColumns = @JoinColumn(name = "id"))
  private List<String> invoices = new LinkedList<>();

}