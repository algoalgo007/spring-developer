package com.example.blogProject.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateArticleDto {

    private String title;
    private String content;

    @Builder
    public UpdateArticleDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
