package com.detailretail.kurlyflow.worker.command.domain;

import com.detailretail.kurlyflow.common.vo.Phone;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prgrms.p2p.domain.user.entity.User;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomUserDetails implements UserDetails {

  private Long id;

  private Phone phone;

  @Builder.Default
  private List<String> authorities = new ArrayList<>();

  public CustomUserDetails(Long id,
      Phone phone, List<String> authorities) {
    this.id = id;
    this.phone = phone;
    this.authorities = authorities;
  }

  public static CustomUserDetails of(User user) {
    return CustomUserDetails.builder()
        .id(user.getId())
        .email(user.getEmail())
        .authorities(user.getAuthorities())
        .build();
  }

  public boolean validate(Long id) {
    return id.equals(this.id);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return email;
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
}
