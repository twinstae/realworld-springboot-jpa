package study.realWorld.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.realWorld.api.dto.ArticleCreateUpdateDto;
import study.realWorld.api.dto.ArticleListResponseDto;
import study.realWorld.api.dto.ArticleDto;
import study.realWorld.api.dto.ArticleResponseDto;
import study.realWorld.service.ArticlesService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/articles")
public class ArticlesController {

    private final ArticlesService articlesService;

    @GetMapping
    public ResponseEntity<ArticleListResponseDto> getArticles(){
        List<ArticleDto> data = articlesService.getFilteredArticles();
        return ResponseEntity.ok(new ArticleListResponseDto(data));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ArticleResponseDto> findArticleBySlug(
            @PathVariable String slug
    ){
        ArticleDto article = articlesService.findBySlug(slug);
        return ResponseEntity.ok(new ArticleResponseDto(article));
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(
            @RequestBody ArticleCreateUpdateDto createBody
    ){
        ArticleDto article = articlesService.save(createBody);
        return new ResponseEntity<>(
                new ArticleResponseDto(article),
                new HttpHeaders(),
                HttpStatus.CREATED);
    }

    @PutMapping("/{slug}")
    public ResponseEntity<ArticleResponseDto> updateArticle(
            @PathVariable String slug, @RequestBody ArticleCreateUpdateDto updateBody
    ){
        ArticleDto article = articlesService.updateBySlug(slug, updateBody);
        return ResponseEntity.ok(new ArticleResponseDto(article));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<?> deleteArticle(@PathVariable String slug){
        articlesService.deleteBySlug(slug);
        return ResponseEntity.noContent().build();
    }
}