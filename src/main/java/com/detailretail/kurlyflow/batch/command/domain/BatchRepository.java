package com.detailretail.kurlyflow.batch.command.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BatchRepository extends JpaRepository<Batch, Long> {

  @Query("SELECT b FROM Batch b JOIN FETCH b.totes bt WHERE b.worker.id = :workerId ")
  Optional<Batch> findByCurrentTote(Long workerId);

  Optional<Batch> findFirstByTotesIsNullAndWorker_Id(Long workerId);
}
