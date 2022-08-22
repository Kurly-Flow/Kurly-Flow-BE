package com.detailretail.kurlyflow.order.presentation;

import com.detailretail.kurlyflow.order.query.application.EndService;
import com.detailretail.kurlyflow.config.aop.CurrentUser;
import com.detailretail.kurlyflow.worker.command.domain.CustomWorkerDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/end")
@RequiredArgsConstructor
public class EndController {

  private final EndService endService;

  @PreAuthorize("hasRole('WORKER')")
  @GetMapping
  public ResponseEntity<Void> getInvoices(@CurrentUser CustomWorkerDetails worker) {
    endService.getInvoices(worker.getId());
    return ResponseEntity.ok(null);
  }
}
