package study.realWorld.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticlesRepository extends JpaRepository<Articles, Long> {
    Articles findOneBySlug(String slug);
}
