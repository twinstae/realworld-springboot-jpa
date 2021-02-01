package study.realWorld.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
