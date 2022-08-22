package com.detailretail.kurlyflow.admin.command.domain;

import com.detailretail.kurlyflow.common.vo.EmployeeNumber;
import com.detailretail.kurlyflow.common.vo.Phone;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmployeeNumber(EmployeeNumber employeeNumber);

    @Query("SELECT a FROM Admin a JOIN FETCH a.workers" )
    Optional<Admin> getAdmin();

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Admin a SET a.region = :region where a.id = :adminId")
    int updateAdminRegion(@Param(value="region") String region, @Param(value="adminId") Long adminId);
}
