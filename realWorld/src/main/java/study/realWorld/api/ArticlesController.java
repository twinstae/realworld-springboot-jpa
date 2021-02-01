package study.realWorld.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.realWorld.api.dto.ArticleCreateDto;
import study.realWorld.api.dto.ArticleListDto;
import study.realWorld.api.dto.ArticleDto;
import study.realWorld.api.dto.ArticleResponseDto;
import study.realWorld.service.ArticlesService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/articles")
public class ArticlesController {

    private final ArticlesService articlesService;

    @GetMapping
    public ResponseEntity<ArticleListDto> getArticles(){
        List<ArticleDto> data = articlesService.getFilteredArticles();
        return ResponseEntity.ok(new ArticleListDto(data));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ArticleResponseDto> findArticleBySlug(
            @PathVariable String slug
    ){
        System.out.println(slug);
        ArticleDto article = articlesService.findBySlug(slug);
        return ResponseEntity.ok(new ArticleResponseDto(article));
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(
            @RequestBody ArticleCreateDto articleRequest
    ){
        ArticleDto article = articlesService.save(articleRequest);
        return new ResponseEntity<>(
                new ArticleResponseDto(article),
                new HttpHeaders(),
                HttpStatus.CREATED);
    }
}