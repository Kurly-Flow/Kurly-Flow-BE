package com.detailretail.kurlyflow.order.query.application;

import com.detailretail.kurlyflow.batch.command.domain.Batch;
import com.detailretail.kurlyflow.batch.command.domain.BatchRepository;
import com.detailretail.kurlyflow.order.command.domain.InvoiceProduct;
import com.detailretail.kurlyflow.order.command.domain.InvoiceProductRepository;
import com.detailretail.kurlyflow.order.util.InvoiceConverter;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
import com.detailretail.kurlyflow.worker.exception.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MultiPickingService {

  private final InvoiceProductRepository invoiceProductRepository;
  private final BatchRepository batchRepository;
  private final WorkerRepository workerRepository;

  public MultiBatchResponse getMultiPickingList(Long workerId) {
    Worker worker = workerRepository.findById(workerId).orElseThrow(EntityNotFoundException::new);
    Batch newBatch = batchRepository.save(new Batch(worker.getId()));
    List<InvoiceProduct> pickingList = invoiceProductRepository.findTop30NotRead();
    return InvoiceConverter.ofMulti(newBatch.getId(), pickingList);
  }
}
