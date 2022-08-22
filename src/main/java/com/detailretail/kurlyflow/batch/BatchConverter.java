package com.detailretail.kurlyflow.batch;

import com.detailretail.kurlyflow.batch.MultiBatchResponse.BatchResponse;
import java.util.List;

public class BatchConverter {

  private static final double MAX_TOTE_WEIGHT = 8.0;

  public static BatchResponse ofBatch(Batch batch) {
    return BatchResponse.builder().batchId(batch.getId())
        .name(batch.getInvoiceProduct().getProduct().getName())
        .quantity(batch.getInvoiceProduct().getQuantity())
        .weight(batch.getInvoiceProduct().getProduct().getWeight())
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
    return (int) Math.round(sum / MAX_TOTE_WEIGHT);
  }
}
