package com.detailretail.kurlyflow.admin.presentation;

import com.detailretail.kurlyflow.admin.command.application.*;
import com.detailretail.kurlyflow.admin.command.domain.CustomAdminsDetails;
import com.detailretail.kurlyflow.config.aop.CurrentUser;
import com.detailretail.kurlyflow.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminSignUpService adminSignUpService;
    private final AdminLoginService adminLoginService;
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

//    @ResponseBody
//    @PatchMapping("/place")
//    public ResponseEntity<AdminRegionResponse> modifyRegion(@CurrentUser CustomAdminsDetails admin, @RequestBody AdminSignUpRequest adminSignUpRequest){
//        Long adminId = admin.getId();
//        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtTokenProvider.getId();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return ResponseEntity.;
//            }
//            //같다면 유저네임 변경
//            AdminSignUpRequest adminSignUpRequest = new AdminSignUpRequest(userIdx,user.getUserName());
//            adminRegionService.modifyRegion(adminSignUpRequest);
//
//            String result = "";
//            return new BaseResponse<>(result);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
}
