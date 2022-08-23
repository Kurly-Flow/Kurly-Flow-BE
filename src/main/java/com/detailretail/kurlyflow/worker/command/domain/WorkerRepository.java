package com.detailretail.kurlyflow.worker.command.domain;

import com.detailretail.kurlyflow.common.vo.Phone;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

  Optional<Worker> findByPhone(Phone phone);

  List<Worker> findAllByIsWorkedTrueAndLoginAtBetween(LocalDateTime start, LocalDateTime end);

  //미배정받았고,출석은 한 작업자
  @Query("SELECT w FROM Worker w JOIN FETCH w.histories WHERE w.region =: UNASSIGNED AND w.isAttended = true AND w.loginAt > :beforeOneHour")
  List<Worker> findWorkerWithWorkerHistory(LocalDateTime beforeOneHour);
}
