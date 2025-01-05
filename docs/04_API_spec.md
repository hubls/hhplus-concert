# 📚콘서트 예약 시스템 API 명세서

## 1. 대기열 토큰 발급

### Description
- 대기열 큐에 사용자를 추가하고 토큰을 발급한다.

### Request
- **URI**: 'api/v1/queue/tokens'
- **Method**: POST
- **Body**:
```json
{
  "userId": 1
}
```
- `userId`: long (사용자 ID)

### Response
```json
{
  "token": "sadf1234",
  "status": "WAITING",
  "createdAt": "2024-12-30 12:00:00",
  "rank": 1
}
```
- **Response**:
    - `token`: String (토큰 UUID)
    - `status` Enum (대기 상태)
    - `createdAt`: DateTime (생성 시각)
    - `rank`: int (대기 순번)

### Error
- 요청 Body에 userId가 없거나 형식이 잘못된 경우.
```json
{
  "statusCode": 400,
  "message": "Missing or invalid userId."
}
```
- 해당 userId가 존재하지 않을 경우 (예: DB에 해당 사용자가 없는 경우).
```json
{
  "statusCode": 404,
  "message": "User not found."
}
```

---
## 2. 대기열 상태 조회

### Description
- 폴링 방식으로 사용자의 대기열 상태를 조회한다.

### Request
- **URI**: `api/v1/queue/status`
- **Method**: GET
- **Headers**:
    - `Token`: String (토큰 UUID)
    - `User-Id`: Long (사용자 ID)

```json
{
  "status": "ACTIVE",
  "rank": 0,
  "enteredAt": "2024-12-30 12:00:00",
  "expiredAt": "2024-12-30 24:00:00"
}
```

- **Response**:
    - `status`: Enum (`WAITING`: 대기 / `ACTIVE`: 활성화)
    - `rank`: int (대기 순번)
    - `enteredAt`: DateTime (활성화 시각)
    - `expiredAt`: DateTime (만료 시각)

### Error
- 헤더의 Token 값이 유효하지 않을 때.

```json
{
  "statusCode": 401,
  "message": "Invalid Token"
}
```

- 해당 userId가 존재하지 않을 경우 (예: DB에 해당 사용자가 없는 경우).
```json
{
  "statusCode": 404,
  "message": "User not found."
}
```


- 사용자가 대기열에 속해 있지 않거나 상태를 조회할 수 없는 경우 발생.
```json
{
  "statusCode": 400,
  "message": "Queue status cannot be retrieved for this user."
}
```

---

## 3. 콘서트 예약 가능 일정 조회

### Description
- 특정 콘서트의 예약 가능한 일정을 조회한다.

### Request
- **URI**: `api/v1/concerts/{concertId}/schedules`
- **Method**: GET
- **Path Variable**:
    - `concertId`: Long (콘서트 ID)
- **Headers**:
  - `Token`: String (토큰 UUID)
  - `User-Id`: Long (사용자 ID)

### Response

```json
{
  "concertId": 1,
  "schedules": [
    {
      "scheduleId": 1,
      "concertAt": "2024-12-30 12:00:00",
      "reservationAt": "2024-12-30 12:00:00",
      "deadline": "2024-12-30 12:00:00"
    },
    {
      "scheduleId": 2,
      "concertAt": "2024-12-30 12:00:00",
      "reservationAt": "2024-12-30 12:00:00",
      "deadline": "2024-12-30 12:00:00"
    }
  ]
}
```
- **Response**:
  - `concertId`: Long (콘서트 ID)
  - `schedules`: List (일정 목록)
    - `scheduleId`: Long (일정 ID)
    - `concertAt`: DateTime (콘서트 시각)
    - `reservationAt`: DateTime (예약 시각)
    - `deadline`: DateTime (예약 마감 시각)


### Error
- 헤더의 Token 값이 유효하지 않을 때.
```json
{
  "statusCode": 401,
  "message": "Invalid Token"
}
```

