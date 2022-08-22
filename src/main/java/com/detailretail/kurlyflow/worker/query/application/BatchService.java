package com.detailretail.kurlyflow.worker.query.application;

import com.detailretail.kurlyflow.worker.command.domain.Batch;
import com.detailretail.kurlyflow.worker.command.domain.BatchRepository;
import com.detailretail.kurlyflow.worker.query.application.MultiBatchResponse.BatchResponse;
import com.detailretail.kurlyflow.worker.util.WorkerConverter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BatchService {

  private final BatchRepository batchRepository;

  public MultiBatchResponse getMultiPickingList(Long workerId) {
    List<Batch> pickingList = batchRepository.findTop50ByWorker_IdAndIsBarcordReadFalse(workerId);
    List<BatchResponse> batchResponses = pickingList.stream().map(WorkerConverter::ofBatch)
        .collect(Collectors.toList());
    return WorkerConverter.ofMulti(batchResponses);
  }
}
