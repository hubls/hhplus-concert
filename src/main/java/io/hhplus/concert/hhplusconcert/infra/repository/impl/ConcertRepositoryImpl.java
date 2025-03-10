package io.hhplus.concert.hhplusconcert.infra.repository.impl;

import io.hhplus.concert.hhplusconcert.domain.model.Concert;
import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.domain.repository.ConcertRepository;
import io.hhplus.concert.hhplusconcert.infra.entity.ConcertEntity;
import io.hhplus.concert.hhplusconcert.infra.entity.ConcertScheduleEntity;
import io.hhplus.concert.hhplusconcert.infra.entity.SeatEntity;
import io.hhplus.concert.hhplusconcert.infra.repository.jpa.ConcertJpaRepository;
import io.hhplus.concert.hhplusconcert.infra.repository.jpa.ConcertScheduleJpaRepository;
import io.hhplus.concert.hhplusconcert.infra.repository.jpa.SeatJpaRepository;
import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import io.hhplus.concert.hhplusconcert.support.type.SeatStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {
    private final ConcertJpaRepository concertJpaRepository;
    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;
    private final SeatJpaRepository seatJpaRepository;

    @Override
    public List<Concert> findConcerts() {
        return concertJpaRepository.findAll().stream()
                .map(ConcertEntity::of)
                .collect(Collectors.toList());
    }

    @Override
    public Concert findConcert(Long concertId) {
        return concertJpaRepository.findById(concertId)
                .map(ConcertEntity::of)
                .orElseThrow(() -> new CoreException(ErrorType.RESOURCE_NOT_FOUND, "검색한 콘서트 ID: " + concertId));
    }

    @Override
    public List<ConcertSchedule> findConcertSchedules(Long concertId) {
        return concertScheduleJpaRepository.findByConcertId(concertId).stream()
                .map(ConcertScheduleEntity::of)
                .collect(Collectors.toList());
    }

    @Override
    public ConcertSchedule findConcertSchedule(Long concertScheduleId) {
        return concertScheduleJpaRepository.findById(concertScheduleId)
                .map(ConcertScheduleEntity::of)
                .orElseThrow(() -> new CoreException(ErrorType.RESOURCE_NOT_FOUND, "검색한 일정 ID: " + concertScheduleId));
    }

    @Override
    public List<Seat> findAvailableSeats(Long concertScheduleId) {
        return seatJpaRepository.findAllByConcertScheduleIdAndStatus(concertScheduleId, SeatStatus.AVAILABLE).stream()
                .map(SeatEntity::of)
                .toList();
    }

    @Override
    public Seat findSeatById(Long seatId) {
        return seatJpaRepository.findById(seatId)
                .map(SeatEntity::of)
                .orElseThrow(() -> new CoreException(ErrorType.RESOURCE_NOT_FOUND, "검색한 좌석 ID: " + seatId));
    }

    @Override
    public void saveSeat(Seat seat) {
        seatJpaRepository.save(SeatEntity.from(seat));
    }

    @Override
    public void saveConcert(Concert concert) {
        concertJpaRepository.save(ConcertEntity.builder()
                .title(concert.title())
                .description(concert.description())
                .status(concert.status())
                .build());
    }

    @Override
    public void saveConcertSchedule(ConcertSchedule concertSchedule) {
        concertScheduleJpaRepository.save(ConcertScheduleEntity.builder()
                .concertId(concertSchedule.concertId())
                .reservationAt(concertSchedule.reservationAt())
                .deadline(concertSchedule.deadline())
                .concertAt(concertSchedule.concertAt())
                .build());
    }
}
