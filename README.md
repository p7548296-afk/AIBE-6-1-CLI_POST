# 📝 CLIPost: Java CLI 기반 텍스트 게시판

---

## 🎯 개요

이 프로젝트는 **Java 콘솔 환경**에서 게시판 시스템입니다.
Java의 기본 문법 **객체지향 설계(OOP)**, **계층형 아키텍처(3-Tier Architecture)**, 그리고 **테스트 주도적인 개발(Test-Driven Approach)**을 실습하는 데 중점을 두었습니다.

---

## 🧩 전체 기능 및 명령어

사용자는 콘솔 입력을 통해 아래 명령어를 수행할 수 있습니다.
URL 쿼리 스트링 형식(`?key=value&...`)

- **게시글 작성** : `write`
  - 제목과 내용을 입력받아 고유 번호를 가진 새 게시글을 생성합니다.
- **게시글 목록** : `list?page={번호}&pagesize={크기}`
  - 현재 저장된 모든 게시글을 최신순(번호 역순)으로 출력합니다.
- **게시글 상세보기** : `detail?id={번호}`
  - 특정 ID의 게시글 제목, 내용, 등록일, 수정일을 상세히 확인합니다.
- **게시글 수정** : `update?id={번호}`
  - 특정 게시글의 제목과 내용을 수정하며, 수정 시각이 자동으로 갱신됩니다.
- **게시글 삭제** : `delete?id={번호}`
  - 특정 ID의 게시글을 시스템에서 완전히 제거합니다.
- **도움말** : `help`
  - 사용 가능한 모든 명령어 가이드를 출력합니다.
- **프로그램 종료** : `exit`
  - 프로그램을 안전하게 종료하며 사용 중인 자원을 해제합니다.

---

## 게시글 객체 구조
```java
  class Article {
    int id;
    String title;
    String content;
    LocalDateTime regDate;
    LocalDateTime modDate;
}
```

---

## ⚙️ 주요 클래스 및 파일 구조

```
src
├── main
│   └── java
│       ├── App.java            # 앱 메인 루프 및 컨트롤러 라우팅
│       ├── Container.java      # 의존성 관리 및 전역 객체 저장소
│       ├── Main.java           # 프로그램 시작점
│       ├── controller
│       │   └── ArticleController.java  # 사용자 인터페이스 및 입출력 제어
│       ├── domain
│       │   └── Article.java    # 게시글 데이터 도메인 객체
│       ├── global
│       │   └── dto             # 전역적으로 사용되는 데이터 전송 객체(DTO)
│       │       ├── Page.java   # 페이징 결과 데이터 및 메타정보 객체
│       │       ├── Pageable.java # 페이징 요청 규격(페이지 번호, 사이즈) 객체
│       │       └── Rq.java     # URL 쿼리 파싱 및 명령어 해석 DTO
│       ├── repository
│       │   └── ArticleRepository.java # 데이터 스토리지 직접 접근 (In-Memory)
│       └── service
│           └── ArticleService.java    # 비즈니스 로직 및 페이징 가공 계층
└── test
    └── java
        ├── AppTest.java        # 전체 시나리오 통합 테스트 (AppTestRunner 활용)
        ├── controller
        │   └── ArticleControllerTest.java # 컨트롤러 입출력 단위 테스트
        ├── repository
        │   └── ArticleRepositoryTest.java # 저장소 로직 및 정렬 테스트
        ├── service
        │   └── ArticleServiceTest.java    # 비즈니스 규칙 및 예외 발생 테스트
        └── util
            ├── RqTest.java     # 파싱 예외 케이스(공백 처리 등) 테스트
            └── TestUtil.java   # 입출력 가로채기(System.out/in) 유틸리티
```
---

## 🧠 계층별 상세 설계

### 1. ArticleController (View/Controller)
* **역할**: 사용자 인터랙션 담당. `Rq`에서 필요한 파라미터를 추출하여 서비스 계층에 전달합니다.
* **특징**: 서비스 계층의 예외를 가로채어 사용자에게 에러 메시지를 출력합니다.

