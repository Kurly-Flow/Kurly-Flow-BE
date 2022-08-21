package com.detailretail.kurlyflow.worker.command.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Batch, Long> {

  List<Batch> findTop50ByWorker_IdAndIsBarcordReadFalse(Long workerId);
}
