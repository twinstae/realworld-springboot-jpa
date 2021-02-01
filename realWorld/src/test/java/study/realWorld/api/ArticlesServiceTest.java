package study.realWorld.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import study.realWorld.api.dto.ArticleDto;
import study.realWorld.service.ArticlesService;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticlesServiceTest extends ArticlesTestingUtil {
    @Autowired
    private ArticlesService articlesService;

    @Test
    public void findOneBySlugTest() {
        articleCreateDto = getCreateArticleDto(title);
        articles = createArticle(articleCreateDto);

        ArticleDto firstArticles = articlesService.findBySlug(articles.getSlug());

        assertThat(firstArticles.getSlug()).isEqualTo(articles.getSlug());
    }
}