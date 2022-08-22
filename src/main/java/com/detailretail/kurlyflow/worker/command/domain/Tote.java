package com.detailretail.kurlyflow.worker.command.domain;

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
@Table(name = "tote")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tote {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  private String name;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tote")
  private List<Batch> batches = new ArrayList<>();
}