package com.detailretail.kurlyflow.config.jwt;

import com.detailretail.kurlyflow.common.vo.Authority;
import com.detailretail.kurlyflow.worker.command.application.WorkerDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

  private final WorkerDetailService workerDetailService;
  @Value("${jwt.secretKey}")
  private String secretKey;
  // 토큰 유효시간 하루
  private long tokenValidTime = 24 * 60 * 60 * 1000L;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String userPk, List<String> roles) {
    Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
    claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
    Date now = new Date();
    return Jwts.builder().setClaims(claims).setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + tokenValidTime))
        .signWith(SignatureAlgorithm.HS256, secretKey).compact();
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = findUserDetails(token);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getId(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public List<String> getRoles(String token) {
    return (List<String>) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
        .get("roles");
  }

  public UserDetails findUserDetails(String token) {
    Authority authority = Authority.valueOf(this.getRoles(token).get(0));
    if (authority.equals(Authority.ROLE_WORKER)) {
      return workerDetailService.loadUserByUsername(this.getId(token));
    } else {
      //관리자
      return null;
    }
  }

  public String resolveToken(HttpServletRequest request) {
    return request.getHeader("Authorization");
  }

  public boolean validateToken(String jwtToken) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }
}