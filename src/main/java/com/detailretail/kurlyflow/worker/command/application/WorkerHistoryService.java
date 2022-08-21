package com.detailretail.kurlyflow.worker.command.application;

import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.command.domain.WorkerHistory;
import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class WorkerHistoryService {

  private final WorkerRepository workerRepository;

  @Scheduled(cron = "0 0 5 * * *") //새벽 5시에 상태 변경
  public void saveWorkerHistory() {
    List<Worker> workers = workerRepository.findAllByIsWorkedTrueAndLoginAtBetween(
        LocalDateTime.now().minusDays(1L), LocalDateTime.now());
    workers.stream()
        .forEach(worker -> worker.addHistory(new WorkerHistory(worker.getRegion(), worker)));
  }
}
