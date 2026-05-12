# Yeoun Backend

고인의 사진, 음성, 인터뷰 답변을 기반으로 디지털 추모 페르소나를 생성하고 대화할 수 있도록 지원하는 Spring Boot 백엔드 서버입니다.

Spring Boot는 인증, 권한 검증, 페르소나 관리, 대화 세션 관리, Safety Guard, FastAPI 연동 오케스트레이션을 담당합니다. 실제 AI 추론은 WireGuard 터널을 통해 연결된 로컬 AI 서버의 FastAPI에서 수행합니다.

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Security
- OAuth2 Resource Server / JWT
- Spring Data JPA
- PostgreSQL
- Gradle
- Lombok

## System Architecture

```text
Mobile Web / PWA
        |
        v
Next.js Frontend + BFF
        |
        v
Spring Boot Backend
- JWT 인증/인가
- 사용자/페르소나 관리
- 대화 세션 관리
- Safety Guard
- FastAPI 작업 위임
- 미디어 스트리밍
        |
        | WireGuard VPN
        v
Local AI Server
- FastAPI
- PostgreSQL
- Local LLM
- Voice Cloning TTS
- Talking Head Model
- File System Storage
