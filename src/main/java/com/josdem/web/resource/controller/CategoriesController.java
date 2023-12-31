package com.josdem.web.resource.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriesController {

  @GetMapping("/categories")
  public List<String> getCategories() {
    return List.of("Healing", "Energy", "Healthy", "Boost");
  }
}
