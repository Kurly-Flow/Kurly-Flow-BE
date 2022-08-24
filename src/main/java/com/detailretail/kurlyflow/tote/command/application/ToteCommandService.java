package com.detailretail.kurlyflow.tote.command.application;

import com.detailretail.kurlyflow.batch.command.domain.Batch;
import com.detailretail.kurlyflow.batch.command.domain.BatchRepository;
import com.detailretail.kurlyflow.order.command.domain.InvoiceProduct;
import com.detailretail.kurlyflow.order.command.domain.InvoiceProductRepository;
import com.detailretail.kurlyflow.tote.command.domain.Tote;
import com.detailretail.kurlyflow.tote.command.domain.ToteRepository;
import com.detailretail.kurlyflow.worker.exception.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ToteCommandService {

  private final ToteRepository toteRepository;
  private final BatchRepository batchRepository;
  private final InvoiceProductRepository invoiceProductRepository;

  public void assignTote(ToteRequest toteRequest, Long workerId) {
    Batch batch = batchRepository.findFirstByTotesIsNullAndWorker_Id(workerId)
        .orElseThrow(EntityNotFoundException::new);
    toteRepository.save(new Tote(toteRequest.getToteId(), batch));
  }

  public void moveTote(ToteMoveRequest toteMoveRequest) {
    Tote tote = toteRepository.findById(toteMoveRequest.getNewToteId())
        .orElseThrow(EntityNotFoundException::new);
    List<InvoiceProduct> moveList = invoiceProductRepository.findByIdIn(
        toteMoveRequest.getInvoiceProductIds());
    moveList.stream().forEach(invoiceProduct -> invoiceProduct.moveTote(tote));
  }
}
