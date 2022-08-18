package com.detailretail.kurlyflow.worker.command.domain;

import com.detailretail.kurlyflow.common.vo.EmployeeNumber;
import com.detailretail.kurlyflow.common.vo.Phone;
import com.detailretail.kurlyflow.common.vo.Region;
import com.detailretail.kurlyflow.worker.command.application.LoginFailException;
import com.detailretail.kurlyflow.worker.util.PasswordEncrypter;
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
  ;

  @Column(name = "is_attended")
  private Boolean isAttended;

  @Column(name = "is_worked")
  private Boolean isWorked;

  //뭐하는건지 까먹었음
  @Column(name = "location")
  private String location;

  public Worker(String name, Phone phone, String password) {
    Objects.requireNonNull(name, "name must not be null");
    Objects.requireNonNull(phone, "phone must not be null");
    Objects.requireNonNull(password, "password must not be null");
    this.name = name;
    this.phone = phone;
    this.password = password;
  }

  public void matchPassword(String password) {
    if (!PasswordEncrypter.isMatch(password, this.password)) {
      throw new LoginFailException();
    }
  }

  public void assignRegion(Region region) {
    this.region = region;
  }

  public void assignEmployeeNumber(EmployeeNumber employeeNumber) {
    this.employeeNumber = employeeNumber;
  }

  public void assignWishRegion(Region wishRegion) {
    this.wishRegion = wishRegion;
  }
}
