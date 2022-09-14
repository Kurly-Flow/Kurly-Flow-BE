package com.detailretail.kurlyflow.admin.command.domain;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import com.detailretail.kurlyflow.admin.exception.NegativeTOException;
import com.detailretail.kurlyflow.admin.util.CalculateConverter;
import com.detailretail.kurlyflow.common.vo.Authority;
import com.detailretail.kurlyflow.common.vo.EmployeeNumber;
import com.detailretail.kurlyflow.common.vo.Region;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.exception.LoginFailException;
import com.detailretail.kurlyflow.worker.util.PasswordEncrypter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admin")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  private String name;

  @Embedded
  @Column(name = "employee_number")
  private EmployeeNumber employeeNumber;

  @Column(name = "password")
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "region")
  private Region region = Region.UNASSIGNED;

  @Column(name = "working_date")
  private LocalDate workingDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "working_team")
  private WorkingTeam workingTeam;

  @Column(name = "working_numbers")
  private Integer workingNumbers;

  @Enumerated(EnumType.STRING)
  @Column(name = "authority")
  private Authority authority = Authority.ROLE_ADMIN;

  private LocalDateTime createdAt;

  @ElementCollection
  @CollectionTable(name = "admin_worker", joinColumns = @JoinColumn(name = "id"))
  private Set<Long> workerIds = new HashSet<>();


  public Admin(String name, EmployeeNumber employeeNumber, String password) {
    Objects.requireNonNull(name, "name must not be null");
    Objects.requireNonNull(employeeNumber, "employeeNumber must not be null");
    Objects.requireNonNull(password, "password must not be null");
    this.name = name;
    this.employeeNumber = employeeNumber;
    this.password = password;
    this.authority = Authority.ROLE_ADMIN;
    this.createdAt = LocalDateTime.now();
  }

  public void matchPassword(String password) {
    if (!PasswordEncrypter.isMatch(password, this.password)) {
      throw new LoginFailException();
    }
  }

  public void assignRegion(Region region) {
    this.region = region;
  }

  public void assignWorkingDate(LocalDate workingDate) {
    this.workingDate = workingDate;
  }

  public void assignWorkingTeam(WorkingTeam workingTeam) {
    this.workingTeam = workingTeam;
  }

  public void assignTo(Integer workingNumbers) {
    if (workingNumbers < INTEGER_ZERO) {
      throw new NegativeTOException();
    }
    this.workingNumbers = workingNumbers;
  }

  public void assignDetailRegion(List<Worker> workers) {
    workers.stream().forEach(worker -> worker.assignDetailRegion("ASSIGN_DETAIL_REGION"));
  }


  public void assignWorkers(List<Worker> workers) {
    int seventyRateNumbers = CalculateConverter.getSeventy(this.getWorkingNumbers());
    IntStream.range(0, seventyRateNumbers).forEach(idx -> {
      if (idx < seventyRateNumbers) {
        workers.get(idx).assignAdmin(this);
        workers.get(idx).assignRegion(this.getRegion());
      } else {
        workers.get(workers.size() + seventyRateNumbers - idx - 1).assignAdmin(this);
        workers.get(workers.size() + seventyRateNumbers - idx - 1).assignRegion(this.getRegion());
      }
    });
  }
}
