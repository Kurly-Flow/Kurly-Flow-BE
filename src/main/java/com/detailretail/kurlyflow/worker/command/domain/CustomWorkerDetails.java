package com.detailretail.kurlyflow.worker.command.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomWorkerDetails implements UserDetails {


  private Long id;

  private String phone;

  @Builder.Default
  private List<String> authorities = new ArrayList<>();

  public CustomWorkerDetails(Long id, String phone, List<String> authorities) {
    this.id = id;
    this.phone = phone;
    this.authorities = authorities;
  }

  public static CustomWorkerDetails of(Worker worker) {
    return CustomWorkerDetails.builder().id(worker.getId()).phone(worker.getPhone().getNumber())
        .authorities(List.of(worker.getAuthority().name())).build();
  }

  public boolean validate(Long id, String phone) {
    return id.equals(this.id);
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  @JsonIgnore
  public boolean isEnabled() {
    return false;
  }

  @Override
  public String getUsername() {
    return phone;
  }
}
