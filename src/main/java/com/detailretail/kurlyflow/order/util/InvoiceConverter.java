package com.detailretail.kurlyflow.order.util;

import static com.detailretail.kurlyflow.common.ToteWeightPolicy.MAX_TOTE_WEIGHT;

import com.detailretail.kurlyflow.order.command.domain.Invoice;
import com.detailretail.kurlyflow.order.command.domain.InvoiceProduct;
import com.detailretail.kurlyflow.order.query.application.InvoiceResponse;
import com.detailretail.kurlyflow.order.query.application.InvoiceResponse.InvoiceProductResponse;
import com.detailretail.kurlyflow.order.query.application.MultiBatchResponse;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceConverter {

  public static InvoiceProductResponse ofInvoiceProduct(InvoiceProduct invoiceProduct) {
    return InvoiceProductResponse.builder().invoiceProductId(invoiceProduct.getId())
        .name(invoiceProduct.getProduct().getName()).quantity(invoiceProduct.getQuantity())
        .packaging(invoiceProduct.getProduct().getPackaging()).build();
  }

  public static InvoiceResponse ofInvoice(Invoice invoice) {
    return InvoiceResponse.builder().id(invoice.getId()).products(
        invoice.getInvoiceProducts().stream().map(InvoiceConverter::ofInvoiceProduct)
            .collect(Collectors.toList())).build();
  }

  public static MultiBatchResponse.InvoiceProductResponse ofMultiInvoiceProduct(
      InvoiceProduct invoiceProduct) {
    return MultiBatchResponse.InvoiceProductResponse.builder()
        .invoiceProductId(invoiceProduct.getId()).name(invoiceProduct.getProduct().getName())
        .quantity(invoiceProduct.getQuantity()).weight(invoiceProduct.getProduct().getWeight())
        .imageUrl(invoiceProduct.getProduct().getImageUrl())
        .region(invoiceProduct.getProduct().getRegion())
        .location(invoiceProduct.getProduct().getLocation()).build();
  }

  public static MultiBatchResponse ofMulti(Long batchId,List<InvoiceProduct> invoiceProducts) {
    return MultiBatchResponse.builder().recommendToteCount(calculateTote(invoiceProducts))
        .batchId(batchId)
        .invoiceProductResponses(
            invoiceProducts.stream().map(InvoiceConverter::ofMultiInvoiceProduct)
                .collect(Collectors.toList())).build();
  }

  private static Integer calculateTote(List<InvoiceProduct> invoiceProducts) {
    double sum = invoiceProducts.stream().mapToDouble(
            invoiceProduct -> invoiceProduct.getQuantity() * invoiceProduct.getProduct().getWeight())
        .sum();
    return (int) Math.round(sum / (double) MAX_TOTE_WEIGHT.getWeight());
  }
}