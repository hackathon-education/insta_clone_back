# Insta Clone - BE

Insta_clone의 백엔드 저장소입니다.  
**SpringBoot + MongoDB + Maven** 기반으로 개발하고 있습니다.

---

## 📂 폴더 구조

```plaintext
추후 추가 예정
```

---

## 🔖 브랜치 전략

- `main`: 운영 배포용 브랜치
- `dev`: 기본 개발 브랜치
- `type/설명/#이슈번호`: 기능/버그 작업 브랜치

---

## 📝 커밋 / 브랜치 / PR 컨벤션

| 구분       | 형식                   | 예시                            |
| ---------- | ---------------------- | ------------------------------- |
| **Issue**  | `[type] 제목`          | `[Feat] 회원가입 기능 추가`     |
| **PR**     | `[type] 제목`          | `[Fix] 로그인 오류 수정`        |
| **Commit** | `Gitmoji type: 메시지` | `♻️ refactor: 로그인 로직 정리` |
| **Branch** | `type/설명/#이슈번호`  | `feat/login-api/#12`            |

### ✅ 타입 리스트

| Gitmoji | Type       | Description                          |
| ------- | ---------- | ------------------------------------ |
| ✨      | `feat`     | 새로운 기능 추가                     |
| 🐛      | `fix`      | 버그 수정                            |
| 🚑️     | `!hotfix`  | 긴급 패치                            |
| 💄      | `design`   | UI 디자인 수정                       |
| 🎨      | `style`    | 코드 포맷팅, 세미콜론 등 스타일 변경 |
| ♻️      | `refactor` | 코드 리팩토링                        |
| 💡      | `comment`  | 주석 추가/수정                       |
| 📝      | `docs`     | 문서 작성/수정                       |
| ✅      | `test`     | 테스트 코드 추가/수정                |
| 🔧      | `chore`    | 설정 변경                            |
| 🚚      | `rename`   | 파일/폴더명 변경                     |
| 🔥      | `remove`   | 코드/파일 삭제                       |

---

## 🗂️ 이슈 & PR 작성 가이드

- 이슈: 템플릿 사용 (기능: `Feature Template` / 버그: `Bug Report Template`)
- PR: 템플릿 자동 적용
- 작업 흐름: 이슈 → 브랜치 → 작업 → PR → (리뷰 후) `dev` 머지

---

## 실행 방법

```bash
# 의존성 설치
.\mvnw.cmd clean install

# 개발 서버 실행
 .\mvnw.cmd spring-boot:run
```
