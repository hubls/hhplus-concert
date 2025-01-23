package io.hhplus.concert.hhplusconcert.application.facade;

import io.hhplus.concert.hhplusconcert.domain.model.Point;
import io.hhplus.concert.hhplusconcert.domain.service.PointService;
import io.hhplus.concert.hhplusconcert.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PointFacade {
    private final UserService userService;
    private final PointService pointService;

    @Transactional(readOnly = true)
    public Point getPoint(Long userId) {
        userService.validateUser(userId);
        return pointService.getPoint(userId);
    }

    @Transactional
    public Point chargePoint(Long userId, Long amount) {
        userService.validateUser(userId);
        return pointService.chargePoint(userId, amount);
    }
}
