package com.josdem.web.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriesController {

  @GetMapping("/articles")
  public String[] getArticles() {
    return new String[] {"Article 1", "Article 2", "Article 3"};
  }
}
