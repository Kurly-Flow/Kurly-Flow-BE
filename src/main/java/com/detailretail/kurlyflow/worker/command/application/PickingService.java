package com.detailretail.kurlyflow.worker.command.application;

import com.detailretail.kurlyflow.worker.command.domain.Batch;
import com.detailretail.kurlyflow.worker.command.domain.BatchRepository;
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

  private final BatchRepository batchRepository;

  public List<MultiBatchResponse> getMultiPickingList(Long workerId) {
    List<Batch> pickingList = batchRepository.findTop50ByWorker_IdAndIsBarcordReadFalse(workerId);
    return pickingList.stream().map(WorkerConverter::ofMultiBatch).collect(Collectors.toList());
  }

  public void readBarcode(Long batchId) {
    Batch batch = batchRepository.findById(batchId).orElseThrow(WorkerNotFoundException::new);
    batch.readBarcode();
  }
}
