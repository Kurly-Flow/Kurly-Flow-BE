package com.detailretail.kurlyflow.admin.command.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.detailretail.kurlyflow.admin.exception.NegativeTOException;
import com.detailretail.kurlyflow.admin.exception.WorkingTeamNotMatchException;
import com.detailretail.kurlyflow.common.vo.EmployeeNumber;
import com.detailretail.kurlyflow.common.vo.Phone;
import com.detailretail.kurlyflow.common.vo.Region;
import com.detailretail.kurlyflow.common.vo.RegionNotMatchException;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.exception.LoginFailException;
import com.detailretail.kurlyflow.worker.util.PasswordEncrypter;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AdminTest {

  Worker worker;
  Admin admin;

  @BeforeEach
  void setup() {
    worker = new Worker("강완수", new Phone("010-4234-3465"), PasswordEncrypter.encrypt("kang1234"));
    admin = new Admin("관리자", new EmployeeNumber("3453"), PasswordEncrypter.encrypt("sdf4235"));
  }

  @Nested
  class 관리자_생성 {

    @Test
    public void 작업자_회원가입_성공() {
      assertThat(admin.getName()).isEqualTo("관리자");
      assertThat(admin.getEmployeeNumber()).isEqualTo(new EmployeeNumber("3453"));
    }

    @Test
    public void 잘못된_번호_양식으로_회원가입_예외_발생() {
      assertThatThrownBy(() -> new Admin("관리자", new EmployeeNumber("34353"),
          PasswordEncrypter.encrypt("sdf4235"))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 잘못된_양식으로_회원가입_예외_발생() {
      assertThatThrownBy(() -> new Admin(null, null, null)).isInstanceOf(
          NullPointerException.class);
    }
  }

  @Nested
  class 비밀번호_확인 {

    @Test
    public void 비밀번호_확인_성공() {
      admin.matchPassword("sdf4235");
    }

    @Test
    public void 틀린_비밀번호로_확인_예외_발생() {
      assertThatThrownBy(() -> admin.matchPassword("wrong")).isInstanceOf(LoginFailException.class);
    }
  }

  @Nested
  class 근무지_배정 {

    @Test
    public void 근무지_배정_성공() {
      admin.assignRegion(Region.PICKING);
    }

    @Test
    public void 근무지_배정_예외_발생() {
      assertThatThrownBy(() -> admin.assignRegion(Region.of("wrong"))).isInstanceOf(
          RegionNotMatchException.class);
    }
  }

  @Nested
  class 근무_날짜 {

    @Test
    public void 근무_날짜_입력_성공() {
      admin.assignWorkingDate(LocalDate.now());
    }
  }

  @Nested
  class 근무조_배정 {

    @Test
    public void 근무조_배정_성공() {
      admin.assignWorkingTeam(WorkingTeam.미들);
    }

    @Test
    public void 잘못된_입력으로_근무조_배정_예외_발생() {
      assertThatThrownBy(() -> admin.assignWorkingTeam(WorkingTeam.of("wrong"))).isInstanceOf(
          WorkingTeamNotMatchException.class);
    }
  }

  @Nested
  class TO_배정 {

    @Test
    public void TO_배정_성공() {
      admin.assignTo(15);
    }

    @Test
    public void 음수_입력으로_TO_배정_예외_발생() {
      assertThatThrownBy(() -> admin.assignTo(-1)).isInstanceOf(NegativeTOException.class);
    }
  }
}