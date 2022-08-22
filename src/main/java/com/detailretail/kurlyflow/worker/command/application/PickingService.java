package com.detailretail.kurlyflow.worker.command.application;

import com.detailretail.kurlyflow.common.vo.Phone;
import com.detailretail.kurlyflow.config.jwt.JwtTokenProvider;
import com.detailretail.kurlyflow.worker.command.domain.Batch;
import com.detailretail.kurlyflow.worker.command.domain.BatchRepository;
import com.detailretail.kurlyflow.worker.command.domain.Tote;
import com.detailretail.kurlyflow.worker.command.domain.ToteRepository;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
import com.detailretail.kurlyflow.worker.exception.EntityNotFoundException;
import com.detailretail.kurlyflow.worker.util.WorkerConverter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PickingService {

  private final WorkerRepository workerRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final BatchRepository batchRepository;
  private final ToteRepository toteRepository;

  public WorkingPlaceLoginResponse startWork(LoginRequest loginRequest) {
    Worker worker = workerRepository.findByPhone(new Phone(loginRequest.getPhone()))
        .orElseThrow(EntityNotFoundException::new);
    worker.matchPassword(loginRequest.getPassword());
    worker.startWork();
    return WorkerConverter.ofWorkingPlaceLogin(
        jwtTokenProvider.createToken(String.valueOf(worker.getId()),
            List.of(worker.getAuthority().name())), worker);
  }

  public void readBarcode(Long batchId, Long toteId) {
    Batch batch = batchRepository.findById(batchId).orElseThrow(EntityNotFoundException::new);
    Tote tote = toteRepository.findById(toteId).orElseThrow(EntityNotFoundException::new);
    batch.readBarcode(tote);
  }

  public void workingToggle(Long workerId) {
    workerRepository.findById(workerId).ifPresentOrElse(worker -> {
      if (worker.getIsWorked()) {
        worker.breakWork();
      } else {
        worker.startWork();
      }
    }, () -> new EntityNotFoundException());
  }
}
