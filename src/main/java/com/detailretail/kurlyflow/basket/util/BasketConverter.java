package com.detailretail.kurlyflow.basket.util;

import com.detailretail.kurlyflow.basket.command.domain.Basket;
import com.detailretail.kurlyflow.basket.query.application.EndBasketInvoiceResponse;
import com.detailretail.kurlyflow.basket.query.application.EndCompleteResponse;
import com.detailretail.kurlyflow.order.command.domain.Invoice;
import com.detailretail.kurlyflow.order.command.domain.InvoiceProduct;

public class BasketConverter {

  public static EndCompleteResponse ofEnd(Basket basket, Invoice invoice) {
    return EndCompleteResponse.builder().basketId(basket.getId()).invoiceId(invoice.getId())
        .endAt(invoice.getEndAt()).build();
  }

  public static EndBasketInvoiceResponse ofEndInvoice(Invoice invoice,
      InvoiceProduct invoiceProduct) {
    return EndBasketInvoiceResponse.builder().name(invoiceProduct.getProduct().getName())
        .quantity(invoiceProduct.getQuantity()).build();
  }
}
