# CLIPost

---

## 🎯 개요

이 과제는 **Java 콘솔 프로그램**으로 간단한 텍스트 게시판을 구현하는 프로젝트입니다.
Java 기본 문법, 클래스 및 객체지향 설계, 사용자 입력 처리, 그리고 데이터 저장 구조(ArrayList 등)를 연습합니다.

---

## 🧩 전체 기능  및 명령어
- 게시글 작성 :write
  - 명령어 입력 시 제목/내용을 받아 새 게시글 생성
- 게시글 목록 :list
  - 명령어 입력 시 모든 게시글을 번호순으로 출력
- 게시글 상세보기	: detail [id]
  - 명령어로 특정 게시글 내용을 전체 확인
- 게시글 수정	: update [id]
  - 명령어로 제목/내용을 수정
- 게시글 삭제	: delete [id]
  - 명령어로 해당 글 삭제
- 종료	: exit
  - 명령어로 프로그램 종료
- 명령어 목록 : help
  - 명령어를 보여줍니다

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

## ⚙️ 주요 클래스 및 파일 구조 예시
```
src
    ├── main
    │   ├── java
    │   │   ├── App.java
    │   │   ├── Container.java
    │   │   ├── Main.java
    │   │   ├── controller
    │   │   │   └── ArticleController.java
    │   │   ├── domain
    │   │   │   └── Article.java
    │   │   ├── repository
    │   │   │   └── ArticleRepository.java
    │   │   ├── service
    │   │   │   └── ArticleService.java
    │   │   └── util
    │   │       └── Rq.java
    │   └── resources
    └── test
        ├── java
        └── resources
```

---

## 🧠 메서드 설계

### 1. App / Container (실행 및 조립)
| 메서드명 | 설명 |
| :--- | :--- |
| run() | 앱 실행 메인 루프. Rq를 통한 명령어 파싱 및 컨트롤러 라우팅 담당 |
| init() | 프로그램 시작 시 필요한 객체(Controller, Service, Repo) 생성 및 의존성 주입(DI) |
| scClose() | 프로그램 종료 시 Scanner 자원 해제 및 메모리 누수 방지 |

### 2. ArticleController (사용자 인터페이스 - View)
| 메서드명 | 설명 |
| :--- | :--- |
| doWrite() | 게시글 작성 UI. 제목/내용 입력받아 서비스에 전달 |
| showList() | 전체 게시글 목록 출력. Container.DATE_FORMATTER를 통한 날짜 포매팅 적용 |
| showDetail(Rq rq) | 특정 게시글 상세 UI. try-catch로 존재하지 않는 글 예외 처리 |
| doModify(Rq rq) | 게시글 수정 UI. 기존 내용을 보여주고 새 내용을 입력받아 수정 요청 |
| doDelete(Rq rq) | 게시글 삭제 처리 결과 출력 |
| showHelp() | 사용 가능한 모든 명령어와 사용법 가이드 출력 |

### 3. ArticleService (비즈니스 로직 - 핵심)
| 메서드명 | 설명 |
| :--- | :--- |
| write(title, content) | 게시글 생성 로직 수행 및 생성된 게시글 번호(ID) 반환 |
| getArticles() | 게시글 목록 반환 (비즈니스 규칙에 따른 데이터 가공) |
| getArticle(id) | 단건 조회 및 검증. 글이 없을 경우 RuntimeException 발생 |
| modify(id, title, content) | 조회된 게시글 객체의 상태 변경 (Dirty Checking 방식) |
| remove(id) | 게시글 존재 여부 확인 후 삭제 로직 실행 |

### 4. ArticleRepository (데이터 액세스 - Storage)
| 메서드명 | 설명 |
| :--- | :--- |
| save(title, content) | Article 객체 생성 후 Map에 저장. 생성된 ID 리턴 |
| findAll() | Map의 모든 Value를 List로 변환하여 반환 (최신순 정렬) |
| findById(id) | Map에서 ID를 키값으로 객체 조회 |
| delete(id) | Map에서 해당 ID의 데이터 완전 삭제 |
--- 

