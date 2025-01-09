INSERT INTO users (name) VALUES ('123');

INSERT INTO concert (title, description, status) VALUES
('Concert 1', 'Description of Concert 1', 'AVAILABLE'),
('Concert 2', 'Description of Concert 2', 'AVAILABLE'),
('Concert 3', 'Description of Concert 3', 'AVAILABLE'),
('Concert 4', 'Description of Concert 4', 'UNAVAILABLE'),
('Concert 5', 'Description of Concert 5', 'UNAVAILABLE');

INSERT INTO concert_schedule (concert_id, reservation_at, deadline, concert_at)
VALUES (1, '2025-02-01 12:00:00', '2025-02-28 23:59:59', '2025-03-01 18:00:00'),
       (2, '2025-01-01 12:00:00', '2025-01-02 12:00:00', '2025-02-01 12:00:00'),
       (3, '2025-01-01 12:00:00', '2099-01-01 12:00:00', '2099-02-01 12:00:00');


---- 1번 콘서트 스케줄 ----
---- 예약 가능한 좌석 (1~25)
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 1, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 2, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 3, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 4, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 5, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 6, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 7, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 8, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 9, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 10, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 11, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 12, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 13, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 14, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 15, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 16, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 17, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 18, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 19, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 20, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 21, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 22, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 23, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 24, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 25, 'AVAILABLE', 10000);

-- 예약 불가능한 좌석 (26~50)
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 26, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 27, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 28, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 29, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 30, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 31, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 32, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 33, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 34, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 35, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 36, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 37, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 38, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 39, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 40, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 41, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 42, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 43, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 44, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 45, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 46, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 47, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 48, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 49, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (1, 50, 'UNAVAILABLE', 10000);

---------------------------------------------------------------

---- 2번 콘서트 스케줄 ----
---- 예약 가능한 좌석 (1~25)
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 1, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 2, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 3, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 4, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 5, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 6, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 7, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 8, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 9, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 10, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 11, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 12, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 13, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 14, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 15, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 16, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 17, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 18, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 19, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 20, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 21, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 22, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 23, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 24, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 25, 'AVAILABLE', 10000);

-- 예약 불가능한 좌석 (26~50)
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 26, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 27, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 28, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 29, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 30, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 31, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 32, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 33, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 34, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 35, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 36, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 37, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 38, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 39, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 40, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 41, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 42, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 43, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 44, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 45, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 46, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 47, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 48, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 49, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (2, 50, 'UNAVAILABLE', 10000);

---------------------------------------------------------------



---- 3번 콘서트 스케줄 ----
-- 예약 가능한 좌석 (1~25)
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 1, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 2, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 3, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 4, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 5, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 6, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 7, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 8, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 9, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 10, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 11, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 12, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 13, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 14, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 15, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 16, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 17, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 18, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 19, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 20, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 21, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 22, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 23, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 24, 'AVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 25, 'AVAILABLE', 10000);

-- 예약 불가능한 좌석 (26~50)
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 26, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 27, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 28, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 29, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 30, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 31, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 32, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 33, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 34, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 35, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 36, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 37, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 38, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 39, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 40, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 41, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 42, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 43, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 44, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 45, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 46, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 47, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 48, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 49, 'UNAVAILABLE', 10000);
INSERT INTO concert_seat (concert_schedule_id, seat_no, status, seat_price) VALUES (3, 50, 'UNAVAILABLE', 10000);

---------------------------------------------------------------