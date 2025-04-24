# 🍕FoodOrder Basic Rest Api 프로젝트
## 🛠️ 주요 개발 환경

| 항목           | 버전/설정                           |
| -------------- | ----------------------------------- |
| Java          | 11                                 |
| Spring Boot   | 2.7.x                               |
| 빌드 도구      | Gradle     |
| DB            | 없음 (메모리 기반 저장)              |

## 🧩 주요 기능 요약
### [사용자 API] `/api/users`

| 메서드 | URL                          | 설명                                   |
| ------ | ---------------------------- | -------------------------------------- |
| POST   | /api/users/register           | 회원가입                               |
| POST   | /api/users/login              | 로그인 (JWT 토큰 발급)                 |
| POST   | /api/users/change-password    | 비밀번호 변경                          |

### 보안 설정

- **JWT 인증**: `/api/users/login` API에서 사용자가 로그인 시 JWT 토큰을 발급받고, 이후 모든 API 호출은 헤더에 토큰을 포함하여 인증됩니다.
- **패스워드 암호화**: `BCryptPasswordEncoder`를 사용하여 패스워드를 암호화합니다.
- **경로 설정**:
  - `/api/users/login`, `/api/users/register`: 인증 없이 접근 가능
  - `/api/orders/**`: 인증된 사용자만 접근 가능
- **JWT 필터**: `JwtAuthenticationFilter`가 모든 요청을 필터링하여 유효한 토큰을 확인하고 인증을 처리합니다.
### [음식 API] `/api/foods`

| 메서드 | URL                       | 설명                           |
| ------ | ------------------------- | ------------------------------ |
| GET    | /api/foods                 | 모든 음식 목록 조회            |
| GET    | /api/foods/{id}            | 특정 음식 상세 조회            |
| POST   | /api/foods                 | 음식 추가                      |
| PUT    | /api/foods/{id}            | 음식 수정                      |
| DELETE | /api/foods/{id}            | 음식 삭제                      |
| GET    | /api/foods/search          | 음식 이름 검색                 |
| GET    | /api/foods/category/{category} | 카테고리별 음식 조회         |

### [주문 API] `/api/orders`

| 메서드 | URL                     | 설명                           |
| ------ | ----------------------- | ------------------------------ |
| GET    | /api/orders              | 모든 주문 목록 조회            |
| GET    | /api/orders/{id}         | 특정 주문 상세 조회            |
| POST   | /api/orders              | 주문 생성                      |
| PUT    | /api/orders/{id}/status  | 주문 상태 수정                 |
| DELETE | /api/orders/{id}         | 주문 삭제                      |
| POST   | /api/orders/{id}/pay/stripe | Stripe 결제 (가맹점 키 필요)                  |
| POST   | /api/orders/{id}/pay/kakao | 카카오페이 결제 (가맹점 키 필요)                |
