package com.detailretail.kurlyflow.tote.command.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToteRepository extends JpaRepository<Tote, Long> {

  Optional<Tote> findByBatchesNull();
}
