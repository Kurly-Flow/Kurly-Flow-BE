package com.detailretail.kurlyflow.worker.presentation;

import com.detailretail.kurlyflow.batch.query.application.BatchService;
import com.detailretail.kurlyflow.batch.query.application.MultiBatchResponse;
import com.detailretail.kurlyflow.config.aop.CurrentUser;
import com.detailretail.kurlyflow.tote.command.application.ToteCommandService;
import com.detailretail.kurlyflow.tote.query.application.ToteQueryService;
import com.detailretail.kurlyflow.worker.command.application.LoginRequest;
import com.detailretail.kurlyflow.worker.command.application.LoginService;
import com.detailretail.kurlyflow.worker.command.application.PickingService;
import com.detailretail.kurlyflow.worker.command.application.WorkingPlaceLoginResponse;
import com.detailretail.kurlyflow.worker.command.domain.CustomWorkerDetails;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/picking")
@RequiredArgsConstructor
public class PickingController {

  private final LoginService loginService;
  private final PickingService pickingService;
  private final BatchService batchService;
  private final ToteQueryService toteQueryService;
  private final ToteCommandService toteCommandService;

  @PostMapping("/login")
  public ResponseEntity<WorkingPlaceLoginResponse> login(@RequestBody LoginRequest loginRequest) {
    WorkingPlaceLoginResponse loginResponse = loginService.startWork(loginRequest);
    return ResponseEntity.ok(loginResponse);
  }

  @PreAuthorize("hasRole('WORKER')")
  @GetMapping("/multi")
  public ResponseEntity<MultiBatchResponse> multiPickingList(
      @CurrentUser CustomWorkerDetails worker) {
    MultiBatchResponse multiPickingList = batchService.getMultiPickingList(worker.getId());
    return ResponseEntity.ok(multiPickingList);
  }

  @PreAuthorize("hasRole('WORKER')")
  @GetMapping("/bacord/{batchId}")
  public ResponseEntity<Void> readBarcode(@PathVariable("batchId") Long batchId,
      @RequestParam(name = "toteId") Long toteId) {
    pickingService.readBarcode(batchId, toteId);
    return ResponseEntity.ok(null);
  }

  @PreAuthorize("hasRole('WORKER')")
  @GetMapping()
  public ResponseEntity<Void> workingToggle(@CurrentUser CustomWorkerDetails worker) {
    pickingService.workingToggle(worker.getId());
    return ResponseEntity.ok(null);
  }

  @PreAuthorize("hasRole('WORKER')")
  @GetMapping("/tote")
  public ResponseEntity<String> getTote(@CurrentUser CustomWorkerDetails worker) {
    String toteId = toteQueryService.getTote(worker.getId());
    return ResponseEntity.ok(toteId);
  }

  @PreAuthorize("hasRole('WORKER')")
  @PostMapping("/tote")
  public ResponseEntity<String> assignTote(@RequestBody ToteRequest toteRequest,
      @CurrentUser CustomWorkerDetails worker) {
    toteCommandService.assignTote(toteRequest, worker.getId());
    return ResponseEntity.created(URI.create("/api/picking/tote")).body(null);
  }
}
