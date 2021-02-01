package study.realWorld.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import study.realWorld.api.dto.ArticleCreateDto;
import study.realWorld.domain.Articles;
import study.realWorld.domain.ArticlesRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticlesTestingUtil {

    String title = "제목";
    String description = "개요";
    String body = "내용";
    Articles articles;
    ArticleCreateDto articleCreateDto;

    @Autowired
    ArticlesRepository articlesRepository;

    @AfterEach
    public void tearDown() throws Exception {
        articlesRepository.deleteAll();
    }

    public ArticleCreateDto getCreateArticleDto(String testTitle) {
        return ArticleCreateDto
                .builder()
                .title(testTitle)
                .description(description)
                .body(body)
                .build();
    }

    public Articles createArticle(ArticleCreateDto articleCreateDto){
        return articlesRepository.save(articleCreateDto.toEntity());
    }
}