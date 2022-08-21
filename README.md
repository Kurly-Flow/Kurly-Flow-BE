# Kurly Flow BE
본 프로젝트는 "KURLY HACK FESTA 2022" 과제 해결을 위해 개발된 
디테일 리테일 팀의 프로젝트 "Kurly-Flow"의 백엔드 파트입니다.

## ✨ Common

### Pattern - DDD (Domain-Driven Design)
도메인 주도 설계

### Architecture
> User - Presentation - Application - Domain - Infrastructure - DB


## ✨ Structure
```text
api-server-spring-boot
  > * build
  > gradle
  > src.main.java.com.detailretail.kurlyflow
    > admin  // 관리자 비지니스 로직
    
    > common // 공통적으로 사용되는 exception, response 로직들을 관리하는 곳
      > converter
      > exception
       | BadRequestException.java // Controller, Service, Provider 에서 Response 용으로 공통적으로 사용 될 익셉션 클래스
       | ConflictException
       | NotFoundException
       | UnAuthorizedException
      > vo
       | EmployeeNumber
       | Phone
       | Region
       | RegionNotMatchException
      | ErrorResponse.java // Controller 에서 Response 용으로 공통적으로 사용되는 구조를 위한 모델 클래스
      | ExceptionAdvice.java 
      
    > config // 설정파일 관리
      | CorsConfig
      | CurrentUserArgumentResolver
      
    > worker // 작업자 비지니스 로직
      > Command
        > application
        > domain
      > util
        | PasswordEncrypter
        | WorkerConverter
        
    | DemoApplication // Application 서버 시작 지점
    
   > src.test
    > java
    > resources
     | application.yml // Database 연동을 위한 설정 값 세팅 및 Port 정의 파일
     
build.gradle // gradle 빌드시에 필요한 dependency 설정하는 곳
.gitignore // git 에 포함되지 않아야 하는 폴더, 파일들을 작성 해놓는 곳

```