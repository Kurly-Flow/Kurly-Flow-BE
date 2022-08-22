package com.detailretail.kurlyflow.worker.command.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tote")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tote {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  private String name;
}