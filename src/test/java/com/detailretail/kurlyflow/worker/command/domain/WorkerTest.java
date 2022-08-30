package com.detailretail.kurlyflow.worker.command.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.detailretail.kurlyflow.admin.command.domain.Admin;
import com.detailretail.kurlyflow.common.vo.EmployeeNumber;
import com.detailretail.kurlyflow.common.vo.Phone;
import com.detailretail.kurlyflow.common.vo.Region;
import com.detailretail.kurlyflow.worker.exception.AdminIdIsNullException;
import com.detailretail.kurlyflow.worker.exception.AlreadyAssignedException;
import com.detailretail.kurlyflow.worker.exception.LoginFailException;
import com.detailretail.kurlyflow.worker.exception.UnAssignedFieldException;
import com.detailretail.kurlyflow.worker.util.PasswordEncrypter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class WorkerTest {

  Worker worker;
  Admin admin;

  @BeforeEach
  void setup() {
    worker = new Worker("강완수", new Phone("010-4234-3465"), PasswordEncrypter.encrypt("kang1234"));
    admin = new Admin("관리자", new EmployeeNumber("3453"), "관리자");
  }

  @Nested
  class 작업자_생성 {

    @Test
    public void 작업자_회원가입_성공() {
      assertThat(worker.getName()).isEqualTo("강완수");
      assertThat(worker.getPhone()).isEqualTo(new Phone("010-4234-3465"));
    }

    @Test
    public void 잘못된_번호_양식으로_회원가입_예외_발생() {
      assertThatThrownBy(() -> new Worker("강완수", new Phone("0140-42344-3465"),
          PasswordEncrypter.encrypt("kang1234"))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 잘못된_양식으로_회원가입_예외_발생() {
      assertThatThrownBy(() -> new Worker(null, null,
          PasswordEncrypter.encrypt("kang1234"))).isInstanceOf(NullPointerException.class);
    }
  }

  @Nested
  class 관리자_배정 {

    @Test
    public void 관리자_배정() {
      worker.assignAdmin(admin);
      assertThat(worker.getAdmin()).isNotNull();
    }

    @Test
    public void 빈_객체_주입으로_예외_발생() {
      assertThatThrownBy(() -> worker.assignAdmin(null)).isInstanceOf(AdminIdIsNullException.class);
    }
  }

  @Nested
  class 비밀번호_확인 {

    @Test
    public void 비밀번호_확인_성공() {
      worker.matchPassword("kang1234");
    }

    @Test
    public void 틀린_비밀번호로_확인_예외_발생() {
      assertThatThrownBy(() -> worker.matchPassword("wrong")).isInstanceOf(
          LoginFailException.class);
    }
  }

  @Nested
  class 근무지_배정 {

    @Test
    public void 근무지_배정_성공() {
      worker.assignRegion(Region.PICKING);
      assertThat(worker.getRegion()).isEqualTo(Region.PICKING);
    }

    @Test
    public void 이미_배정된_근무지를_배정하여_예외_발생() {
      worker.assignRegion(Region.PICKING);
      assertThatThrownBy(() -> worker.assignRegion(Region.PICKING)).isInstanceOf(
          AlreadyAssignedException.class);
    }
  }

  @Nested
  class 근무지_확인 {

    @Test
    public void 근무지_확인_성공() {
      worker.assignEmployeeNumber(new EmployeeNumber("1234"));
      worker.assignRegion(Region.PICKING);
      worker.canCheckRegion();
      assertThat(worker.getEmployeeNumber()).isNotNull();
      assertThat(worker.getRegion()).isEqualTo(Region.PICKING);
    }

    @Test
    public void 사번을_입력_받지_않아_근무지_확인_예외_발생() {
      assertThatThrownBy(() -> worker.canCheckRegion()).isInstanceOf(
          UnAssignedFieldException.class);
    }
  }

  @Nested
  class 세부_권역_확인 {

    @Test
    public void 세부_권역_확인_성공() {
      worker.assignEmployeeNumber(new EmployeeNumber("1234"));
      worker.assignRegion(Region.PICKING);
      worker.canCheckDetailRegion();
      assertThat(worker.getEmployeeNumber()).isNotNull();
      assertThat(worker.getRegion()).isEqualTo(Region.PICKING);
    }

    @Test
    public void 사번을_입력_받지_않아_세부권역_확인_예외_발생() {
      assertThatThrownBy(() -> worker.canCheckDetailRegion()).isInstanceOf(
          UnAssignedFieldException.class);
    }

    @Test
    public void 근무지를_입력받지않아_세부권역_확인_예외_발생() {
      worker.assignEmployeeNumber(new EmployeeNumber("1234"));
      assertThatThrownBy(() -> worker.canCheckDetailRegion()).isInstanceOf(
          UnAssignedFieldException.class);
    }
  }

  @Nested
  class 출석 {

    @Test
    public void 출석_성공() {
      worker.assignWishRegion(Region.PICKING);
      worker.attend();
      assertThat(worker.getWishRegion()).isEqualTo(Region.PICKING);
    }

    @Test
    public void 희망_근무지를_입력하지않아_출석_예외_발생() {
      assertThatThrownBy(() -> worker.attend()).isInstanceOf(
          UnAssignedFieldException.class);
    }

    @Test
    public void 이미_출석한_상태에서_재출석시_출석_예외_발생() {
      worker.assignWishRegion(Region.PICKING);
      worker.attend();
      assertThatThrownBy(() -> worker.attend()).isInstanceOf(
          AlreadyAssignedException.class);
    }
  }

}