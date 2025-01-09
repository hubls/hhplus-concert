package io.hhplus.concert.hhplusconcert.domain.repository;

import io.hhplus.concert.hhplusconcert.domain.model.Point;

public interface PointRepository {
    Point findPoint(Long userId);
    void save(Point updatedPoint);
}
