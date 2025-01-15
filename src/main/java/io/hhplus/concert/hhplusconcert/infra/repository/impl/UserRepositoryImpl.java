package io.hhplus.concert.hhplusconcert.infra.repository.impl;

import io.hhplus.concert.hhplusconcert.domain.repository.UserRepository;
import io.hhplus.concert.hhplusconcert.infra.entity.UserEntity;
import io.hhplus.concert.hhplusconcert.infra.repository.jpa.UserJpaRepository;
import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public void existsUser(Long userId) {
        if (!userJpaRepository.existsById(userId)) {
            throw new CoreException(ErrorType.USER_NOT_FOUND, "userId: " + userId);
        }
    }

    @Override
    public void save(String userName) {
        userJpaRepository.save(UserEntity.builder()
                .name(userName)
                .build());
    }
}
