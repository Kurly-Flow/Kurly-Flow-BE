package com.detailretail.kurlyflow.worker.presentation;

import com.detailretail.kurlyflow.admin.command.domain.CustomDetails;
import com.detailretail.kurlyflow.config.aop.CurrentUser;
import com.detailretail.kurlyflow.worker.command.application.AdminCallService;
import com.detailretail.kurlyflow.worker.command.application.AttendanceService;
import com.detailretail.kurlyflow.worker.command.application.InputRequest;
import com.detailretail.kurlyflow.worker.command.application.InputService;
import com.detailretail.kurlyflow.worker.command.application.LoginRequest;
import com.detailretail.kurlyflow.worker.command.application.LoginResponse;
import com.detailretail.kurlyflow.worker.command.application.SignUpRequest;
import com.detailretail.kurlyflow.worker.command.application.SignUpService;
import com.detailretail.kurlyflow.worker.command.application.WorkerLoginService;
import com.detailretail.kurlyflow.worker.command.application.WorkerStartService;
import com.detailretail.kurlyflow.worker.query.application.CheckRegionService;
import com.detailretail.kurlyflow.worker.query.application.DetailRegionResponse;
import com.detailretail.kurlyflow.worker.query.application.InfoResponse;
import com.detailretail.kurlyflow.worker.query.application.InfoService;
import com.detailretail.kurlyflow.worker.query.application.RegionResponse;
import java.io.IOException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workers")
@RequiredArgsConstructor
public class WorkerController {

  private final SignUpService signUpService;
  private final WorkerStartService workerStartService;
  private final WorkerLoginService workerLoginService;
  private final InputService inputService;
  private final AttendanceService attendanceService;
  private final CheckRegionService checkRegionService;
  private final InfoService infoService;
  private final AdminCallService adminCallService;

  @PostMapping("/call")
  public ResponseEntity pushMessage(@CurrentUser CustomDetails worker,
      @RequestHeader(value = "targetToken") String targetToken) throws IOException {
    adminCallService.sendMessageTo(targetToken, worker.getId());
    return ResponseEntity.ok().build();
  }

  @PreAuthorize("hasRole('WORKER')")
  @GetMapping("/my")
  public ResponseEntity<InfoResponse> getMyInfo(@CurrentUser CustomDetails worker) {
    InfoResponse workerInfo = infoService.findWorkerInfo(worker.getId());
    return ResponseEntity.ok(workerInfo);
  }


  @PostMapping("/signup")
  public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
    Long workerId = signUpService.signUp(signUpRequest);
    return ResponseEntity.created(URI.create("/api/workers/" + workerId)).body(null);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    LoginResponse loginResponse = workerLoginService.login(loginRequest);
    return ResponseEntity.ok(loginResponse);
  }

  @PreAuthorize("hasRole('WORKER')")
  @PostMapping
  public ResponseEntity<Void> inputEmployeeNumberAndWishRegion(
      @RequestBody InputRequest inputRequest, @CurrentUser CustomDetails worker) {
    inputService.inputEmployeeNumberAndWishRegion(inputRequest, worker.getId());
    return ResponseEntity.ok(null);
  }

  @PreAuthorize("hasRole('WORKER')")
  @GetMapping
  public ResponseEntity<RegionResponse> checkRegion(@CurrentUser CustomDetails worker) {
    RegionResponse regionResponse = checkRegionService.checkRegion(worker.getId());
    return ResponseEntity.ok(regionResponse);
  }

  @PreAuthorize("hasRole('WORKER')")
  @GetMapping("/region")
  public ResponseEntity<DetailRegionResponse> checkDetailRegion(@CurrentUser CustomDetails worker) {
    DetailRegionResponse detailRegionResponse = checkRegionService.checkDetailRegion(
        worker.getId());
    return ResponseEntity.ok(detailRegionResponse);
  }

  @PreAuthorize("hasRole('WORKER')")
  @PostMapping("/attendance")
  public ResponseEntity<Void> attend(@CurrentUser CustomDetails worker) {
    attendanceService.attend(worker.getId());
    return ResponseEntity.ok(null);
  }
}