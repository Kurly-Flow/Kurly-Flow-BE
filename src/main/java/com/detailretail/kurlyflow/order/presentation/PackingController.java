package com.detailretail.kurlyflow.order.presentation;

import com.detailretail.kurlyflow.order.command.application.InvoiceConsistencyService;
import com.detailretail.kurlyflow.order.query.application.InvoiceResponse;
import com.detailretail.kurlyflow.order.query.application.PackingService;
import com.detailretail.kurlyflow.worker.command.application.LoginRequest;
import com.detailretail.kurlyflow.worker.command.application.WorkerStartService;
import com.detailretail.kurlyflow.worker.command.application.WorkingPlaceLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/packing")
@RequiredArgsConstructor
public class PackingController {

  private final WorkerStartService workerStartService;
  private final PackingService packingService;
  private final InvoiceConsistencyService invoiceConsistencyService;

  @PostMapping("/login")
  public ResponseEntity<WorkingPlaceLoginResponse> login(@RequestBody LoginRequest loginRequest) {
    WorkingPlaceLoginResponse loginResponse = workerStartService.startWork(loginRequest);
    return ResponseEntity.ok(loginResponse);
  }

  @GetMapping("/{invoiceId}")
  public ResponseEntity<InvoiceResponse> getInvoice(@PathVariable("invoiceId") String invoiceId) {
    InvoiceResponse invoiceForPacking = packingService.getInvoiceForPacking(invoiceId);
    return ResponseEntity.ok(invoiceForPacking);
  }

  @PostMapping("/{invoiceId}")
  public ResponseEntity<Void> invoiceInconsistency(@PathVariable("invoiceId") String invoiceId) {
    invoiceConsistencyService.changeInvoiceUnConsistency(invoiceId);
    return ResponseEntity.ok(null);
  }
}
