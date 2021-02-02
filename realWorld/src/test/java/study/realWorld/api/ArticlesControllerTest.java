package study.realWorld.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import study.realWorld.api.dto.ArticleCreateDto;
import study.realWorld.api.dto.ArticleListResponseDto;
import study.realWorld.api.dto.ArticleDto;
import study.realWorld.api.dto.ArticleResponseDto;
import study.realWorld.ArticlesTestingUtil;
import study.realWorld.domain.Articles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticlesControllerTest extends ArticlesTestingUtil {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getArticleListTest(){
        createInit();
        ArticleCreateDto articleCreateDto2 = getCreateArticleDto(title + "2");
        createArticle(articleCreateDto2);

        ResponseEntity<ArticleListResponseDto> responseEntity = restTemplate.getForEntity(
                baseUrl(), ArticleListResponseDto.class
        );

        assertStatus(responseEntity, HttpStatus.OK);

        ArticleListResponseDto responseBody = responseEntity.getBody();
        assert responseBody != null;

        assertThat(responseBody.getArticlesCount()).isEqualTo(2);

        ArticleDto first = responseBody.getArticles().get(0);
        assertDtoIsEqualTo(first, articleCreateDto);

        ArticleDto second = responseBody.getArticles().get(1);
        assertDtoIsEqualTo(second, articleCreateDto2);
    }


    private String baseUrl() {
        return "http://localhost:" + port + "/api/articles";
    }

    private String slugUrl(){
        return baseUrl() + "/" + articles.getSlug();
    }

    private void assertStatus(ResponseEntity responseEntity, HttpStatus expectedStatus){
        assertThat(responseEntity.getStatusCode())
                .isEqualTo(expectedStatus);
    }

    @Test
    public void createArticleTest(){
        articleCreateDto = getCreateArticleDto(title);

        ResponseEntity<ArticleResponseDto> responseEntity = restTemplate.postForEntity(
                baseUrl(), articleCreateDto, ArticleResponseDto.class
        );

        assertStatus(responseEntity, HttpStatus.CREATED);

        assertBodyIsEqualToDto(responseEntity, articleCreateDto);
    }

    @Test
    public void getArticleBySlugTest(){
        createInit();

        ResponseEntity<ArticleResponseDto> responseEntity = restTemplate.getForEntity(
                slugUrl(), ArticleResponseDto.class
        );

        assertStatus(responseEntity, HttpStatus.OK);

        assertBodyIsEqualToDto(responseEntity, articleCreateDto);
    }

    protected void assertBodyIsEqualToDto(
            ResponseEntity<ArticleResponseDto> responseEntity,
            ArticleCreateDto createUpdateDto
    ) {
        ArticleResponseDto responseBody = responseEntity.getBody();
        assert responseBody != null;
        assertDtoIsEqualTo(responseBody.getArticle(), createUpdateDto);
    }

    @Test
    public void getArticleBySlug_No_Slug(){
        String url = baseUrl() + "/" + "존재하지않는슬러그";

        ResponseEntity<ArticleResponseDto> responseEntity = restTemplate.getForEntity(
                url, ArticleResponseDto.class
        );

        assertStatus(responseEntity, HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateControllerTest(){
        createInit();
        articleUpdateDto = getUpdateArticleDto();

        HttpEntity<ArticleCreateDto> requestUpdate = new HttpEntity<>(
                articleUpdateDto, new HttpHeaders()
        );

        ResponseEntity<ArticleResponseDto> responseEntity = restTemplate.exchange(
                slugUrl(), HttpMethod.PUT, requestUpdate, ArticleResponseDto.class
        );

        assertStatus(responseEntity, HttpStatus.OK);
        assertBodyIsEqualToDto(responseEntity, articleUpdateDto);
    }

    @Test
    public void deleteControllerTest(){
        createInit();
        restTemplate.delete(slugUrl());

        Optional<Articles> result = articlesRepository.findOneBySlug(articleCreateDto.getSlug());
        assertThat(result).isEmpty();
    }
}