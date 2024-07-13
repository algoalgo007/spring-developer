package com.example.blogProject.controller;

import com.example.blogProject.domain.Article;
import com.example.blogProject.dto.AddArticleDto;
import com.example.blogProject.dto.ArticleResponseDto;
import com.example.blogProject.dto.UpdateArticleDto;
import com.example.blogProject.service.BlogService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Result<ArticleResponseDto>> addArticle(@RequestBody AddArticleDto addArticleDto, Principal principal) {
        Article savedArticle = blogService.save(addArticleDto, principal.getName());
        ArticleResponseDto dto = ArticleResponseDto.toDto(savedArticle);
        Result<ArticleResponseDto> result = new Result<>(HttpStatus.CREATED.value(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<Result<List<ArticleResponseDto>>> getAllArticles() {
        List<ArticleResponseDto> articles = blogService.findAll().stream()
                .map(ArticleResponseDto::toDto)
                .toList();
        Result<List<ArticleResponseDto>> result = new Result<>(HttpStatus.OK.value(), articles);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<Result<ArticleResponseDto>> getArticleById(@PathVariable Long id) {
        Article findArticle = blogService.findById(id);
        ArticleResponseDto dto = ArticleResponseDto.toDto(findArticle);
        Result<ArticleResponseDto> result = new Result<>(HttpStatus.OK.value(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Result<String>> deleteArticleById(@PathVariable Long id) {
        blogService.delete(id);
        Result<String> result = new Result<>(HttpStatus.OK.value(), "Delete success");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Result<ArticleResponseDto>> updateArticleById(
            @PathVariable Long id,
            @RequestBody UpdateArticleDto updateArticleDto) {
        Article updateArticle = blogService.update(id, updateArticleDto);
        Result<ArticleResponseDto> result = new Result<>(HttpStatus.OK.value(), ArticleResponseDto.toDto(updateArticle));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Setter
    @Getter
    public static class Result<T> {
        private int status;
        private T data;

        public Result(int status, T data) {
            this.status = status;
            this.data = data;
        }
    }
}
