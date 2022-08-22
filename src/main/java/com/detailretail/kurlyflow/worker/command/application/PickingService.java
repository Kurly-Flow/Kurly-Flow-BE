package com.detailretail.kurlyflow.worker.command.application;

import com.detailretail.kurlyflow.common.vo.Phone;
import com.detailretail.kurlyflow.config.jwt.JwtTokenProvider;
import com.detailretail.kurlyflow.worker.command.application.MultiBatchResponse.BatchResponse;
import com.detailretail.kurlyflow.worker.command.domain.Batch;
import com.detailretail.kurlyflow.worker.command.domain.BatchRepository;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
import com.detailretail.kurlyflow.worker.exception.WorkerNotFoundException;
import com.detailretail.kurlyflow.worker.util.WorkerConverter;
import java.util.List;
import java.util.stream.Collectors;
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

  public WorkingPlaceLoginResponse startWork(LoginRequest loginRequest) {
    Worker worker = workerRepository.findByPhone(new Phone(loginRequest.getPhone()))
        .orElseThrow(WorkerNotFoundException::new);
    worker.matchPassword(loginRequest.getPassword());
    worker.startWork();
    return WorkerConverter.ofWorkingPlaceLogin(
        jwtTokenProvider.createToken(String.valueOf(worker.getId()),
            List.of(worker.getAuthority().name())), worker);
  }

  @Transactional(readOnly = true)
  public MultiBatchResponse getMultiPickingList(Long workerId) {
    List<Batch> pickingList = batchRepository.findTop50ByWorker_IdAndIsBarcordReadFalse(workerId);
    List<BatchResponse> batchResponses = pickingList.stream().map(WorkerConverter::ofBatch)
        .collect(Collectors.toList());
    return WorkerConverter.ofMulti(batchResponses);
  }

  public void readBarcode(Long batchId) {
    Batch batch = batchRepository.findById(batchId).orElseThrow(WorkerNotFoundException::new);
    batch.readBarcode();
  }

  public void workingToggle(Long workerId) {
    workerRepository.findById(workerId).ifPresentOrElse(worker -> {
      if (worker.getIsWorked()) {
        worker.breakWork();
      } else {
        worker.startWork();
      }
    }, () -> new WorkerNotFoundException());
  }
}