### 2. ArticleService (Business Logic)
* **역할**: 핵심 비즈니스 로직 수행, 페이징 계산 및 데이터 검증을 담당합니다.
* **특징**: `getArticle` 호출 시 데이터가 없으면 `RuntimeException`을 던져 방어적으로 설계했습니다. `Optional`을 활용한 선언적 프로그래밍을 적용했습니다. `(page - 1) * pageSize`을 통해 리스트를 슬라이싱하며, `Math.ceil`을 사용하여 전체 페이지 수를 계산합니다.

### 3. ArticleRepository (Data Access)
* **역할**: `HashMap`을 사용한 인메모리 데이터 저장소입니다.
* **특징**: 조회 시 `Optional<Article>`을 반환하여 Null 안정성을 확보하고, 최신순 정렬 리스트를 제공합니다.

### 4. Rq & TestUtil (Utilities)
* **Rq**: 역할: ?, &, = 기호를 기준으로 명령어를 파싱하여 Map<String, String> 구조로 저장합니다.
* **TestUtil**: 테스트 시 표준 입출력을 메모리 스트림으로 전환하여 자동화된 입출력 검증을 가능하게 합니다.

### 5. Pageable & Page (Paging Abstraction)
* **Pageable**: "몇 페이지를, 몇 개씩 보고 싶은가"에 대한 요청을 캡슐화한 객체입니다. `getOffset()` 메서드를 통해 데이터 추출 시작점을 계산합니다.
* **Page<T>**: 잘라온 데이터(`content`)와 함께 전체 페이지 수, 현재 페이지 번호 등 페이징 처리에 필요한 메타데이터를 담는 범용 제네릭 객체입니다.

---

## 💬 실행 예시
```
== 자바 텍스트 게시판 시작 ==
명령어: write
제목: 페이징 테스트
내용: 내용입니다.
1번 게시글이 등록되었습니다.

명령어: list?page=1&pagesize=3
번호 | 제목           | 등록일
1    | 페이징 테스트   | 26-04-15 11:50
--- 현재 페이지: 1 / 1 ---

명령어: detail?id=1
번호: 1
제목: 페이징 테스트
내용: 내용입니다.
등록일: 26-04-15 11:50
수정일: 26-04-15 11:50

명령어: update?id=1
제목(기존: 페이징 테스트): 수정 제목
내용(기존: 내용입니다.): 수정 내용
1번 게시글이 수정되었습니다.

명령어: exit
프로그램을 종료합니다.
스캐너는 닫았으니 메모리 누수는 걱정 말라
```

---

## 🎯 개발 핵심 역량

### 계층형 아키텍처  적용
* 객체의 책임을 `Controller-Service-Repository`로 분리하여 코드의 유지보수성과 가독성을 챙겼습니다.

### 의존성 주입 (Dependency Injection)
* `Container` 클래스와 생성자 주입 방식을 사용하여 클래스 간 결합도를 낮췄으며, 이를 통해 테스트 시 가짜(Mock) 객체나 시뮬레이션용 `Scanner` 주입이 용이하도록 설계했습니다.

### 검증된 코드 (Comprehensive Testing)
* 단위 테스트부터 전체 시나리오를 검증하는 통합 테스트까지 구축했습니다.
  * **성공 케이스**: 정상적인 게시판 CRUD 흐름 확인
  * **실패 케이스**: 존재하지 않는 ID 접근, 잘못된 입력 형식 등에 대한 예외 처리 검증

### 페이징 추상화(Pagination Abstraction) 구현
* Spring Data JPA의 표준 규격을 모티브로 하여 Pageable과 Page 객체를 도입했습니다. 이를 통해 서비스 계층의 파라미터를 단순화하고, 페이징 로직의 재사용성과 확장성을 시도했습니다.

### 자원 및 예외 관리
* `Shutdown Hook`을 활용해 프로그램 강제 종료 시에도 자원(`Scanner`)이 안전하게 해제되도록 설계했으며, `Optional`을 도입하여  `NullPointerException` 위험을 제거했습니다.