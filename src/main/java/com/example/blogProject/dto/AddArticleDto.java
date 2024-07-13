package com.example.blogProject.dto;

import com.example.blogProject.domain.Article;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AddArticleDto {

    private String title;
    private String content;

    @Builder
    public AddArticleDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Article toEntity(String author) {
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
