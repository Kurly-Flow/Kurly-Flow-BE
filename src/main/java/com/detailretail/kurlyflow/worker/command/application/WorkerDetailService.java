package com.detailretail.kurlyflow.worker.command.application;

import com.detailretail.kurlyflow.worker.command.domain.CustomUserDetails;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class CustomWorkerDetailService implements UserDetailsService {

  private final WorkerRepository workerRepository;

  @Override
  public CustomUserDetails loadUserByUsername(String phone) {
    Worker worker = workerRepository.findByPhone(phone)
        .orElseThrow(() -> new IllegalArgumentException("찾을 수 없습니다."));
    return CustomUserDetails.of(worker);
  }
}