- 해당 userId가 존재하지 않을 경우 (예: DB에 해당 사용자가 없는 경우).
```json
{
  "statusCode": 404,
  "message": "User not found."
}
```

- 콘서트가 데이터베이스에 존재하지 않을 때
```json
{
  "statusCode": 404,
  "message": "Concert Not Found"
}
```

---
## 4. 콘서트 잔여 좌석 조회

### Description
- 특정 콘서트 일정의 잔여 좌석을 조회한다.

### Request

- **URI**: `api/v1/concerts/{concertId}/schedules/{scheduleId}/seats`
- **Method**: GET
- **Path Variable**:
  - `concertId`: Long (콘서트 ID)
  - `scheduleId`: Long (일정 ID)
- **Headers**:
  - `Token`: String (토큰 UUID)
  - `User-Id`: Long (사용자 ID)

### Response

```json
{
  "concertId": 1,
  "concertAt": "2024-12-30 12:00:00",
  "maxSeats": 50,
  "seats": [
    {
      "seatId": 1,
      "seatNumber": 1,
      "seatStatus": "AVAILABLE",
      "seatPrice": 10000
    },
    {
      "seatId": 2,
      "seatNumber": 2,
      "seatStatus": "AVAILABLE",
      "seatPrice": 10000
    }
  ]
}
```

- **Response**:
  - `concertId`: Long (콘서트 ID)
  - `concertAt`: DateTime (콘서트 시각)
  - `maxSeats`: Long (총 좌석 수)
  - `seats`: List (예약 가능 좌석 목록)
    - `seatId`: Long (좌석 ID)
    - `seatNumber`: Long (좌석 번호)
    - `seatStatus`: Enum (좌석 예약 가능 여부)
    - `seatPrice`: Long (좌석 가격)

### Error
- 헤더의 Token 값이 유효하지 않을 때.
```json
{
  "statusCode": 401,
  "message": "Invalid Token"
}
```

- 해당 userId가 존재하지 않을 경우 (예: DB에 해당 사용자가 없는 경우).
```json
{
  "statusCode": 404,
  "message": "User Not Found"
}
```

- 콘서트가 데이터베이스에 존재하지 않을 때
```json
{
  "statusCode": 404,
  "message": "Concert Not Found"
}
```

- 콘서트 일정이 존재하지 않을 때
```json
{
  "statusCode": 404,
  "message": "Schedule Not Found"
}
```

---

## 5. 좌석 예약

### Description
- 콘서트 좌석을 예약한다.

### Request

- **URI**: `api/v1/reservations`
- **Method**: POST
- **Headers**:
  - `Token`: String (토큰 UUID)
  - `User-Id`: Long (사용자 ID)
- **Body**:
```json
{
  "userId": 1,
  "concertId": 1,
  "scheduleId": 1,
  "seatId": 1
}
```
- `userId`: Long (사용자 ID)
- `concertId`: Long (콘서트 ID)
- `scheduleId`: Long (일정 ID)
- `seatId`: int (선택한 좌석 ID)

### Response
```json
{
  "reservationId": 1,
  "concertId": 1,
  "concertName": "콘서트",
  "concertAt": "2024-12-30 12:00:00",
  "seats": {
      "seatNumber": 1,
      "seatPrice": 10000
    },
  "totalPrice": 20000,
  "reservationStatus": "PAYMENT_WAITING"
}
```

- **Response**:
  - `reservationId`: Long (예약 ID)
  - `concertId`: Long (콘서트 ID)
  - `concertName`: String (콘서트 이름)
  - `concertAt`: DateTime (콘서트 시각)
  - `seats`: List (선택한 좌석 목록)
    - `seatNumber`: Long (좌석 번호)
    - `price`: Long (좌석 금액)
  - `totalPrice`: Long (결제 금액)
  - `reservationStatus`: Enum (`PAYMENT_WAITING`: 결제 대기 상태)

### Error

