package study.realWorld.domain;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import study.realWorld.ArticlesTestingUtil;
import study.realWorld.api.dto.ArticleCreateDto;
import study.realWorld.api.exception.ResourceNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticlesRepositoryTest extends ArticlesTestingUtil {
    @Test
    public void findOneBySlugTest() {
        createInit();

        Articles firstArticles = articlesRepository.findOneBySlug(articles.getSlug())
                .orElseThrow(ResourceNotFoundException::new);
        assertThat(firstArticles.getSlug()).isEqualTo(articles.getSlug());
    }

    @Test
    @Transactional
    public void updateRepoTest() {
        createInit();

        Articles articlesInDB = articlesRepository.findOneBySlug(this.articles.getSlug())
                .orElseThrow(ResourceNotFoundException::new);

        ArticleCreateDto updateDto = getUpdateArticleDto();
        articlesInDB.update(updateDto);

        assertThat(articlesInDB.getSlug()).isEqualTo(updateDto.getSlug());
    }
}