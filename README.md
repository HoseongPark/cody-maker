# Cody Maker

# 개요

---

8개의 카테고리 기반으로 상품을 하나씩 구매하여, 코디를 완성하기 위한 서비스

# 구현 범위

1. 고객은 카테고리 별로 최저가격인 브랜드와 가격을 조회하고 총액이 얼마인지 확인할 수 있어야 합니다.
2. 고객은 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액이 얼마인지 확인할 수 있어야 합니다.
3. 고객은 특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 확인하고 각 브랜드 상품의 가격을 확인할 수 있어야 합니다.
4. 운영자는 새로운 브랜드를 등록하고, 모든 브랜드의 상품을 추가, 변경, 삭제할 수 있어야 합니다.

## 완료 부분

- 유스케이스를 Client에서 처리 할 수 있도록 API 구현
- 코드 상의 Unit Test 작성 작업 진행
- 부분 통합 테스트 작성 진행

## 미비한 부분

- Frontend 화면 구성
- 통합 테스트 부족
- 상세한 API 문서 제공 부족
- 상세한 ReadMe 작성

# 실행 방법

- 실행 환경은 Mac or Linux 환경을 가정하고 작성을 진행 하였습니다.
- Window의 경우 gradlew.bat을 사용해주세요. (동작 확인은 하지 못했음)

## 개발 환경

- JDK : temurin-17
- Kotlin : 1.9
- Spring Boot : 3.3.2

## Unit Test 실행 방법

```bash
./gradlew test
```

- 프로젝트 Root 폴더에서 Gradlew를 통한 Test를 진행 합니다.

### 결과

![Untitled](Cody%20Maker%20ef634657662b4b48bb02fdcdde6177d5/Untitled.png)

- 하단 Build Successful 확인이 되면 테스트 완료.

## 코드 빌드 방법

```bash
./gradlew build
```

- 프로젝트 Root 폴더에서 Gradlew를 통한 Build를 진행 합니다.
- 실행하기위한 Jar 파일이 생성 됩니다.

### 결과

![Untitled](Cody%20Maker%20ef634657662b4b48bb02fdcdde6177d5/Untitled%201.png)

- 하단 Build Successful 확인이 되면  빌드 완료.

## 실행 방법

```bash
./gradlew bootRun
```

- 프로젝트 Root 폴더에서 Gradlew를 통한 Spring Application을 실행 합니다.
- Port : 18080

### 결과

![Untitled](Cody%20Maker%20ef634657662b4b48bb02fdcdde6177d5/Untitled%202.png)

- 18080 port 기반 Spring Boot Application 실행이되면 완료

## API 문서 및 H2 DB Console 확인 방법

- Swagger API 문서 : localhost:18080/docs
- H2 DB Console : localhost:18080/h2-console

# 추가 사항

## 도메인 구조

해당 서비스에서 식별되는 도메인의 경우 상품, 브랜드를 통해 해결이 가능하다.

- 하나의 브랜드는 N개의 상품을 가질 수 있는 1 : N 관계를 가지고 있다고 확인이 됨.

상품 도메인의 경우 8개의 하위 도메인으로 구성 할 수있다.
ex) 상의, 아우터, 바지, 스니커즈, 가방, 모자, 양말, 액세서리 추가 등등..

- > 각각의 내용은 구체 적인 요구사항이 들어오게될 확률이 높다는 생각이 들었음.

**장점**
-> 각 하위 도메인에 대한 구체적인 유즈케이스 처리가 가능해짐.
ex) 노란색 체크 무늬를 가진 상의에 대한 최저 가격 및 브랜드에 대한 정보 조회 등등...

**단점**
-> 해당 내용을 구체화 하게 되면 복잡도 상승에 대한 우려가 있음.

**결정**
-> 일단 상품 하나의 도메인에서 해당 유즈케이스를 처리하도록 진행 함.
-> 추후 구체적인 유즈케이스가 들어오게 된다면 그때 하나씩 도메인을 추가 정의해서 처리하는 방향으로 감.

각 도메인의 CRUD를 제외한 나머지 유즈케이스의 경우 상품에대한 최저 및 최고가와 같은 통계 내역을 제공해주는것으로 식별을 하였으며, 

여러개의 도메인을 사용하여 하나의 통계 내역을 제공해주는 역할이 크다고 생각이들어 통계와 같은 기능성 컨텍스트를 만들어 처리하는것으로 결정

## 프로젝트 패키지 구조

- 도메인 기반 MVC 아키텍처를 기반으로 구성을 진행

![Untitled](Cody%20Maker%20ef634657662b4b48bb02fdcdde6177d5/Untitled%203.png)

## 1. Brand

브랜드 컨텍스트 패키지

- 브랜드관련 도메인 모델 관리
- 브랜드 관련 API 제공

## 2. Product

상품 컨텍스트 패키지

- 상품 관련 도메인 모델 관리
- 상품 관련 API 제공

## 3. Stat

통계 컨텍스트 패키지

- 통계 내역 제공

## 4. Common

공통 모듈 패키지

- 설정 및 유틸성 로직 제공
