package com.detailretail.kurlyflow.admin.command.domain;

import com.detailretail.kurlyflow.common.vo.EmployeeNumber;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Long> {

  Optional<Admin> findByEmployeeNumber(EmployeeNumber employeeNumber);

  @Query("SELECT a FROM Admin a JOIN FETCH a.workers WHERE a.id =:adminId")
  Optional<Admin> findWithWorkers(Long adminId);

}
