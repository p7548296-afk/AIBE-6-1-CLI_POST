# CLI 자바 서비스 만들기

Java 콘솔 기반 텍스트 게시판 프로젝트입니다.

## 개요

- 명령어 입력으로 게시글 CRUD를 수행합니다.
- 객체지향 설계와 테스트 주도 방식으로 기능을 확장했습니다.
- 현재는 조회수/검색 기능까지 구현된 상태입니다.

## 구현 기능

| 기능 | 명령어 | 설명 |
| --- | --- | --- |
| 게시글 작성 | `write` | 제목/내용 입력 후 게시글 등록 |
| 게시글 목록 | `list` | 최신글 우선으로 목록 출력 |
| 게시글 상세보기 | `detail [id]` | 게시글 상세 조회 + 조회수 증가 |
| 게시글 수정 | `update [id]` | 제목/내용 수정 |
| 게시글 삭제 | `delete [id]` | 게시글 삭제 |
| 게시글 검색 | `search [keyword]` | 제목/내용 포함 검색 |
| 종료 | `exit` | 프로그램 종료 |

## 게시글 데이터 구조

```java
class Article {
    int id;
    String title;
    String content;
    String regDate; // yyyy-MM-dd
    int count;      // 조회수
}
```

## 프로젝트 구조

```
src/
├─ main/
│  └─ java/com/ll/
│     ├─ Main.java           // 진입점
│     ├─ App.java            // 입출력 + 명령 라우팅
│     ├─ ArticleService.java // 게시글 비즈니스 로직
│     ├─ Article.java        // 게시글 도메인 모델
│     └─ Rq.java             // 명령어 파싱 유틸
└─ test/
   └─ java/com/ll/
      ├─ AppTest.java
      ├─ ArticleServiceTest.java
      ├─ ArticleTest.java
      └─ RqTest.java
```

## 실행 방법

```bash
./gradlew run
```

Windows PowerShell:

```powershell
.\gradlew.bat run
```

## 실행 예시

```text
명령어) write
제목: 자바 공부
내용: 자바 텍스트 게시판 만들기
=> 게시글이 등록되었습니다.

명령어) list
번호 | 제목 | 등록일 | 조회수
-----------------------------
1    | 자바 공부  | 2026-04-15 | 0

명령어) detail 1
번호: 1
제목: 자바 공부
내용: 자바 텍스트 게시판 만들기
등록일: 2026-04-15
조회수: 1

명령어) search 자바
번호 | 제목 | 등록일 | 조회수
-----------------------------
1    | 자바 공부  | 2026-04-15 | 1

명령어) exit
프로그램을 종료합니다.
```

## 테스트

```bash
./gradlew test
```

Windows PowerShell:

```powershell
.\gradlew.bat test
```

- 단정문은 AssertJ(`assertThat`) 스타일을 사용합니다.

## 개발 포인트

- `Scanner` 기반 입력 처리
- `ArrayList<Article>` 기반 목록 관리
- `LocalDate.now()` 기반 등록일 관리
- 최신글 우선(역순) 목록 출력
- `App`/`ArticleService`/`Article`/`Rq` 책임 분리
- JUnit5 + AssertJ 기반 단위 테스트 구성

## 추가 기능 체크리스트

- [x] 게시글 조회수 기능 (`count`)
- [x] 게시글 검색 기능 (`search [keyword]`)
- [ ] 파일 저장/불러오기
- [ ] 게시글 정렬 옵션 (날짜순, 번호순 등)
