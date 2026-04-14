# 📝 CLI 자바 텍스트 게시판 (콘솔 기반)

간단한 **Java 콘솔 프로그램**으로 구현된 텍스트 게시판입니다. Java 기본 문법, 객체지향 설계, 사용자 입력 처리, ArrayList를 활용한 데이터 관리를 연습할 수 있습니다.

---

## 🎯 개요

- **프로젝트 타입**: Java Console Application
- **빌드 도구**: Gradle
- **패키지**: `com.ll`
- **주요 기능**: 게시글의 CRUD(Create, Read, Update, Delete) 기능 구현

---

## 🧩 주요 기능

| 기능 | 설명 | 명령어 예시 |
|------|------|-----------|
| 📝 게시글 작성 | 새로운 게시글 생성 | `write` |
| 📋 게시글 목록 | 모든 게시글을 최신순으로 출력 | `list` |
| 🔍 게시글 상세보기 | 특정 게시글의 전체 내용 확인 | `detail 1` |
| ✏️ 게시글 수정 | 기존 게시글의 제목과 내용 수정 | `update 1` |
| 🗑️ 게시글 삭제 | 게시글 삭제 | `delete 1` |
| 🚪 프로그램 종료 | 프로그램 종료 | `exit` |

---

## 📂 프로젝트 구조

```
CLI_POST/
├── src/main/java/com/ll/
│   ├── Main.java          # 프로그램 진입점
│   ├── App.java           # 메인 애플리케이션 로직 (명령어 처리)
│   ├── Article.java       # 게시글 데이터 클래스
│   └── Rq.java            # 명령어 파싱 유틸 클래스
├── build.gradle.kts       # Gradle 빌드 설정
├── gradlew                # Gradle Wrapper (Linux/Mac)
├── gradlew.bat            # Gradle Wrapper (Windows)
└── README.md              # 프로젝트 문서
```

---

## 🏗️ 클래스 설계

### 1. **Main.java** - 진입점
프로그램의 시작점으로 App 인스턴스를 생성하고 `run()` 메서드를 호출합니다.

```java
public class Main {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}
```

### 2. **Article.java** - 게시글 데이터 클래스
게시글의 정보(id, title, content, regDate)를 관리합니다.

```java
public class Article {
    private int id;              // 게시글 번호
    private String title;        // 게시글 제목
    private String content;      // 게시글 내용
    private String regDate;      // 등록일 (yyyy-MM-dd)
}
```

**메서드:**
- `getId()` / `setId(int id)` - 게시글 번호 관리
- `getTitle()` / `setTitle(String title)` - 제목 관리
- `getContent()` / `setContent(String content)` - 내용 관리
- `getCurrentDate()` - 등록일 조회 (yyyy-MM-dd 형식)

### 3. **Rq.java** - 명령어 파싱 클래스
사용자의 명령어 문자열을 파싱하여 액션 이름과 파라미터를 추출합니다.

```java
public class Rq {
    private String actionName;           // 명령어 (write, list, detail, etc.)
    private Map<String, String> paramsMap; // 파라미터 맵 ({id: "1"})
}
```

**메서드:**
- `getActionName()` - 명령어 반환
- `getParam(String paramName, String defaultValue)` - 문자열 파라미터 조회
- `getParamAsInt(String paramName, int defaultValue)` - 정수형 파라미터 조회

**동작 예시:**
```
입력: "detail 1" → actionName: "detail", params: {id: "1"}
입력: "list"     → actionName: "list", params: {}
입력: "exit"     → actionName: "exit", params: {}
```

### 4. **App.java** - 메인 애플리케이션
게시글 관리 로직 및 사용자 상호작용을 담당합니다.

**주요 메서드:**
- `run()` - 프로그램 실행 루프
- `writeArticle()` - 게시글 작성 처리
- `listArticles()` - 게시글 목록 출력 (최신순)
- `showDetail(int id)` - 게시글 상세보기
- `updateArticle(int id)` - 게시글 수정 처리
- `deleteArticle(int id)` - 게시글 삭제 처리
- `findById(int id)` - ID로 게시글 조회

---

## 💬 실행 예시

```
== 게시글 앱 ==
명령어) write
제목: 자바 공부
내용: 자바 텍스트 게시판 만들기
=> 게시글이 등록되었습니다.

명령어) list
번호 | 제목       | 등록일
-----------------------------
1    | 자바 공부  | 2025-08-03

명령어) detail 1
번호: 1
제목: 자바 공부
내용: 자바 텍스트 게시판 만들기
등록일: 2025-08-03

명령어) update 1
제목 (현재: 자바 공부): Java 게시판
내용 (현재: 자바 텍스트 게시판 만들기): 콘솔 기반으로 구현
=> 게시글이 수정되었습니다.

명령어) list
번호 | 제목        | 등록일
-----------------------------
1    | Java 게시판 | 2025-08-03

명령어) delete 1
=> 게시글이 삭제되었습니다.

명령어) list
번호 | 제목 | 등록일
-----------------------------
(게시글 없음)

명령어) exit
프로그램을 종료합니다.
```

---

## ⚙️ 기술 스택

