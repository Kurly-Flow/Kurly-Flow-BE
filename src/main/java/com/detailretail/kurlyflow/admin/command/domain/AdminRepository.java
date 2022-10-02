package com.detailretail.kurlyflow.admin.command.domain;

import com.detailretail.kurlyflow.common.vo.EmployeeNumber;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

  Optional<Admin> findByEmployeeNumber(EmployeeNumber employeeNumber);
}
