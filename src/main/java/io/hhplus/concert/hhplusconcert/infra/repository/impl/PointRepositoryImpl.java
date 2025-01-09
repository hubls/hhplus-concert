package io.hhplus.concert.hhplusconcert.infra.repository.impl;

import io.hhplus.concert.hhplusconcert.domain.model.Point;
import io.hhplus.concert.hhplusconcert.domain.repository.PointRepository;
import io.hhplus.concert.hhplusconcert.infra.entity.PointEntity;
import io.hhplus.concert.hhplusconcert.infra.repository.jpa.PointJpaRepository;
import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {
    private final PointJpaRepository pointJpaRepository;

    @Override
    public Point findPoint(Long userId) {
        return pointJpaRepository.findByUserId(userId)
                .map(PointEntity::of)
                .orElseThrow(() -> new CoreException(ErrorType.RESOURCE_NOT_FOUND, "userId: " + userId));
    }

    @Override
    public void save(Point updatedPoint) {
        pointJpaRepository.save(PointEntity.from(updatedPoint));
    }
}
