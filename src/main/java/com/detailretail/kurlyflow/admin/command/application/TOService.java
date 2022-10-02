package com.detailretail.kurlyflow.admin.command.application;

import com.detailretail.kurlyflow.admin.command.domain.Admin;
import com.detailretail.kurlyflow.admin.command.domain.AdminRepository;
import com.detailretail.kurlyflow.admin.command.domain.WorkingTeam;
import com.detailretail.kurlyflow.admin.exception.LackOfWorkingNumbersException;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
import com.detailretail.kurlyflow.worker.exception.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TOService {

  private final AdminRepository adminRepository;
  private final WorkerRepository workerRepository;

  public void inputTO(TORequest toRequest, Long adminId) {
    Admin admin = adminRepository.findById(adminId)
        .orElseThrow(EntityNotFoundException::new);
    admin.assignWorkingDate(toRequest.getWorkingDate());
    admin.assignWorkingTeam(WorkingTeam.of(toRequest.getWorkingTeam()));
    admin.assignTo(toRequest.getWorkingNumbers());
  }

  public void assignWorkers(Long adminId) {
    Admin admin = adminRepository.findById(adminId).orElseThrow(EntityNotFoundException::new);
    List<Worker> workers = findUnassignedWorkers(admin);
    admin.assignWorkers(workers);
  }

  private List<Worker> findUnassignedWorkers(Admin admin) {
    LocalDateTime beforeOneHourWorkStartTime = LocalDateTime.of(admin.getWorkingDate(),
        admin.getWorkingTeam().getStart().minusHours(1));
    LocalDateTime workStartTime = LocalDateTime.of(admin.getWorkingDate(),
        admin.getWorkingTeam().getStart());
    List<Worker> workers = workerRepository.findByEmployeeNumberIsNotNullAndIsAttendedTrueAndLoginAtBetween(
        beforeOneHourWorkStartTime, workStartTime);
    checkSatisfiedWorkingNumbers(admin, workers);
    return orderingWorker(admin, workers);
  }

  private void checkSatisfiedWorkingNumbers(Admin admin, List<Worker> workers) {
    if (workers.size() < admin.getWorkingNumbers()) {
      throw new LackOfWorkingNumbersException();
    }
  }

  private List<Worker> orderingWorker(Admin admin, List<Worker> workers) {
    return workers.stream().sorted((o1, o2) -> {
      long o1Proficiency = o1.getHistories().stream()
          .filter(workerHistory -> workerHistory.getRegion().equals(admin.getRegion())).count();
      long o2Proficiency = o2.getHistories().stream()
          .filter(workerHistory -> workerHistory.getRegion().equals(admin.getRegion())).count();
      if (o1Proficiency == o2Proficiency) {
        return o1.getWishRegion().equals(o2.getWishRegion()) == true ? 1 : -1;
      }
      return (int) o2Proficiency - (int) o1Proficiency;
    }).collect(Collectors.toList());
  }
}
