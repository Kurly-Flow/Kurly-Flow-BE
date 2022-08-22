package com.detailretail.kurlyflow.admin.command.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TOService {
//    private final AdminRepository adminRepository;
//    private final WorkerRepository workerRepository;
//
//    public AdminResponse inputTO(TORequest toRequest, Long adminId){
//
//        RegionTO regionTO = adminRepository.findById(adminId).orElseThrow(WorkerNotFoundException::new);
//        regionTO.assignWorkingDate(RegionTO.of(toRequest.getWorkingDate()));
//        regionTO.assignWorkingTeam(RegionTO.of(toRequest.getWorkingTeam()));
//        regionTO.assignWorkingNumbers(RegionTO.of(toRequest.getWorkingNumbers()));
//
//
//        Admin admin = adminRepository.findById(adminId).orElseThrow(AdminNotFoundException::new);
//        return AdminConverter.ofTO(admin);
//        return null;
//    }
}
