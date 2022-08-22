package com.detailretail.kurlyflow.admin.command.application;

import com.detailretail.kurlyflow.admin.command.domain.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TOService {
    private final AdminRepository adminRepository;

    public AdminResponse inputTO(TORequest toRequest, Long adminId){

//        Admin admin = adminRepository.findById(adminId).orElseThrow(AdminNotFoundException::new);
//
//        admin.assignWorkingDate(Region.of(toRequest.getWorkingDate()));
//        admin.assignWorkingTeam(Region.of(toRequest.getWorkingTeam()));
//        admin.assignWorkingNumber(Region.of(toRequest.getWorkingNumbers()));
//
//        return WorkerConverter.ofTO(admin);
        return null;
    }
}
