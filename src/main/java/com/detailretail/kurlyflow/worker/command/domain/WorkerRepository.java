package com.detailretail.kurlyflow.worker.command.domain;

import com.detailretail.kurlyflow.common.vo.Phone;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

  Optional<Worker> findByPhone(Phone phone);
}
