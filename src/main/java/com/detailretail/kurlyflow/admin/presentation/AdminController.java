package com.detailretail.kurlyflow.admin.presentation;

import com.detailretail.kurlyflow.admin.command.application.AdminLoginRequest;
import com.detailretail.kurlyflow.admin.command.application.AdminLoginResponse;
import com.detailretail.kurlyflow.admin.command.application.AdminLoginService;
import com.detailretail.kurlyflow.admin.command.application.AdminRegionRequest;
import com.detailretail.kurlyflow.admin.command.application.AdminRegionService;
import com.detailretail.kurlyflow.admin.command.application.AdminSignUpRequest;
import com.detailretail.kurlyflow.admin.command.application.AdminSignUpService;
import com.detailretail.kurlyflow.admin.command.application.TORequest;
import com.detailretail.kurlyflow.admin.command.application.TOService;
import com.detailretail.kurlyflow.admin.command.domain.CustomDetails;
import com.detailretail.kurlyflow.admin.query.application.AdminQueryService;
import com.detailretail.kurlyflow.admin.query.application.WorkerAttendanceResponse;
import com.detailretail.kurlyflow.admin.query.application.WorkerStatusResponse;
import com.detailretail.kurlyflow.config.aop.CurrentUser;
import com.detailretail.kurlyflow.worker.query.application.DetailRegionResponse;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

  private final AdminSignUpService adminSignUpService;
  private final AdminLoginService adminLoginService;
  private final AdminRegionService adminRegionService;
  private final AdminQueryService adminQueryService;
  private final TOService toService;


  @PostMapping("/signup")
  public ResponseEntity<Void> adminSignUp(@RequestBody AdminSignUpRequest adminSignUpRequest) {
    Long adminId = adminSignUpService.adminSignUp(adminSignUpRequest);
    return ResponseEntity.created(URI.create("/api/admins/" + adminId)).body(null);
  }


  @PostMapping("/login")
  public ResponseEntity<AdminLoginResponse> adminLogin(
      @RequestBody AdminLoginRequest adminLoginRequest) {
    AdminLoginResponse adminLoginResponse = adminLoginService.adminLogin(adminLoginRequest);
    return ResponseEntity.ok(adminLoginResponse);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/region")
  public ResponseEntity<Void> selectRegion(@CurrentUser CustomDetails admin,
      @RequestBody AdminRegionRequest adminRegionRequest) {
    adminRegionService.assignRegion(adminRegionRequest, admin.getId());
    return ResponseEntity.ok(null);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<List<WorkerStatusResponse>> getWorkerStatus(
      @CurrentUser CustomDetails admin) {
    List<WorkerStatusResponse> workerStatus = adminQueryService.getWorkerStatus(admin.getId());
    return ResponseEntity.ok(workerStatus);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/to")
  public ResponseEntity<Void> inputTo(@CurrentUser CustomDetails admin,
      @RequestBody TORequest toRequest) {
    toService.inputTO(toRequest, admin.getId());
    return ResponseEntity.ok(null);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/assignment")
  public ResponseEntity<Void> workerAssignment(@CurrentUser CustomDetails admin) {
    toService.assignWorkers(admin.getId());
    return ResponseEntity.ok(null);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/attendance")
  public ResponseEntity<List<WorkerAttendanceResponse>> checkAttendance(
      @CurrentUser CustomDetails admin) {
    List<WorkerAttendanceResponse> workers = adminQueryService.getWorkers(admin.getId());
    return ResponseEntity.ok(workers);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/detail")
  public ResponseEntity<List<DetailRegionResponse>> getDetails(@CurrentUser CustomDetails admin) {
    List<DetailRegionResponse> workers = adminQueryService.getDetailRegionForWorkers(admin.getId());
    return ResponseEntity.ok(workers);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/detail")
  public ResponseEntity<List<DetailRegionResponse>> assignWorkers(
      @CurrentUser CustomDetails admin) {
    List<DetailRegionResponse> workers = adminRegionService.assignDetailRegion(admin.getId());
    return ResponseEntity.ok(workers);
  }

}
