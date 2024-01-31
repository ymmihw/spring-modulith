package com.ymmihw.events.externalization;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Getter
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String slug;
    private String title;
    private String author;
    private String content;
    public Article(String slug, String title, String author, String content) {
        this.slug = slug;
        this.title = title;
        this.author = author;
        this.content = content;
    }
}