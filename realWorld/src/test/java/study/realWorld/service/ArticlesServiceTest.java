package study.realWorld.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import study.realWorld.api.dto.ArticleCreateUpdateDto;
import study.realWorld.api.dto.ArticleDto;
import study.realWorld.ArticlesTestingUtil;
import study.realWorld.domain.Articles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticlesServiceTest extends ArticlesTestingUtil {
    @Autowired
    private ArticlesService articlesService;

    @Test
    public void findOneBySlugTest() {
        createInit();

        ArticleDto firstArticles = articlesService.findBySlug(articles.getSlug());

        assertThat(firstArticles.getSlug()).isEqualTo(articles.getSlug());
    }

    @Test
    public void updateServiceTest(){
        createInit();

        ArticleCreateUpdateDto updateDto = getUpdateArticleDto();
        articlesService.updateBySlug(articleCreateUpdateDto.getSlug(), updateDto);

        ArticleDto dto = articlesService.findBySlug(updateDto.getSlug());

        assertDtoIsEqualTo(dto, updateDto);
    }

    @Test
    public void deleteServiceTest(){
        createInit();
        String slug = articles.getSlug();
        articlesService.deleteBySlug(slug);
        Optional<Articles> result = articlesRepository.findOneBySlug(slug);
        assertThat(result).isEmpty();
    }

}