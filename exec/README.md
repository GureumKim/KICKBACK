# README

## 1️⃣ Description

---

## 2️⃣ FRONT-END

---

[FRONT-END 파일 구조](https://www.notion.so/FRONT-END-5e3a48e09d644e7d840c937252ade9aa?pvs=21)

- 메인 페이지
    
    ![Untitled](README%20e34149f1b96b433e874ed0442cecbcc6/Untitled.png)
    
- 회원가입 페이지
    
    ![Untitled](README%20e34149f1b96b433e874ed0442cecbcc6/Untitled%201.png)
    
- 랭킹 페이지
    
    ![Untitled](README%20e34149f1b96b433e874ed0442cecbcc6/Untitled%202.png)
    

## 3️⃣ WEB SERVER

---

[WEB SERVER 파일 구조](https://www.notion.so/WEB-SERVER-67b26ebecc7f489bba8c1e76f8bb8066?pvs=21)

### 소개

- Spring Security와 JWT 토큰을 사용하여 인증 및 인가 시스템을 구현했습니다. 

- Redis를 활용하여 refreshToken을 저장하고 빠른 조회를 가능하게 구현했습니다.

- Jasypt를 통해 환경 변수를 암호화하여 안전하게 관리하였습니다.

- 클라우드 스토리지(파이어베이스)를 사용하여 서버 저장소 부담을 감소시켰습니다.

- JPA 및 @Query 어노테이션을 사용하여 유연한 데이터베이스 접근을 구현하였습니다.

### 📌 인증/인가

spring security, jwt 토큰 사용

jwt 토큰을 활용

redis를 활용하여 accesstoken 재 발급 요청에 대한 빠른 데이터 조회 가능

중복 로그인 방지

jasypt 활용한 환경 변수 암호화

~~+) vault ..?~~

### 📌비동기 통신 활용 (async function)

### 📌 JPA 및 @Query 활용 메서드 유연하게…?

### 📌 파이어베이스 활용, 서버 저장소 부담 감소

### ERD

![Untitled](README%20e34149f1b96b433e874ed0442cecbcc6/Untitled%203.png)

## 4️⃣ BUSINESS SERVER

---

[BUSINESS SERVER 파일 구조](https://www.notion.so/BUSINESS-SERVER-a6cf3b944af8472984605b2ef0b7c152?pvs=21)

## 📌 HOW TO

### **Netty**

- 자바 네트워크 애플리케이션 프레임워크
- 비동기 이벤트 기반 네트워크 응용프로그램 프레임워크

**Reactor Pattern으로 비동기 Non-Blocking TCP 서버 구현**

**ChannelPipeline에 Handler 등록**

- LineBasedFrameDecoder
- 사용자 정의 ChannelHandlerAdapter

| 메서드 | 설명 |
| --- | --- |
| handlerAdded | client와 최초로 연결되었을 경우
- logging |
| handlerRemoved | client와의 연결이 끊겼을 경우
- 연결이 끊어진 session 삭제
- logging |
| exceptionCaught | 예외 처리
- logging |

### 💽 데이터 처리 방법

- **MessagePack(네트워크 통신 데이터 직렬화 라이브러리) 사용**
    - 송신 데이터 **MessageBufferPacker**
        
        ```java
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        
        byte[] bytes;
        packer.packArrayHeader(2);     // 배열의 Header 지정
        packer.packString("userList"); // 문자열 - packString , 정수 - packInt
        packer.packString(String.valueOf(channel.getSessionsName()));
        bytes = packer.toByteArray();  // 바이트 배열로 변환후 송신
        ```
        
    - 수신 데이터 **MessageUnpacker**
        
        ```java
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(request);
        
        int arrayLength = unpacker.unpackArrayHeader();  // 배열 Header unpacking
        Type type = Type.findByIndex(unpacker.unpackInt()); // unpackInt - 타입 반환
        String userName = unpacker.unpackString();       // 문자열 - unpackString
        ```
        
    - Type 지정 **ENUM**
        
        ![Untitled](README%20e34149f1b96b433e874ed0442cecbcc6/Untitled%204.png)
        

### 🌊 CLIENT - BUSINESS SERVER 데이터 흐름

### ① LOBBY & CHANNEL

- Unity 로그인 후 Busniess Server 최초접속
- Business Server에서 Type 확인 후 case 별 handler 호출
- 데이터 확인 후 각 Channel 에 Broadcasting

![Untitled](README%20e34149f1b96b433e874ed0442cecbcc6/Untitled%205.png)

## 5️⃣ CHATTING SERVER

---

[CHATTING SERVER 파일 구조](https://www.notion.so/CHATTING-SERVER-b5ab3bc680f04b1ca359fc0e5bb92505?pvs=21)

## 6️⃣ LIVE SERVER

---

## 7️⃣ UNITY

---

- 회원 가입
- 로그인
- 로비
- 방 만들기
- 대기방

- PC 버전
    - 인트로
        
        ![1.gif](README%20e34149f1b96b433e874ed0442cecbcc6/1.gif)
        
        - 게임 시작 시 Main Camera의 Transform을 변환 시켜 게임 인트로 및 카운트 다운 구현
        - 인트로 및 카운트 다운 진행 중 움직이지 못하도록 제한
    - 게임 플레이
        
        ![3.gif](README%20e34149f1b96b433e874ed0442cecbcc6/3.gif)
        
        - 키보트 버튼 클릭에 따라 Horizontal, Vertical 값을 Raw 형태로 받아 -1, 1로 입력값으로 받음
        - Rigidbody를 통해 AddForce 등을 통해 Transform 변화를 통해 움직임 구현
        - Left Shift를 눌렀을 때 Drift 발동, Player의 회전 속도를 상승 시켜 빠른 회전을 통해 드리프트 효과 구현
            
            ![4.gif](README%20e34149f1b96b433e874ed0442cecbcc6/4.gif)
            
            ![2.gif](README%20e34149f1b96b433e874ed0442cecbcc6/2.gif)
            
        - 드리프트 시 부스터 게이지 충전
        - 드리프트 시 TrailRender 생성을 통해 스키드 마크 구현
        - 부스터 최대 2개 까지 충전
        - 부스터 사용 동안 중첩 부스터 사용 제한
        - 부스터 사용 시 ParticleSystem 재생, 불꽃 효과 및 스피드 라인을 통해 속도감 있는 부스터 구현
        - OnTrigger 이벤트를 통해 부스터 패드를 밟았을 시에도 부스터 발동
            
            ![4.gif](README%20e34149f1b96b433e874ed0442cecbcc6/4%201.gif)
            
        - RayCast를 통해 땅 감지 및 점프 기능 구현
            
            ![5.gif](README%20e34149f1b96b433e874ed0442cecbcc6/5.gif)
            
            ![6.gif](README%20e34149f1b96b433e874ed0442cecbcc6/6.gif)
            
            ![7.gif](README%20e34149f1b96b433e874ed0442cecbcc6/7.gif)
            
        - 레벨 디자인에 투명한 체크 포인트 배치
        - 체크 포인트를 지날 때 리스폰 Transform 초기화
        - 맵에 끼거나 맵 밖으로 나가졌을 때 데드존에 떨어졌을 때 리스폰
        - 각 체크 포인트 및 데드존은 OnTrigger 이벤트를 통해 초기화
        - 체크 포인트를 순서대로 통과했을 때 랩 측정
        - 올바르게 체크 포인트를 통과하지 못할 경우 경고 메세지 출력 후 강제 리스폰
        - 각 맵 별 정해진 랩을 모두 통과 했을 시 게임 종료
- 안드로이드 버전
    - 앱 다운로드 후 실행
        
        ![1.gif](README%20e34149f1b96b433e874ed0442cecbcc6/1%201.gif)
        
    - 인트로
        
        ![2.gif](README%20e34149f1b96b433e874ed0442cecbcc6/2%201.gif)
        
        - 게임 시작 시 Main Camera의 Transform을 변환 시켜 게임 인트로 및 카운트 다운 구현
        - 인트로 및 카운트 다운 진행 중 움직이지 못하도록 제한
    - 게임 플레이
        
        ![3.gif](README%20e34149f1b96b433e874ed0442cecbcc6/3%201.gif)
        
        ![4.gif](README%20e34149f1b96b433e874ed0442cecbcc6/4%202.gif)
        
    - 조이스틱의 움직임에 따라 Horizontal, Vertical 값을 Raw 형태로 받아 -1, 1로 입력값으로 받음
    - Rigidbody를 통해 AddForce 등을 통해 Transform 변화를 통해 움직임 구현
    - 각각의 기능들을 OnClick 혹은 Event Trigger를 통해 버튼에 담아 버튼 클릭 시 기능이 구현
    - Drift를 눌렀을 때 Player의 회전 속도를 상승 시켜 빠른 회전을 통해 드리프트 효과 구현
    - 드리프트 시 부스터 게이지 충전
    - 드리프트 시 TrailRender 생성을 통해 스키드 마크 구현
    
    ![8.gif](README%20e34149f1b96b433e874ed0442cecbcc6/8.gif)
    
    ![5.gif](README%20e34149f1b96b433e874ed0442cecbcc6/5%201.gif)
    
    - 부스터 최대 2개 까지 충전
    - 부스터 사용 동안 중첩 부스터 사용 제한
    - 부스터 사용 시 ParticleSystem 재생, 불꽃 효과 및 스피드 라인을 통해 속도감 있는 부스터 구현
    - OnTrigger 이벤트를 통해 부스터 패드를 밟았을 시에도 부스터 발동
        
        ![7.gif](README%20e34149f1b96b433e874ed0442cecbcc6/7%201.gif)
        
        ![9.gif](README%20e34149f1b96b433e874ed0442cecbcc6/9.gif)
        
        ![10.gif](README%20e34149f1b96b433e874ed0442cecbcc6/10.gif)
        
    
    - 다양한 장애물 배치를 통해 색다른 레이싱 구현
    - RayCast를 통해 땅 감지 및 점프 기능 구현
    - 레벨 디자인에 투명한 체크 포인트 배치
    - 체크 포인트를 지날 때 리스폰 Transform 초기화
    - 맵에 끼거나 맵 밖으로 나가졌을 때 데드존에 떨어졌을 때 리스폰
    - 각 체크 포인트 및 데드존은 OnTrigger 이벤트를 통해 초기화
    - 체크 포인트를 모두 통과했을 때 랩 측정
    - 각 맵 별 정해진 랩을 모두 통과 했을 시 게임 종료
    
    ![Screenshot_20240517_102358_KICKBACK.jpg](README%20e34149f1b96b433e874ed0442cecbcc6/Screenshot_20240517_102358_KICKBACK.jpg)
    
    ![Screenshot_20240517_102435_KICKBACK.jpg](README%20e34149f1b96b433e874ed0442cecbcc6/Screenshot_20240517_102435_KICKBACK.jpg)
    
    - 다양한 맵 구성
    - 비탈길에서 Physics Raycast를 통해 지면과 붙어 있는지 확인 후 자연스러운 움직임 구현

- 축구 모드
- 랭킹 페이지
    
    <aside>
    <img src="https://www.notion.so/icons/chess-queen_yellow.svg" alt="https://www.notion.so/icons/chess-queen_yellow.svg" width="40px" /> 랭킹 시스템
    
    - 맵 별 랩타임 기록
    </aside>
    
    ![8.gif](README%20e34149f1b96b433e874ed0442cecbcc6/8%201.gif)
    
    ![Untitled](README%20e34149f1b96b433e874ed0442cecbcc6/Untitled%206.png)
    
    - 게임 종료 후 자신이 기록한 랩 타임은 HTTP 프로토콜 통신을 통해 requestUrl에 PUT 요청 보냄
    - 갱신된 랩 타임은 웹페이지 랭킹 페이지에서 확인

![Untitled](README%20e34149f1b96b433e874ed0442cecbcc6/Untitled%207.png)