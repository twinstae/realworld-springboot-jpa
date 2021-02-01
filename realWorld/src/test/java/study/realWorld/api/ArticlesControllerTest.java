package study.realWorld.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import study.realWorld.api.dto.ArticleCreateDto;
import study.realWorld.api.dto.ArticleListDto;
import study.realWorld.api.dto.ArticleDto;
import study.realWorld.api.dto.ArticleResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticlesControllerTest extends ArticlesTestingUtil {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void articleDtoTest(){
        ArticleDto dto = ArticleDto
                .builder()
                .title(title)
                .description(description)
                .body(body)
                .build();
        checkDtoEqualToDefault(dto);
    }

    private void checkDtoEqualToDefault(ArticleDto dto) {
        assertThat(dto.getTitle()).isEqualTo(title);
        assertThat(dto.getDescription()).isEqualTo(description);
        assertThat(dto.getBody()).isEqualTo(body);
    }

    private void assertDtoIsEqualTo(ArticleDto dto, ArticleCreateDto expected) {
        assertThat(dto.getSlug()).isEqualTo(expected.getSlug());
        assertThat(dto.getTitle()).isEqualTo(expected.getTitle());
        assertThat(dto.getDescription()).isEqualTo(expected.getDescription());
        assertThat(dto.getBody()).isEqualTo(expected.getBody());
    }

    @Test
    public void getArticleListTest(){
        articleCreateDto = getCreateArticleDto(title);
        articles = createArticle(articleCreateDto);

        ArticleCreateDto articleCreateDto2 = getCreateArticleDto(title + "2");
        articles = createArticle(articleCreateDto2);

        ResponseEntity<ArticleListDto> responseEntity = restTemplate.getForEntity(
                baseUrl(), ArticleListDto.class
        );

        assertStatus(responseEntity, HttpStatus.OK);

        ArticleListDto responseBody = responseEntity.getBody();

        assertThat(responseBody.getArticlesCount()).isEqualTo(2);

        ArticleDto first = responseBody.getArticles().get(0);
        assertDtoIsEqualTo(first, articleCreateDto);

        ArticleDto second = responseBody.getArticles().get(1);
        assertDtoIsEqualTo(second, articleCreateDto2);
    }

    private String baseUrl() {
        return "http://localhost:" + port + "/api/articles";
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

        ArticleResponseDto responseBody = responseEntity.getBody();
        assert responseBody != null;
        assertDtoIsEqualTo(responseBody.getArticle(), articleCreateDto);
    }

    @Test
    public void getArticleBySlugTest(){
        articleCreateDto = getCreateArticleDto(title);
        articles = createArticle(articleCreateDto);

        String url = baseUrl() + "/" + articles.getSlug();

        ResponseEntity<ArticleResponseDto> responseEntity = restTemplate.getForEntity(
                url, ArticleResponseDto.class
        );

        assertStatus(responseEntity, HttpStatus.OK);

        ArticleResponseDto responseBody = responseEntity.getBody();
        assert responseBody != null;
        assertDtoIsEqualTo(responseBody.getArticle(), articleCreateDto);
    }
}