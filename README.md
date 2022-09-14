# Kurly Flow BE
본 프로젝트는 "KURLY HACK FESTA 2022" 과제 해결을 위해 개발된 
디테일 리테일 팀의 프로젝트 "Kurly-Flow"의 백엔드 파트입니다.

### 프로젝트 소개
풀필먼트 내 소통 솔루션

### 개발 기간
2022.08.19 ~ 2022.08.24

### 기술 스택
Java 11, Spring Boot 2.6.11, MySQL 8.0, firebase-admin 6.8.1, jjwt 0.9.1, Jenkins, Code Deploy, AWS(EC2, S3)

### 링크 모음
[노션 팀 페이지](https://cuddly-force-7ca.notion.site/KURLY-HACK-FESTA-2022-ac1128c8694e496e91461ffb8cb4da22)  
[과제 계획서](https://drive.google.com/file/d/10bG3zmDK-itsKCObTtsnK3s-HUjMxr2P/view?usp=sharing)  
[컬리플로우 PPT](https://docs.google.com/presentation/d/1HO0knuIgcyGL32xdSNezxrLFk-Ua40_i/edit?usp=sharing&ouid=114543955451291444059&rtpof=true&sd=true)   
[시연 영상](https://drive.google.com/file/d/1uGB9EZBm2O5QXG8CKU1E7gVHrD_dA0Vq/view?usp=sharing)  

### ERD 다이어그램
<img width="733" alt="스크린샷 2022-09-13 03 13 48" src="https://user-images.githubusercontent.com/58693617/189726446-f78bd928-cbfc-4cc5-95bf-ac9a11474711.png">

### Architecture
![컬리 아키텍처 이미지](https://user-images.githubusercontent.com/58693617/189577647-896d35f1-0520-4278-abd9-4fc5b90b0764.png)


### 컬리 플로우 애그리거트 및 바운디드 컨텍스트
도메인 제약 사항을 공유하고 함께 생성되고 삭제되는 분야를 묶었다.
![컬리_바운디드 컨텍스트](https://user-images.githubusercontent.com/58693617/188844113-f5746151-7288-4936-9342-ba5eb01c55f0.jpg)

## 개발 기능
![image](https://user-images.githubusercontent.com/58693617/189472362-fa5be769-8f33-4332-ae81-24f56020e077.png)

## 개발 중점 사항

### Git-Flow 전략
Git-Flow 브랜치 전략에 따라서 이슈를 발행하고 기능별로 브랜치를 나누어 적용했다. 짧은 개발 기간이기에 Hotfix, Release 브랜치는 굳이 사용하지 않았다.  
Master <- develop <- feature 순의 프로세스를 가지고 있다.  
![image](https://user-images.githubusercontent.com/58693617/189517348-7ce523c4-8ef9-48ef-860c-99042f31e1b3.png)  

### 객체 참조 대신 ID 참조
가장 강한 결합도를 가지는 객체 참조를 사용을 자체하고, ID 참조를 통해 객체 간 결합도를 분리했다.  
애그리거트 정의와 더불어 트랜잭션 경계를 제어하기 위해서도 ID 참조가 필요했다.

### CQRS 패턴
DDD 및 CQRS 모델에 집중하며 만들었다.  

명령 모델 프로세스  
User - Presentation - Command - Application - Domain - Repository  
조회 모델 프로세스  
User - Presentation - Query - Application - Domain - Repository   

명령 조회 모델을 분리하며 조회 모델 쪽에 트래픽에 대비할 수 있는 여러 장치를 줄 수 있다.  
**DB Replication**을 한다던지, 조회 모델 쪽에만 **Cache**를 적용할 수도 있다. **인덱스**를 조회 모델에 최적화되도록 걸 수도 있어 보인다.  
높은 수준의 CQRS 패턴을 적용하기 위해선 추후에 조회용 DB를 NoSQL로 추가한다면 성능 상의 이점을 얻을 수 있을 것이다.  

### DIP, SRP, 적절한 패키지 단위
의존 역전 원칙을 지키기 위해 외부 라이브러리인 FCM을 infra에 작성하고 인터페이스는 domain 내부에 존재하게 했다.
그동안 WorkerService에 모든 관련 로직을 넣어 관리했는데 기능의 목적별로 서비스를 나누어 단일 책임 원칙을 최대한 지키려 노력했다. 그런데 이렇게 구성하니 패키지 당 클래스가 너무 많아지는 문제가 있었다. 예외를 분리해 적절한 패키지 단위를 유지하려 했다.(10 ~ 15개)  
<img width="378" alt="스크린샷 2022-09-14 14 28 11" src="https://user-images.githubusercontent.com/58693617/190066888-7278b630-0412-4223-8d5b-74ed2d3c74d8.png">  
<img width="303" alt="스크린샷 2022-09-14 14 30 32" src="https://user-images.githubusercontent.com/58693617/190067359-1d1b9d55-867b-47d5-bb1f-338c172e4647.png">  


### 예외 처리
예외 처리는 대표적인 예외들을 common 폴더에 두고 각 도메인에 맞게 상속받아 작성했다.

```java
public class LackOfWorkingNumbersException extends BadRequestException {

  private static final String LACK_OF_WORKING_NUMBERS = "근무자 수가 부족합니다.";

  public LackOfWorkingNumbersException() {
    super(LACK_OF_WORKING_NUMBERS);
  }
}
```

### 값 객체 사용
값 객체를 사용하여 도메인은 값 객체에 대한 검증이 없이 도메인 모델에만 집중할 수 있도록 했다.

```java
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

  ... 생략
}
```

```java
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Phone {

  private static final String PHONE_REGX = "^\\d{3}-\\d{3,4}-\\d{4}$";
  private String number;

  public Phone(String number) {
    validationPhone(number);
    this.number = number;
  }

  public String getNumber() {
    return number;
  }

  public void validationPhone(String number) {
    if (StringUtils.isBlank(number) || !number.matches(PHONE_REGX)) {
      throw new IllegalArgumentException();
    }
  }

}
```

### 스케줄러 사용
유저 히스토리 테이블을 위한 기능이었다. 스케줄러를 활용해 일을 한 작업자들이 작업한 기록이 남도록 트래픽이 거의 없는 새벽 5시에 테이블에 추가되도록 했다.

```java
@Component
@RequiredArgsConstructor
@Transactional
public class WorkerHistoryService {

  private final WorkerRepository workerRepository;

  @Scheduled(cron = "0 0 5 * * *") //새벽 5시에 상태 변경
  public void saveWorkerHistory() {
    List<Worker> workers = workerRepository.findAllByIsWorkedTrueAndLoginAtBetween(
        LocalDateTime.now().minusDays(1L), LocalDateTime.now());
    workers.stream()
        .forEach(worker -> worker.addHistory(new WorkerHistory(worker.getRegion(), worker)));
  }
}
```

### 여러 개의 userDetails
여러 개의 loadByusername을 구현해 config에서 여러 개를 등록해줘야 했다.
Admin과 Worker를 나누어 설정해주었다.

```java
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenProvider jwtTokenProvider;
  private final AdminDetailService adminDetailService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().disable() 
        .csrf().disable() 
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
            UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(adminDetailService);
  }
}
```
```java
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WorkerSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenProvider jwtTokenProvider;
  private final WorkerDetailService workerDetailService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().disable() 
        .csrf().disable() 
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
            UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(workerDetailService);
  }
}
```


### 근무지 배정 로직
작업자의 숙련도와 선호도를 고려해 70%를 배정하고 나머지 30%는 랜덤하게 배정하도록 했다. 구현을 위해 스트림을 사용했다.

```java
public List<Worker> orderingWorker(Admin admin, List<Worker> workers) {
  return workers.stream().sorted((o1, o2) -> {
    long o1Proficiency = o1.getHistories().stream()
        .filter(workerHistory -> workerHistory.getRegion().equals(admin.getRegion())).count();
    long o2Proficiency = o2.getHistories().stream()
        .filter(workerHistory -> workerHistory.getRegion().equals(admin.getRegion())).count();
    if (o1Proficiency == o2Proficiency) {
      return o1.getWishRegion().equals(o2.getWishRegion()) == true ? 1 : -1;
    }
    return (int) o2Proficiency - (int) o1Proficiency;
  }).collect(Collectors.toList());
}
```

### Enum 활용
ENUM을 활용해 정책을 부여하였다. 기존에 상수로 활용했을땐, 정책이 바뀌면서 여러 가지 변경할 점이 많았는데 Enum으로 관리하니 변경점이 적어 좋았다. 불변성 또한 보장할 수 있다는 점도 장점이다.  


```java
@Getter
@RequiredArgsConstructor
public enum ToteWeightPolicy {
  MAX_TOTE_WEIGHT(8000);

  private final Integer weight;
}
```
```java
@Getter
@RequiredArgsConstructor
public enum WorkingTeam {
  주간조(LocalTime.of(10, 00), LocalTime.of(19, 00)), 
  점심조(LocalTime.of(13, 00), LocalTime.of(22, 00)), 
  풀타임(LocalTime.of(15, 30), LocalTime.of(12, 50)), 
  미들(LocalTime.of(17, 00), LocalTime.of(12, 50)), 
  파트(LocalTime.of(19, 30), LocalTime.of(12, 50));

  private final LocalTime start;
  private final LocalTime end;

  public static WorkingTeam of(String team) {
    return Arrays.stream(WorkingTeam.values())
        .filter(workingTeam -> workingTeam.name().equals(teams)).findFirst()
        .orElseThrow(WorkingTeamNotMatchException::new);
  }
}

```

### FCM 사용
기술 선택 과정 중 SSE와 FCM을 고민했었다. SSE는 웹소켓과는 다르게 HTTP 프로토콜만으로 사용이 가능하고, 가볍다. 대부분의 브라우저에서 지원하기도 하고 PDA에서 사용하는 안드로이드 환경에서도 완벽히 대응한다. 단방향 연결이고 한 번 연결 후 지속되기 때문에 배터리 사용 빈도도 WebSocket보다 나을 것이며 연결을 계속해서 확인하지 않아도 된다.  
그런데도 FCM을 선택한 이유는 개발 속도때문이다. 프론트 개발자 분과 내가 사용해보지 않았기에 얼마나 걸릴지 모를 일이었다. 해결해나가야 할 과제가 마지막 날까지도 지속적으로 있었기 때문에 지체할 수 없었다.

### 리팩토링 과정
작업자 배정 메서드가 지저분해 가독성이 좋고 단일 책임 원칙을 준수하는 코드로 리팩토링했다.

리팩토링 전
```java
public void assignWorkers(Long adminId) {
    Admin admin = adminRepository.findById(adminId).orElseThrow(EntityNotFoundException::new);
    LocalDateTime beforeOneHourWorkStartTime = LocalDateTime.of(admin.getWorkingDate(),
        admin.getWorkingTeam().getStart().minusHours(1));
    LocalDateTime workStartTime = LocalDateTime.of(admin.getWorkingDate(),
        admin.getWorkingTeam().getStart());
    List<Worker> workers = workerRepository.findByEmployeeNumberIsNotNullAndIsAttendedTrueAndAdminIsNullAndLoginAtBetween(
        beforeOneHourWorkStartTime, workStartTime);
    checkSatisfiedWorkingNumbers(admin, workers);
    int seventyRateNumbers = CalculateConverter.getSeventy(admin.getWorkingNumbers());
    List<Worker> orderedWorker = orderingWorker(admin, workers);
    IntStream.range(0, seventyRateNumbers).forEach(idx -> {
      if (idx < seventyRateNumbers) {
        orderedWorker.get(idx).assignAdmin(admin);
        orderedWorker.get(idx).assignRegion(admin.getRegion());
      } else {
        orderedWorker.get(orderedWorker.size() + seventyRateNumbers - idx - 1).assignAdmin(admin);
        orderedWorker.get(orderedWorker.size() + seventyRateNumbers - idx - 1)
            .assignRegion(admin.getRegion());
      }
    });
  }
}
```

리팩토링 후
```java
public void assignWorkers(Long adminId) {
    Admin admin = adminRepository.findById(adminId).orElseThrow(EntityNotFoundException::new);
    List<Worker> workers = findUnassignedWorkers(admin);
    admin.assignWorkers(workers);
  }
}
```
