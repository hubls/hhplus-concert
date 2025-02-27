import http from 'k6/http';
import { check, sleep } from 'k6';

// 부하 조건 설정
export let options = {
  stages: [
    { duration: '20s', target: 200 }, // 20초 동안 유저 수를 200명으로 증가
    { duration: '20s', target: 200 },
    { duration: '20s', target: 200 }
  ],
  thresholds: {
    http_req_duration: ['p(95)<500'], // 95%의 요청이 500ms 미만이어야 함
    http_req_failed: ['rate<0.01'] // 실패율은 1% 미만
  }
};

export default function () {
  let token = '21d6207f-b322-3a29-b4f2-ed4d3442ad5f';

  let url = 'http://localhost:8080/api/v1/queue/status';
  let params = {
    headers: {
      'Content-Type': 'application/json',
      'Token': token,
      'User-Id': 1
    }
  };

  // HTTP GET 요청
  let res = http.get(url, params);

  // 응답 검증
  check(res, {
    'status is 200': (r) => r.status === 200,
    'response time < 500ms': (r) => r.timings.duration < 500,
  });

  // 요청 간격
  sleep(0.5)
}