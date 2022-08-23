package com.detailretail.kurlyflow.batch.util;

import static com.detailretail.kurlyflow.common.ToteWeightPolicy.MAX_TOTE_WEIGHT;

import com.detailretail.kurlyflow.batch.command.domain.Batch;
import com.detailretail.kurlyflow.batch.query.application.MultiBatchResponse;
import com.detailretail.kurlyflow.batch.query.application.MultiBatchResponse.BatchResponse;
import java.util.List;

public class BatchConverter {


  public static BatchResponse ofBatch(Batch batch) {
    return BatchResponse.builder().batchId(batch.getId())
        .name(batch.getInvoiceProduct().getProduct().getName())
        .quantity(batch.getInvoiceProduct().getQuantity())
        .weight(batch.getInvoiceProduct().getProduct().getWeight())
        .imageUrl(batch.getInvoiceProduct().getProduct().getImageUrl())
        .region(batch.getInvoiceProduct().getProduct().getRegion())
        .location(batch.getInvoiceProduct().getProduct().getLocation()).build();
  }

  public static MultiBatchResponse ofMulti(List<BatchResponse> batchResponses) {
    return MultiBatchResponse.builder().recommendToteCount(calculateTote(batchResponses))
        .batchResponses(batchResponses).build();
  }

  private static Integer calculateTote(List<BatchResponse> batchResponses) {
    double sum = batchResponses.stream()
        .mapToDouble(batchResponse -> batchResponse.getWeight() * batchResponse.getQuantity())
        .sum();
    return (int) Math.round(sum / MAX_TOTE_WEIGHT.getWeight());
  }
}
