package com.detailretail.kurlyflow.admin.query.application;

import com.detailretail.kurlyflow.admin.command.domain.Admin;
import com.detailretail.kurlyflow.admin.command.domain.AdminRepository;
import com.detailretail.kurlyflow.admin.util.AdminConverter;
import com.detailretail.kurlyflow.worker.exception.EntityNotFoundException;
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

  public List<WorkerStatusResponse> getWorkerStatus(Long adminId) {
    Admin admin = adminRepository.findWithWorkers(adminId)
        .orElseThrow(EntityNotFoundException::new);
    return admin.getWorkers().stream().map(AdminConverter::ofStatus).collect(Collectors.toList());
  }
}
