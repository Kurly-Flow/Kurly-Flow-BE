package com.detailretail.kurlyflow.worker.command.domain;

import com.detailretail.kurlyflow.common.vo.Authority;
import com.detailretail.kurlyflow.common.vo.EmployeeNumber;
import com.detailretail.kurlyflow.common.vo.Phone;
import com.detailretail.kurlyflow.common.vo.Region;
import com.detailretail.kurlyflow.worker.command.application.LoginFailException;
import com.detailretail.kurlyflow.worker.util.PasswordEncrypter;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

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

  private LocalDateTime createdAt;

  public Worker(String name, Phone phone, String password) {
    Objects.requireNonNull(name, "name must not be null");
    Objects.requireNonNull(phone, "phone must not be null");
    Objects.requireNonNull(password, "password must not be null");
    this.name = name;
    this.phone = phone;
    this.password = password;
    this.createdAt = LocalDateTime.now();
  }

  public void matchPassword(String password) {
    if (!PasswordEncrypter.isMatch(password, this.password)) {
      throw new LoginFailException();
    }
  }

  public void assignDetailRegion(String detailRegion) {
    if(Strings.isBlank(detailRegion)) {

    }
    this.detailRegion = detailRegion;
  }

  public void assignRegion(Region region) {
    if (!isAssignedRegion()) {
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
    return region.equals(Region.UNASSIGNED) ? true : false;
  }
}
