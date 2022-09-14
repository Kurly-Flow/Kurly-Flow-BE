package com.detailretail.kurlyflow.batch.command.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "batch")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Batch {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "worker_id")
  private Long workerId;


  public Batch(Long workerId) {
    this.workerId = workerId;
  }

}
