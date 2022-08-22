package com.detailretail.kurlyflow.order.util;

import com.detailretail.kurlyflow.order.command.domain.Invoice;
import com.detailretail.kurlyflow.order.command.domain.InvoiceProduct;
import com.detailretail.kurlyflow.order.query.application.InvoiceResponse;
import com.detailretail.kurlyflow.order.query.application.InvoiceResponse.InvoiceProductResponse;
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
}