## 💬 실행 예시
```
== 자바 텍스트 게시판 시작 ==
명령어 모음
등록 : write
목록 : list
상세 : detail {id}
수정 : update {id}
삭제 : delete {id}
도움 : help
종료: exit
명령어: list
게시글이 존재하지 않습니다.
명령어: write
제목: title1
내용: ct1
1번 게시글이 등록되었습니다.
명령어: list
번호 | 제목       | 최초 등록일 | 최근 수정일
--------------------------------------------------
1    | title1     | 2026-04-13 18:05 | 2026-04-13 18:05
명령어: derail 1
존재하지 않는 명령어입니다.
명령어: detail 1
번호: 1
제목: title1
내용: ct1
최초 등록일: 2026-04-13T18:05:02.026255
최근 수정일: 2026-04-13T18:05:02.026255
명령어: write 
제목: title 2
내용: ct2
2번 게시글이 등록되었습니다.
명령어: update 1
제목(기존: title1): 1111
내용(기존: ct1): 11111
1번 게시글이 수정되었습니다.
명령어: list
번호 | 제목       | 최초 등록일 | 최근 수정일
--------------------------------------------------
2    | title 2    | 2026-04-13 18:05 | 2026-04-13 18:05
1    | 1111       | 2026-04-13 18:05 | 2026-04-13 18:05
명령어: delete 2
2번 게시글이 삭제되었습니다.
명령어: list
번호 | 제목       | 최초 등록일 | 최근 수정일
--------------------------------------------------
1    | 1111       | 2026-04-13 18:05 | 2026-04-13 18:05
명령어: exit
프로그램을 종료합니다.

스캐너는 닫았으니 메모리 누수는 걱정 말라
```

---

## 🎯 개발 포인트 요약

### 1. 객체지향 설계 및 계층 분리 (3-Tier Architecture)
* **Controller - Service - Repository** 구조를 적용하여 각 클래스의 책임을 명확히 분리했습니다.
* **Controller**: 사용자 입출력(UI) 및 요청 라우팅 담당
* **Service**: 비즈니스 로직 수행 및 데이터 검증(Validation) 담당
* **Repository**: 데이터 저장소(Map) 직접 접근 및 CRUD 수행 담당

### 2. 의존성 주입 (Dependency Injection) 및 컨테이너 활용
* **Container** 클래스를 통해 객체의 생명주기를 관리하고, 생성자 주입 방식을 사용하여 결합도를 낮췄습니다.
* 프로그램 시작 시 모든 의존 객체를 조립하고, 전역에서 일관된 객체 인스턴스를 사용하도록 구현했습니다.

### 3. 예외 처리 중심의 비즈니스 로직
* `RuntimeException`을 활용하여 데이터 부재 등 예외 상황을 서비스 계층에서 검증하고 던지도록 설계했습니다.
* 컨트롤러에서는 `try-catch` 블록을 통해 예외 메시지를 사용자에게 일관된 형식으로 출력합니다.

### 4. 효율적인 데이터 관리 및 유틸리티 활용
* **HashMap**을 사용하여 ID 기반의 게시글 조회를 $O(1)$ 시간 복잡도로 최적화했습니다.
* **Rq 클래스**: 복잡한 명령어 파싱 로직을 별도 객체로 분리하여 코드 가독성을 높였습니다.
* **자원 관리**: `Shutdown Hook`을 등록하여 프로그램 강제 종료 시에도 `Scanner` 등 시스템 자원이 안전하게 해제되도록 방어적으로 설계했습니다.

### 5. 사용자 경험 (UX) 및 가독성 고려
* `java.time` 패키지를 활용하여 등록일과 수정일을 구분하여 관리합니다.
* 모든 출력 데이터에 `DateTimeFormatter`를 적용하여 `yy-MM-dd HH:mm` 형식의 일관된 UI를 제공합니다.
