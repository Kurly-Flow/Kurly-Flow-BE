package com.detailretail.kurlyflow.batch;

import com.detailretail.kurlyflow.batch.MultiBatchResponse.BatchResponse;
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
    List<BatchResponse> batchResponses = pickingList.stream().map(BatchConverter::ofBatch)
        .collect(Collectors.toList());
    return BatchConverter.ofMulti(batchResponses);
  }
}
