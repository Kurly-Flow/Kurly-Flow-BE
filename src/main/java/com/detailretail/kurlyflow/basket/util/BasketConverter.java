package com.detailretail.kurlyflow.basket.util;

import com.detailretail.kurlyflow.basket.command.domain.Basket;
import com.detailretail.kurlyflow.basket.query.application.EndBasketInvoiceResponse;
import com.detailretail.kurlyflow.basket.query.application.EndBasketInvoiceResponse.InvoiceProductResponse;
import com.detailretail.kurlyflow.basket.query.application.EndCompleteResponse;
import com.detailretail.kurlyflow.order.command.domain.Invoice;
import com.detailretail.kurlyflow.order.command.domain.InvoiceProduct;
import java.util.stream.Collectors;

public class BasketConverter {

  public static EndCompleteResponse ofEnd(Basket basket, Invoice invoice) {
    return EndCompleteResponse.builder().basketId(basket.getId()).invoiceId(invoice.getId())
        .endAt(invoice.getEndAt()).build();
  }

  public static EndBasketInvoiceResponse ofEndInvoice(Invoice invoice) {
    return EndBasketInvoiceResponse.builder().invoiceId(invoice.getId())
        .ordererName(invoice.getOrder().getOrderer().getName())
        .ordererAddress(invoice.getOrder().getOrderer().getAddress()).products(
            invoice.getInvoiceProducts().stream().map(BasketConverter::ofInvoiceProduct)
                .collect(Collectors.toList())).build();
  }

  public static InvoiceProductResponse ofInvoiceProduct(InvoiceProduct invoiceProduct) {
    return InvoiceProductResponse.builder().invoiceProductId(invoiceProduct.getId())
        .name(invoiceProduct.getProduct().getName()).quantity(invoiceProduct.getQuantity()).build();
  }
}
