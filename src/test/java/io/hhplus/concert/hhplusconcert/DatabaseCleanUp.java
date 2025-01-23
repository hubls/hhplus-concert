package io.hhplus.concert.hhplusconcert;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Profile("test")
public class DatabaseCleanUp {

    @PersistenceContext
    private EntityManager entityManager;

    private final List<String> tables = new ArrayList<>();

    @PostConstruct
    public void afterPropertiesSet() {
        tables.addAll(
                entityManager.getMetamodel().getEntities().stream()
                        .filter(entity -> entity.getJavaType().isAnnotationPresent(Entity.class))
                        .map(entity -> {
                            // @Entity(name = "concert") 에서 name을 가져와 테이블명으로 사용
                            Entity entityAnnotation = entity.getJavaType().getAnnotation(Entity.class);
                            String tableName = (entityAnnotation != null) ? entityAnnotation.name() : entity.getJavaType().getSimpleName();
                            return tableName;
                        })
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        // MySQL에서 외래 키 제약 비활성화
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        for (String table : tables) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + table).executeUpdate();
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        }
    }

    @Transactional
    public void execute(String table) {
        entityManager.flush();
        // MySQL에서 외래 키 제약 비활성화
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE " + table).executeUpdate();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }
}