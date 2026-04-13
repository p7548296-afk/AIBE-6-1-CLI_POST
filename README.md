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

## 🧠 메서드 설계 예시

| 메서드명 | 설명 |
| :--- | :--- |
| run() | 앱 실행 루프 (입력 대기 및 명령어 처리) |
| writeArticle() | 게시글 작성 처리 |
| listArticles() | 게시글 목록 출력 |
| showDetail(int id) | 특정 글 상세 내용 출력 |
| updateArticle(int id) | 게시글 수정 처리 |
| deleteArticle(int id) | 게시글 삭제 처리 |
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