| 기술 | 설명 |
|------|------|
| **Java** | 프로그래밍 언어 |
| **Scanner** | 사용자 입력 처리 |
| **ArrayList** | 게시글 목록 관리 |
| **LocalDate** | 날짜 처리 (yyyy-MM-dd 형식) |
| **Gradle** | 빌드 도구 |

---

## 🎯 설계 포인트

### ✅ 객체지향 설계
- **Article 클래스 분리**: 게시글 데이터를 별도 클래스로 관리
- **Rq 클래스 분리**: 명령어 파싱 로직을 별도 클래스로 분리
- **App 클래스**: 애플리케이션 로직만 담당

### ✅ 입력 처리
- `Scanner`를 활용하여 사용자 명령어와 데이터 입력 처리
- `Rq` 클래스로 명령어 파싱 자동화

### ✅ 리스트 관리
- `ArrayList<Article>`로 동적 게시글 목록 관리
- 최신글이 위에 오도록 역순 출력

### ✅ 날짜 처리
- `LocalDate.now()`로 현재 날짜 자동 저장
- `DateTimeFormatter`로 yyyy-MM-dd 형식 통일

### ✅ 에러 처리
- 존재하지 않는 ID 조회 시 안내 메시지 출력
- ID 미입력 시 알림 처리

---

## 🚀 빌드 및 실행

### 빌드
```bash
./gradlew build
```

### 실행
```bash
./gradlew run
# 또는
java -cp build/classes/java/main com.ll.Main
```

---

## 📋 명령어 가이드

### write - 새 게시글 작성
```
명령어) write
제목: 제목 입력
내용: 내용 입력
=> 게시글이 등록되었습니다.
```

### list - 모든 게시글 목록 (최신순)
```
명령어) list
번호 | 제목 | 등록일
-----------------------------
```

### detail [id] - 특정 게시글 상세보기
```
명령어) detail 1
번호: 1
제목: 게시글 제목
내용: 게시글 내용
등록일: 2025-08-03
```

### update [id] - 게시글 수정
```
명령어) update 1
제목 (현재: 기존제목): 새 제목
내용 (현재: 기존내용): 새 내용
=> 게시글이 수정되었습니다.
```

### delete [id] - 게시글 삭제
```
명령어) delete 1
=> 게시글이 삭제되었습니다.
```

### exit - 프로그램 종료
```
명령어) exit
프로그램을 종료합니다.
```

---

## 🔄 데이터 흐름

```
사용자 입력
   ↓
Main.main() → App.run()
   ↓
명령어 입력 대기
   ↓
Rq 파싱 (actionName + params 추출)
   ↓
switch 문으로 명령어 처리
   ↓
Article 객체 생성/조회/수정/삭제
   ↓
ArrayList 관리
   ↓
결과 출력
```

---

## ✨ 특징

- ✅ **간단한 사용법**: 직관적인 명령어 기반 UI
- ✅ **자동 날짜 저장**: 게시글 작성 시 현재 날짜 자동 등록
- ✅ **최신글 우선 표시**: 목록 출력 시 최신글이 맨 위
- ✅ **타입 안전성**: 정수 파라미터 안전 처리
- ✅ **에러 처리**: 존재하지 않는 ID, 미입력 ID 처리
- ✅ **잘 정리된 코드**: 주석과 JavaDoc으로 문서화

---

## 📝 추가 기능 제안

- [ ] 게시글 검색 기능 (`search [keyword]`)
- [ ] 게시글 조회수 기능 (`count` 필드 추가)
- [ ] 파일 저장/불러오기 기능
- [ ] 게시글 정렬 옵션 (날짜순, 번호순, 제목순)
- [ ] 페이징 기능

---

## 📄 라이선스

이 프로젝트는 학습 목적으로 만들어졌습니다.

---

## 👤 작성자

- GitHub: [p7548296-afk](https://github.com/p7548296-afk)
- 저장소: [AIBE-6-1-CLI_POST](https://github.com/p7548296-afk/AIBE-6-1-CLI_POST)

---

## ✅ 요구사항 충족 현황

| 요구사항 | 상태 | 설명 |
|---------|------|------|
| 게시글 작성 | ✅ | `write` 명령어로 제목/내용 입력 |
| 게시글 목록 | ✅ | `list` 명령어로 전체 목록 출력 (최신순) |
| 게시글 상세보기 | ✅ | `detail [id]` 명령어로 특정 게시글 조회 |
| 게시글 수정 | ✅ | `update [id]` 명령어로 게시글 수정 |
| 게시글 삭제 | ✅ | `delete [id]` 명령어로 게시글 삭제 |
| 프로그램 종료 | ✅ | `exit` 명령어로 프로그램 종료 |
| Article 클래스 | ✅ | id, title, content, regDate 필드 |
| Main.java | ✅ | 프로그램 진입점 |
| App.java | ✅ | 메인 애플리케이션 로직 |
| Rq.java | ✅ | 명령어 파싱 유틸 |
| 날짜 처리 | ✅ | LocalDate.now()로 yyyy-MM-dd 형식 |
| 역순 출력 | ✅ | 최신글이 위에 오도록 설정 |
| 객체지향 설계 | ✅ | 클래스 분리 및 메서드 역할 분리 |
| 주석/문서화 | ✅ | JavaDoc 및 주석 포함 |
| README.md | ✅ | 완전한 프로젝트 문서 |

---

마지막 업데이트: 2025-08-03

