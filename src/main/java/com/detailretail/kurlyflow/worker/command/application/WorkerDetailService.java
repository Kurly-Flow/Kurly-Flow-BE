package com.detailretail.kurlyflow.worker.command.application;

import com.detailretail.kurlyflow.admin.command.domain.CustomDetails;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class WorkerDetailService implements UserDetailsService {

  private final WorkerRepository workerRepository;

  @Override
  public CustomDetails loadUserByUsername(String workerId) {
    Worker worker = workerRepository.findById(Long.valueOf(workerId))
        .orElseThrow(() -> new IllegalArgumentException("찾을 수 없습니다."));
    return CustomDetails.builder().id(worker.getId())
        .authorities(List.of(worker.getAuthority().name())).build();
  }
}