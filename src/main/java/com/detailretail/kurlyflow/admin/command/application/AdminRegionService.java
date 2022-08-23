package com.detailretail.kurlyflow.admin.command.application;

import com.detailretail.kurlyflow.admin.command.domain.Admin;
import com.detailretail.kurlyflow.admin.command.domain.AdminRepository;
import com.detailretail.kurlyflow.admin.exception.AdminNotFoundException;
import com.detailretail.kurlyflow.admin.util.AdminConverter;
import com.detailretail.kurlyflow.common.vo.Region;
import com.detailretail.kurlyflow.worker.query.application.DetailRegionResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminRegionService {

  private final AdminRepository adminRepository;

  public void assignRegion(AdminRegionRequest adminRegionRequest, Long adminId) {
    System.out.println("admin.getId() = " + adminId);
    Admin admin = adminRepository.findById(adminId).orElseThrow(AdminNotFoundException::new);
    System.out.println("admin.getId() = " + admin.getId());
    admin.assignRegion(Region.of(adminRegionRequest.getRegion()));
  }

  public List<DetailRegionResponse> assignDetailRegion(Long adminId) {
    Admin admin = adminRepository.findWithWorkers(adminId).orElseThrow(AdminNotFoundException::new);
    admin.getWorkers().stream()
        .forEach(worker -> worker.assignDetailRegion("ASSIGN_DETAIL_REGION"));
    return admin.getWorkers().stream().map(AdminConverter::ofDetailRegion)
        .collect(Collectors.toList());
  }
}
