package com.detailretail.kurlyflow.tote.command.domain;

import com.detailretail.kurlyflow.batch.command.domain.Batch;
import javax.persistence.Column;
import javax.persistence.Entity;
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
  @Column(name = "id")
  private String id;

  @Column(name = "batch_id")
  private Long batchId;

  public Tote(String id, Batch batch) {
    this.id = id;
    this.batchId = batch.getId();
  }

}