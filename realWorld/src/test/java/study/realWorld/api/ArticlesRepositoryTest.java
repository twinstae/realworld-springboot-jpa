package study.realWorld.api;
import org.junit.jupiter.api.Test;
import study.realWorld.domain.Articles;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticlesRepositoryTest extends ArticlesTestingUtil {
    @Test
    public void findOneBySlugTest() {
        articleCreateDto = getCreateArticleDto(title);
        articles = createArticle(articleCreateDto);

        Articles firstArticles = articlesRepository.findOneBySlug(articles.getSlug());
        assertThat(firstArticles.getSlug()).isEqualTo(articles.getSlug());
    }
}