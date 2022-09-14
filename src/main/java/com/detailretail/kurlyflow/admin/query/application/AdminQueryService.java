package com.detailretail.kurlyflow.admin.query.application;

import com.detailretail.kurlyflow.admin.command.domain.Admin;
import com.detailretail.kurlyflow.admin.command.domain.AdminRepository;
import com.detailretail.kurlyflow.admin.exception.AdminNotFoundException;
import com.detailretail.kurlyflow.admin.util.AdminConverter;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
import com.detailretail.kurlyflow.worker.exception.EntityNotFoundException;
import com.detailretail.kurlyflow.worker.query.application.DetailRegionResponse;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminQueryService {

  private final AdminRepository adminRepository;
  private final WorkerRepository workerRepository;

  public List<WorkerStatusResponse> getWorkerStatus(Long adminId) {
    Admin admin = adminRepository.findById(adminId)
        .orElseThrow(EntityNotFoundException::new);
    return workerRepository.findByAdminId(admin.getId()).stream()
        .sorted(Comparator.comparing(Worker::getLoginAt).reversed()).map(AdminConverter::ofStatus)
        .collect(Collectors.toList());
  }

  public List<WorkerAttendanceResponse> getWorkers(Long adminId) {
    Admin admin = adminRepository.findById(adminId)
        .orElseThrow(EntityNotFoundException::new);
    return workerRepository.findByAdminId(admin.getId()).stream()
        .map(AdminConverter::ofAttendance).collect(Collectors.toList());
  }

  public List<DetailRegionResponse> getDetailRegionForWorkers(Long adminId) {
    Admin admin = adminRepository.findById(adminId).orElseThrow(AdminNotFoundException::new);
    return workerRepository.findByAdminId(admin.getId()).stream()
        .map(AdminConverter::ofDetailRegion).collect(Collectors.toList());
  }
}
