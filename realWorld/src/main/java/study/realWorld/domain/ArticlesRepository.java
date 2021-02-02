package study.realWorld.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticlesRepository extends JpaRepository<Articles, Long> {
    Optional<Articles> findOneBySlug(String slug);
    void deleteBySlug(String slug);
}
