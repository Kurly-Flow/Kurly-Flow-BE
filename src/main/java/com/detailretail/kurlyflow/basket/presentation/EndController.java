package com.detailretail.kurlyflow.basket.presentation;

import com.detailretail.kurlyflow.admin.command.domain.CustomDetails;
import com.detailretail.kurlyflow.basket.query.application.EndBasketInvoiceResponse;
import com.detailretail.kurlyflow.basket.query.application.EndCompleteResponse;
import com.detailretail.kurlyflow.basket.query.application.EndService;
import com.detailretail.kurlyflow.config.aop.CurrentUser;
import com.detailretail.kurlyflow.worker.command.application.LoginRequest;
import com.detailretail.kurlyflow.worker.command.application.LoginService;
import com.detailretail.kurlyflow.worker.command.application.WorkingPlaceLoginResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/end")
@RequiredArgsConstructor
public class EndController {

  private final EndService endService;
  private final LoginService loginService;

  @PostMapping("/login")
  public ResponseEntity<WorkingPlaceLoginResponse> login(@RequestBody LoginRequest loginRequest) {
    WorkingPlaceLoginResponse loginResponse = loginService.startWork(loginRequest);
    return ResponseEntity.ok(loginResponse);
  }

  @PreAuthorize("hasRole('WORKER')")
  @GetMapping
  public ResponseEntity<List<EndCompleteResponse>> getEndStatus(
      @CurrentUser CustomDetails worker) {
    List<EndCompleteResponse> endCompleteResponses = endService.getEndStatus(worker.getId());
    return ResponseEntity.ok(endCompleteResponses);
  }

  @PreAuthorize("hasRole('WORKER')")
  @GetMapping("/{invoiceId}")
  public ResponseEntity<List<EndBasketInvoiceResponse>> getInvoice(
      @PathVariable("invoiceId") String invoiceId) {
    List<EndBasketInvoiceResponse> endBasketInvoiceResponses = endService.getInvoice(invoiceId);
    return ResponseEntity.ok(endBasketInvoiceResponses);
  }
}
