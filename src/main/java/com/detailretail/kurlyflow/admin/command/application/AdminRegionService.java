package com.detailretail.kurlyflow.admin.command.application;

import com.detailretail.kurlyflow.admin.command.domain.Admin;
import com.detailretail.kurlyflow.admin.command.domain.AdminRepository;
import com.detailretail.kurlyflow.admin.exception.AdminNotFoundException;
import com.detailretail.kurlyflow.common.vo.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminRegionService {
    private final AdminRepository adminRepository;

    public void modifyRegion(AdminRegionRequest adminRegionRequest, Long adminId){
        Admin admin = adminRepository.findById(adminId).orElseThrow(AdminNotFoundException::new);
        admin.assignRegion(Region.of(adminRegionRequest.getRegion()));
    }
}
