package com.detailretail.kurlyflow.admin.command.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomAdminsDetails  implements UserDetails {
    private Long id;

    @Builder.Default
    private List<String> authorities = new ArrayList<>();

    public CustomAdminsDetails(Long id, List<String> authorities) {
        this.id = id;
        this.authorities = authorities;
    }

    public static CustomAdminsDetails of(Admin admin) {
        return CustomAdminsDetails.builder().id(admin.getId())
                .authorities(List.of(admin.getAuthority().name())).build();
    }

    public boolean validate(Long id) {
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
        return null;
    }
}
