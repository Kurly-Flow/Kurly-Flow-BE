package com.detailretail.kurlyflow.admin.command.application;

import com.detailretail.kurlyflow.admin.command.domain.Admin;
import com.detailretail.kurlyflow.admin.command.domain.AdminRepository;
import com.detailretail.kurlyflow.admin.exception.AdminNotFoundException;
import com.detailretail.kurlyflow.admin.util.AdminConverter;
import com.detailretail.kurlyflow.common.vo.Region;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
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
  private final WorkerRepository workerRepository;

  public void assignRegion(AdminRegionRequest adminRegionRequest, Long adminId) {
    Admin admin = adminRepository.findById(adminId).orElseThrow(AdminNotFoundException::new);
    admin.assignRegion(Region.of(adminRegionRequest.getRegion()));
  }

  public List<DetailRegionResponse> assignDetailRegion(Long adminId) {
    Admin admin = adminRepository.findById(adminId).orElseThrow(AdminNotFoundException::new);
    List<Worker> workers = workerRepository.findByAdminId(admin.getId());
    admin.assignDetailRegion(workers);
    return workers.stream().map(AdminConverter::ofDetailRegion).collect(Collectors.toList());
  }
}
