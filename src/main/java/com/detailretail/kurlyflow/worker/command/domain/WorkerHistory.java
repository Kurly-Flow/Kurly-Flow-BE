package com.detailretail.kurlyflow.worker.command.domain;

import com.detailretail.kurlyflow.common.vo.Region;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "worker_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkerHistory {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "region")
  private Region region;

  @ManyToOne
  @JoinColumn(name = "worker_id")
  private Worker worker;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  public WorkerHistory(Region region, Worker worker) {
    this.region = region;
    this.worker = worker;
    this.createdAt = LocalDateTime.now();
  }

  public void setWorker(Worker worker) {
    this.worker = worker;
  }
}
