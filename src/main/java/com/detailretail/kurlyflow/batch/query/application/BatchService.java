package com.detailretail.kurlyflow.batch.query.application;

import com.detailretail.kurlyflow.batch.command.domain.Batch;
import com.detailretail.kurlyflow.batch.command.domain.BatchRepository;
import com.detailretail.kurlyflow.batch.query.application.MultiBatchResponse.BatchResponse;
import com.detailretail.kurlyflow.batch.util.BatchConverter;
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
    List<Batch> pickingList = batchRepository.findTop30ByWorker_IdAndIsBarcodeReadFalse(workerId);
    List<BatchResponse> batchResponses = pickingList.stream().map(BatchConverter::ofBatch)
        .collect(Collectors.toList());
    return BatchConverter.ofMulti(batchResponses);
  }
}
