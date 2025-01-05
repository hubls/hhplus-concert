# 📂패키지 구조

```
└─hhplusconcert
    ├─application  # 비즈니스 유즈케이스 처리 (Facade 계층)
    ├─domain       # 핵심 도메인 로직 (콘서트, 결제, 사용자 등)
    ├─infra        # 외부 시스템 연동 (DB, MQ, API 등)
    ├─interfaces   # 외부 인터페이스 (Controller, Filter, Interceptor 등)
    ├─support      # 프로젝트 전반에 걸친 공통 기능
    └─mock         # Mock API(삭제 예정)
```