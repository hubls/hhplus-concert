package io.hhplus.concert.hhplusconcert.domain.service;

import io.hhplus.concert.hhplusconcert.domain.model.Point;
import io.hhplus.concert.hhplusconcert.domain.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    public Point getPoint(Long userId) {
        return pointRepository.findPoint(userId);
    }

    public Point chargePoint(Long userId, Long amount) {
        Point point = pointRepository.findPoint(userId);
        Point updatedPoint = point.charge(amount);
        pointRepository.save(updatedPoint);
        return updatedPoint;
    }
}
