package com.detailretail.kurlyflow.batch.command.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BatchRepository extends JpaRepository<Batch, Long> {

  @Query(value = "SELECT b FROM batch as b WHERE b.worker_id = :workerId ", nativeQuery = true)
  Optional<Batch> findByCurrentTote(Long workerId);

  @Query(value = "SELECT b FROM batch as b join tote as t on b.id = t.batch_id  WHERE b.worker_id = :workerId ", nativeQuery = true)
  Optional<Batch> findFirstByTotesIsNullAndWorkerId(Long workerId);
}

