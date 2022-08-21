package com.detailretail.kurlyflow.worker.command.domain;

import com.detailretail.kurlyflow.common.vo.Phone;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

  Optional<Worker> findByPhone(Phone phone);

  List<Worker> findAllByIsWorkedTrueAndLoginAtBetween(LocalDateTime start, LocalDateTime end);
}
