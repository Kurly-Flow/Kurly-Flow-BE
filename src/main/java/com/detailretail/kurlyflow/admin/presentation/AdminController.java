package com.detailretail.kurlyflow.admin.presentation;

import com.detailretail.kurlyflow.admin.command.application.AdminLoginRequest;
import com.detailretail.kurlyflow.admin.command.application.AdminLoginResponse;
import com.detailretail.kurlyflow.admin.command.application.AdminLoginService;
import com.detailretail.kurlyflow.admin.command.application.AdminMainResponse;
import com.detailretail.kurlyflow.admin.command.application.AdminMainService;
import com.detailretail.kurlyflow.admin.command.application.AdminRegionRequest;
import com.detailretail.kurlyflow.admin.command.application.AdminRegionService;
import com.detailretail.kurlyflow.admin.command.application.AdminResponse;
import com.detailretail.kurlyflow.admin.command.application.AdminSignUpRequest;
import com.detailretail.kurlyflow.admin.command.application.AdminSignUpService;
import com.detailretail.kurlyflow.admin.command.application.TORequest;
import com.detailretail.kurlyflow.admin.command.application.TOService;
import com.detailretail.kurlyflow.admin.command.domain.CustomAdminsDetails;
import com.detailretail.kurlyflow.config.aop.CurrentUser;
import com.detailretail.kurlyflow.config.jwt.JwtTokenProvider;
import java.net.URI;
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
    private final AdminMainService adminMainService;
    private final TOService toService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/signup")
    public ResponseEntity<Void> adminSignUp(@RequestBody AdminSignUpRequest adminSignUpRequest) {
        Long adminId = adminSignUpService.adminSignUp(adminSignUpRequest);
        return ResponseEntity.created(URI.create("/api/admins/" + adminId)).body(null);
    }


    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> adminLogin(@RequestBody AdminLoginRequest adminLoginRequest){
        AdminLoginResponse adminLoginResponse = adminLoginService.adminLogin(adminLoginRequest);
        return ResponseEntity.ok(adminLoginResponse);
    }

    /**
     * 관리자 근무지 선택
     * @param admin
     * @param adminRegionRequest
     * @return void
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/region")
    public ResponseEntity<Void> modifyRegion(@CurrentUser CustomAdminsDetails admin, @RequestBody AdminRegionRequest adminRegionRequest){

        //존재하는 idx인지 확인
        Long adminIdx = admin.getId();
        // region 변경
        adminRegionService.modifyRegion(adminRegionRequest, adminIdx);
        return ResponseEntity.ok(null);
    }

    /**
     * 관리자 메인
     * @param admin
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public void adminMain(@CurrentUser CustomAdminsDetails admin){
        AdminMainResponse adminMainResponse = adminMainService.checkWorkersRegion(admin.getId());
    }

    /**
     * TO 입력
     * @param admin
     * @param toRequest
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/to")
    public ResponseEntity<AdminResponse> inputTO(@CurrentUser CustomAdminsDetails admin, @RequestBody TORequest toRequest){
        //존재하는 idx인지 확인
        Long adminIdx = admin.getId();
        // region 변경
        AdminResponse adminResponse = toService.inputTO(toRequest, adminIdx);
        return ResponseEntity.ok(null);
    }




}
