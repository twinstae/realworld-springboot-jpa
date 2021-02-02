package study.realWorld;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.realWorld.api.dto.ArticleCreateUpdateDto;
import study.realWorld.api.dto.ArticleDto;
import study.realWorld.domain.Articles;
import study.realWorld.domain.ArticlesRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticlesTestingUtil {

    protected String title = "제목";
    protected String description = "개요";
    protected String body = "내용";
    protected Articles articles;
    protected ArticleCreateUpdateDto articleCreateUpdateDto;
    protected ArticleCreateUpdateDto articleUpdateDto;

    @Autowired
    protected
    ArticlesRepository articlesRepository;

    @AfterEach
    public void tearDown() throws Exception {
        articlesRepository.deleteAll();
    }

    public ArticleCreateUpdateDto getUpdateArticleDto() {
        return ArticleCreateUpdateDto
                .builder()
                .title("제목수정")
                .description("개요수정")
                .body("내용수정")
                .build();
    }

    public void assertDtoIsEqualTo(ArticleDto dto, ArticleCreateUpdateDto expected) {
        assertThat(dto.getSlug()).isEqualTo(expected.getSlug());
        assertThat(dto.getTitle()).isEqualTo(expected.getTitle());
        assertThat(dto.getDescription()).isEqualTo(expected.getDescription());
        assertThat(dto.getBody()).isEqualTo(expected.getBody());
    }

    public ArticleCreateUpdateDto getCreateArticleDto(String testTitle) {
        return ArticleCreateUpdateDto
                .builder()
                .title(testTitle)
                .description(description)
                .body(body)
                .build();
    }

    public Articles createArticle(ArticleCreateUpdateDto articleCreateUpdateDto){
        return articlesRepository.save(articleCreateUpdateDto.toEntity());
    }

    public void createInit() {
        articleCreateUpdateDto = getCreateArticleDto(title);
        articles = createArticle(articleCreateUpdateDto);
    }
}