package study.realWorld.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.realWorld.api.dto.ArticleCreateUpdateDto;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Articles {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length=512, nullable = false, unique = true)
    private String slug;

    @Column(length=128, nullable = false)
    private String title;

    @Column(length=512, nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @Builder
    public Articles(String slug, String title, String description, String body){
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
    }

    public void update(ArticleCreateUpdateDto updateDto){
        if (!"".equals(slug)){
            this.slug = updateDto.getSlug();
        }
        if (!"".equals(title)) {
            this.title = updateDto.getTitle();
        }
        if (!"".equals(description)) {
            this.description = updateDto.getDescription();
        }
        if (!"".equals(body)) {
            this.body = updateDto.getBody();
        }
    }
}
