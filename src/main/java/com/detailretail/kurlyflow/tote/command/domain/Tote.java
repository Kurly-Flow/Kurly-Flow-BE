package com.detailretail.kurlyflow.tote.command.domain;

import com.detailretail.kurlyflow.batch.command.domain.Batch;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

  @ManyToOne
  @JoinColumn(name = "batch_id")
  private Batch batch;

  public Tote(String id, Batch batch) {
    this.id = id;
    this.batch = batch;
  }

  public void setBatch(Batch batch) {
    this.batch = batch;
  }
}