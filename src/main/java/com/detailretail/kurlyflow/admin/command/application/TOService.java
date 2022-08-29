package com.detailretail.kurlyflow.admin.command.application;

import com.detailretail.kurlyflow.admin.command.domain.Admin;
import com.detailretail.kurlyflow.admin.command.domain.AdminRepository;
import com.detailretail.kurlyflow.admin.command.domain.WorkingTeam;
import com.detailretail.kurlyflow.admin.exception.LackOfWorkingNumbersException;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
import com.detailretail.kurlyflow.worker.exception.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    Admin admin = adminRepository.findWithWorkers(adminId)
        .orElseThrow(EntityNotFoundException::new);
    admin.assignWorkingDate(toRequest.getWorkingDate());
    admin.assignWorkingTeam(WorkingTeam.of(toRequest.getWorkingTeam()));
    admin.assignTo(toRequest.getWorkingNumbers());
  }

  public void assignWorkers(Long adminId) {
    Admin admin = adminRepository.findById(adminId).orElseThrow(EntityNotFoundException::new);
    LocalTime minusOneHour = admin.getWorkingTeam().getStart().minusHours(1);
    List<Worker> workers = workerRepository.findWorkerWithWorkerHistory(
        LocalDateTime.of(LocalDate.now(), minusOneHour));
    System.out.println(workers.size());
    workers.stream().forEach(worker -> System.out.println(worker.getLoginAt()));
//    int seventyRateNumbers = (int) CalculateConverter.getSeventy(admin.getWorkingNumbers());
//    isSatisfiedWorkingNumbers(admin, workers);
//    List<Worker> orderedWorker = orderingWorker(admin, workers);
//    IntStream.range(0, seventyRateNumbers).forEach(idx -> {
//      if (idx < seventyRateNumbers) {
//        orderedWorker.get(idx).assignAdmin(admin);
//        orderedWorker.get(idx).assignRegion(admin.getRegion());
//      } else {
//        orderedWorker.get(orderedWorker.size() + seventyRateNumbers - idx - 1).assignAdmin(admin);
//        orderedWorker.get(orderedWorker.size() + seventyRateNumbers - idx - 1)
//            .assignRegion(admin.getRegion());
//      }
//    });
  }

  private void isSatisfiedWorkingNumbers(Admin admin, List<Worker> workers) {
    if (workers.size() < admin.getWorkingNumbers()) {
      throw new LackOfWorkingNumbersException();
    }
  }

  public List<Worker> orderingWorker(Admin admin, List<Worker> workers) {
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
