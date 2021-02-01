package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.realWorld.api.dto.ArticleCreateDto;
import study.realWorld.api.dto.ArticleDto;
import study.realWorld.domain.Articles;
import study.realWorld.domain.ArticlesRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticlesService {
    private final ArticlesRepository articlesRepository;

    @Transactional
    public ArticleDto save(ArticleCreateDto articleCreateDto){
        Articles articles = articlesRepository.save(articleCreateDto.toEntity());
        return ArticleDto.fromArticles(articles);
    }

    public List<ArticleDto> getFilteredArticles(){
        List<Articles> articles = articlesRepository.findAll();
        return articles.stream()
                .map(ArticleDto::fromArticles)
                .collect(Collectors.toList());
    }

    public ArticleDto findBySlug(String slug){
        Articles articles = articlesRepository.findOneBySlug(slug);
        return ArticleDto.fromArticles(articles);
    }
}
