package com.detailretail.kurlyflow.worker.command.domain;

import com.detailretail.kurlyflow.admin.command.domain.Admin;
import com.detailretail.kurlyflow.common.vo.Authority;
import com.detailretail.kurlyflow.common.vo.EmployeeNumber;
import com.detailretail.kurlyflow.common.vo.Phone;
import com.detailretail.kurlyflow.common.vo.Region;
import com.detailretail.kurlyflow.worker.command.application.LoginFailException;
import com.detailretail.kurlyflow.worker.util.PasswordEncrypter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "worker")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Worker {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  private String name;

  @Embedded
  @Column(name = "phone", unique = true)
  private Phone phone;

  @Embedded
  @Column(name = "employee_number")
  private EmployeeNumber employeeNumber;

  @Column(name = "password")
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "wish_region")
  private Region wishRegion = Region.UNASSIGNED;

  @Enumerated(EnumType.STRING)
  @Column(name = "region")
  private Region region = Region.UNASSIGNED;

  @Column(name = "detail_region")
  private String detailRegion;

  @Column(name = "is_attended")
  private Boolean isAttended = Boolean.FALSE;

  @Column(name = "is_worked")
  private Boolean isWorked = Boolean.FALSE;

  //뭐하는건지 까먹었음
  @Column(name = "location")
  private String location;

  @Enumerated(EnumType.STRING)
  @Column(name = "authority")
  private Authority authority = Authority.ROLE_WORKER;

  @Column(name = "login_at")
  private LocalDateTime loginAt;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "worker")
  private List<WorkerHistory> histories = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "admin_id")
  private Admin admin;

  public Worker(String name, Phone phone, String password) {
    Objects.requireNonNull(name, "name must not be null");
    Objects.requireNonNull(phone, "phone must not be null");
    Objects.requireNonNull(password, "password must not be null");
    this.name = name;
    this.phone = phone;
    this.password = password;
    this.loginAt = LocalDateTime.now();
  }

  public void addHistory(WorkerHistory workerHistory) {
    histories.add(workerHistory);
    workerHistory.setWorker(this);
  }

  public void assignAdmin(Admin admin) {
    if (Objects.isNull(admin)) {
      throw new AdminIdIsNullException();
    }
    this.admin = admin;
  }

  public void matchPassword(String password) {
    if (!PasswordEncrypter.isMatch(password, this.password)) {
      throw new LoginFailException();
    }
  }

  public void canCheckRegion() {
    if (Objects.isNull(employeeNumber)) {
      throw new UnAssignedEmployeeNumberException();
    }
  }

  public void canCheckDetailRegion() {
    if (Objects.isNull(employeeNumber)) {
      throw new UnAssignedEmployeeNumberException();
    }
    if (this.region.equals(Region.UNASSIGNED)) {
      throw new UnAssignedRegionException();
    }
  }

  public void attend() {
    if (this.wishRegion.equals(Region.UNASSIGNED)) {
      throw new UnAssignedRegionException();
    }
    if (this.isAttended) {
      throw new AlreadyAssignedRegionException();
    }
    this.isAttended = Boolean.TRUE;
  }

  public void assignDetailRegion(String detailRegion) {
    if (!isAssignedRegion()) {
      throw new UnAssignedRegionException();
    }
    this.detailRegion = detailRegion;
  }

  public void assignRegion(Region region) {
    if (isAssignedRegion()) {
      throw new AlreadyAssignedRegionException();
    }
    this.region = region;
  }

  public void assignEmployeeNumber(EmployeeNumber employeeNumber) {
    this.employeeNumber = employeeNumber;
  }

  public void assignWishRegion(Region wishRegion) {
    this.wishRegion = wishRegion;
  }

  private boolean isAssignedRegion() {
    return region.equals(Region.UNASSIGNED) ? false : true;
  }

  public void updateLoginAt() {
    this.loginAt = LocalDateTime.now();
  }
}
