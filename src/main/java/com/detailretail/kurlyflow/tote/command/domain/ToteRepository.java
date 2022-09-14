package com.detailretail.kurlyflow.tote.command.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToteRepository extends JpaRepository<Tote, String> {

  List<Tote> findByBatchId(Long batchId);
}
