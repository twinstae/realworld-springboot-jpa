package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.realWorld.api.dto.ArticleCreateUpdateDto;
import study.realWorld.api.dto.ArticleDto;
import study.realWorld.api.exception.ResourceNotFoundException;
import study.realWorld.domain.Articles;
import study.realWorld.domain.ArticlesRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticlesService {
    private final ArticlesRepository articlesRepository;

    @Transactional
    public ArticleDto save(ArticleCreateUpdateDto articleCreateUpdateDto){
        Articles articles = articlesRepository.save(articleCreateUpdateDto.toEntity());
        return ArticleDto.fromArticles(articles);
    }

    public List<ArticleDto> getFilteredArticles(){
        List<Articles> articles = articlesRepository.findAll();
        return articles.stream()
                .map(ArticleDto::fromArticles)
                .collect(Collectors.toList());
    }

    public ArticleDto findBySlug(String slug){
        Articles articles = getArticleBySlugOr404(slug);
        return ArticleDto.fromArticles(articles);
    }

    protected Articles getArticleBySlugOr404(String slug) {
        return articlesRepository.findOneBySlug(slug)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public ArticleDto updateBySlug(String slug, ArticleCreateUpdateDto updateDto){
        Articles articles = getArticleBySlugOr404(slug);
        articles.update(updateDto);
        return ArticleDto.fromArticles(articles);
    }

    @Transactional
    public void deleteBySlug(String slug){
        Articles articles = getArticleBySlugOr404(slug);
        articlesRepository.delete(articles);
    }
}
