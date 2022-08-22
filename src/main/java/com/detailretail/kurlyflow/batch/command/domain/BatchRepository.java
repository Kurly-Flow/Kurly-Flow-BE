package com.detailretail.kurlyflow.batch.command.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BatchRepository extends JpaRepository<Batch, Long> {

  List<Batch> findTop50ByWorker_IdAndIsBarcordReadFalse(Long workerId);

  @Query(value =
      "select b "
          + "from batch as b "
          + "left join worker as w on b.worker_id = w.id "
          + "left join tote as t on b.tote_id  = t.id  "
          + "where b.worker_id = :workerId and b.tote_id = (select b.tote_id from batch as b where b.worker_id = :workerId order by b.read_at desc limit 1)", nativeQuery = true)
  List<Batch> findByCurrentTote(@Param(value = "workerId") Long workerId);
}
