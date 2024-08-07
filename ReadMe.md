# 웃찾사 ( 이웃 찾는 사람들 )

2023.07.01 ~ 2023.07.17

https://github.com/choomi1217/neighbor.git

## **같은 취미, 같은 동네 사람들의 애플리케이션**

---

### 🔧API 설계

- 사용자 ( account )
    - 사용자 등록 `POST /accounts`
        - 최초 가입시 유저 정보가 없이 email과 password만 필요
    - 사용자 수정 `PUT /accounts`
        - Authentication required
        - phoneNumber, email 수정
    - 사용자 삭제 `DELETE /accounts`
    - 사용자 로그인 `POST /accounts/login`
        - 이메일 인증이 된 계정은 주민목록을 불러오도록 함.
        - 이메일 인증이 안 되면 주민등록을 할 수 없는 로직이라서 가능한 부분.
    - 사용자 로그아웃 `GET /accounts/logout`
        - 로그아웃은 서버에 저장된 세션을 제거하거나 클라이언트의 쿠키를 삭제하는 등의 작업을 포함합니다. 이러한 작업은 서버의 상태를 변경시키기 때문에, 이를 **`POST`**로 처리했습니다.
    - 사용자 이메일 인증 `POST /accounts/{id}/email-verification`
        - 사용자 등록 후, 주민등록을 하려면 이메일 인증이 필요함
    - 사용자 비밀번호 인증 `POST /accounts/{id}/password-verification`
        - 사용자 삭제 전 비밀번호 인증
        - 어드민 사용자 목록 조회 `GET /accounts`
    - 어드민 사용자 조회 `GET /accounts?username=james& ....`
- 주민 ( citizen **)**
    - 주민 등록 `POST /citizen`
        - 이메일 미인증시 예외 발생
    - 주민 수정 `PUT /citizen`
        - nickname, address 수정
    - 주민 삭제 `DELETE /citizen`
    - 같은 취미의 주민 찾기 `GET /citizen?hobby=...`
- 주소 ( address )
    - 주소 등록 `POST /address`
    - 주소 수정 `POST /address`
    - 주소 삭제 `DELETE /address`
    - 주소 인증 `POST /adress-verification/14142624.894167583,4505768.030621257`
        - Front-end에서 OpenLayers의 GeoLocation을 통해 들어온 GPS 데이터를 받아서 처리합니다.
        - request 형태는 {x, y} ‘ 14142624.894167583,4505768.030621257 ’입니다
    - 근처 주소 찾기 `GET /address?sido=&sigungu=&eupmyeondong=...`
        - 시도 범위 (다중선택)
        - 시군구 범위 (다중선택)
        - 읍면동 범위 (다중선택)
    - 본인 위치 주변 찾기 `GET /address/14142624.894167583,4505768.030621257`
- 예외
    - 사용자가 없을 때 `404 Not Found`
    - 사용자가 이미 존재할 때 `409 Conflict`
    - 사용자가 인증되지 않았을 때 `401 Unauthorized`
    - 사용자가 권한이 없을 때 `403 Forbidden`
    - 사용자가 잘못된 요청을 보냈을 때 `400 Bad Request`
    - 서버 내부 오류 `500 Internal Server Error`

---

### 🔧기술 

- java 17
  - geo coding
- spring 3.1.1
    - security
- swagger
- postgres
    - postgis

---

### 🔧인덱스 생성

- 공간 정보에 대한 인덱스를 생성합니다.
```sql
CREATE INDEX idx_citizen_location ON CitizenLocationEntity USING gist (ST_MakePoint(longitude, latitude));
```

---

### 🔧기능

### 근처 같은 취미 유저 찾기 기능
- 사용자의 현재 위치에 따라 근처 사용자를 찾아주는 기능
1. 사용자가 앱을 사용
2. 5초 주기로 사용자의 위치를 서버로 전송
3. 서버는 사용자의 위치를 받아서 주변 사용자를 찾아줌
4. 사용자는 주변 사용자를 확인할 수 있음

### 선택한 유저와 채팅하기 기능
- 근처 같은 취미의 유저와 채팅할 수 있는 기능
1. 사용자가 근처 유저를 찾음
2. 사용자가 유저를 선택하여 채팅을 요청
3. 유저는 채팅을 수락하거나 거절
4. 채팅이 수락되면 채팅이 가능
5. 채팅이 종료되면 채팅방이 삭제됨

---

### 🔧설계도
![erd](./erd.drawio.png)
![neighbor_account_join.png](neighbor_account_join.png)
![neihbor_citizen.drawio.png](neihbor_citizen.drawio.png)
---




