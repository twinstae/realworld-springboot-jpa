package study.realWorld.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ArticleListResponseDto {
    private final List<ArticleDto> articles;
    private final int articlesCount;

    public ArticleListResponseDto(List<ArticleDto> articleDataList) {
        this.articles = articleDataList;
        this.articlesCount = articleDataList.size();
    }
}
