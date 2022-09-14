package com.detailretail.kurlyflow.batch.command.domain;

import com.detailretail.kurlyflow.tote.command.domain.Tote;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "batch")
  private List<Tote> totes = new ArrayList<>();

  public Batch(Long workerId) {
    this.workerId = workerId;
  }

}
