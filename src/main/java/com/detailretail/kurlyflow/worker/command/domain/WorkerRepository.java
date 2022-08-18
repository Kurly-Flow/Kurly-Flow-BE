package com.detailretail.kurlyflow.worker.command.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

  Optional<Worker> findByPhone(String phone);
}
