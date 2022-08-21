package com.detailretail.kurlyflow.worker.presentation;

import com.detailretail.kurlyflow.config.aop.CurrentUser;
import com.detailretail.kurlyflow.worker.command.application.LoginRequest;
import com.detailretail.kurlyflow.worker.command.application.LoginResponse;
import com.detailretail.kurlyflow.worker.command.application.LoginService;
import com.detailretail.kurlyflow.worker.command.domain.CustomWorkerDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/picking")
@RequiredArgsConstructor
public class PickingController {

  private final LoginService loginService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    LoginResponse loginResponse = loginService.login(loginRequest);
    return ResponseEntity.ok(loginResponse);
  }

  @PreAuthorize("hasRole('WORKER')")
  @GetMapping("/multi")
  public ResponseEntity<Void> multiPickingList(@CurrentUser CustomWorkerDetails worker) {
    return ResponseEntity.ok(null);
  }
}
