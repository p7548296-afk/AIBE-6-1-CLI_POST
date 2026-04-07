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
src/
├─ Main.java         ← 진입점
├─ App.java     ← 프로그램 실행 로직
├─ Article.java      ← 게시글 데이터 클래스
└─ Rq.java         ← 커맨드 명령어 요청 유틸 클래스
```

---

## 🧠 메서드 설계 예시


--- 

## 💬 실행 예시
```

```

---

## 🎯 개발 포인트 요약