- 헤더의 Token 값이 유효하지 않을 때.
```json
{
  "statusCode": 401,
  "message": "Invalid Token"
}
```

- 해당 userId가 존재하지 않을 경우 (예: DB에 해당 사용자가 없는 경우).
```json
{
  "statusCode": 404,
  "message": "User Not Found"
}
```

- 콘서트가 데이터베이스에 존재하지 않을 때
```json
{
  "statusCode": 404,
  "message": "Concert Not Found"
}
```

- 콘서트 일정이 존재하지 않을 때
```json
{
  "statusCode": 404,
  "message": "Schedule Not Found"
}
```

- 이미 예약, 없는 좌석 등으로 좌석을 예약할 수 없을 때
```json
{
  "statusCode": 404,
  "message": "Seat Not Found"
}
```

---
## 6. 결제 요청

### Description
- 예약에 대한 결제를 진행한다.
- 예약을 5분내에 결제해야 한다.

### Request

- **URI**: `api/v1/payments`
- **Method**: POST
- **Headers**:
  - `Token`: String (토큰 UUID)
  - `User-Id`: Long (사용자 ID)

- **Body**:
```json
{
  "userId": 1,
  "reservationId": 1
}
```
- `userId`: Long (사용자 ID)
- `reservationId`: Long (예약 ID)

### Response
```json
{
  "paymentId": 1,
  "amount": 30000,
  "paymentStatus": "COMPLETED"
}
```
- **Response**
  - `paymentId`: Long (결제 ID)
  - `amount`: Long (결제 금액)
  - `paymentStatus`: Enum (`COMPLETED`: 완료)

### Error

- 헤더의 Token 값이 유효하지 않을 때.
```json
{
  "statusCode": 401,
  "message": "Invalid Token"
}
```

- 해당 userId가 존재하지 않을 경우 (예: DB에 해당 사용자가 없는 경우).
```json
{
  "statusCode": 404,
  "message": "User Not Found"
}
```

- 예약 정보가 존재 하지 않을 경우
```json
{
  "statusCode": 404,
  "message": "Reservation Not Found"
}
```

- 계좌 잔액이 부족할 경우
```json
{
  "statusCode": 400,
  "message": "Not Enough Balance"
}
```

- 알 수 없는 에러로 결제가 실패했을 경우
```json
{
  "statusCode": 500,
  "message": "Payment Failed"
}
```

---

## 7. 잔액 충전

### Description
- 사용자의 잔액을 충전합니다.

### Request

- **URI**: `api/v1/users/{userId}/point`
- **Method**: PATCH
- **Path Variable**:
  - `userId`: Long (사용자 ID)
- **Body**:
```json
{
  "amount": 50000
}
```
- `amount`: Long (충전 금액)

### Response
```json
{
  "userId": 1,
  "currentAmount": 50000
}
```

### Error
- 해당 userId가 존재하지 않을 경우 (예: DB에 해당 사용자가 없는 경우).
```json
{
  "statusCode": 404,
  "message": "User Not Found"
}
```

- Amount에 잘못된 값을 충전하려 시도할 경우
```json
{
  "statusCode": 400,
  "message": "Invalid Charge Amount"
}
```

- 알 수 없는 에러로 충전이 실패할 경우
```json
{
  "statusCode": 500,
  "message": "Charge Failed"
}
```

---

## 8. 잔액 조회

### Description
- 사용자의 현재 잔액을 조회한다.

### Request

- **URI**: `api/v1/users/{userId}/point`
- **Method**: GET
- **Path Variable**:
  - `userId`: Long (사용자 ID)

### Response
```json
{
  "userId": 1,
  "currentAmount": 50000
}
```
- **Response**
  - `userId`: Long (사용자 ID)
  - `currentAmount`: Long (잔액)

### Error

- 해당 userId가 존재하지 않을 경우 (예: DB에 해당 사용자가 없는 경우).
```json
{
  "statusCode": 404,
  "message": "User Not Found"
}
```