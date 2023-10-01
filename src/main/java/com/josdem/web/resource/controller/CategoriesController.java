package com.josdem.web.resource.controller;

import com.josdem.web.resource.model.Category;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoriesController {

  private final List<Category> categories = new ArrayList<>();

  @PostConstruct
  void init() {
    categories.add(new Category(5, "Healing"));
    categories.add(new Category(6, "Energy"));
    categories.add(new Category(7, "Healthy"));
    categories.add(new Category(8, "Boost"));
  }

  @GetMapping("/categories")
  public ModelAndView getCategories() {
    ModelAndView modelAndView = new ModelAndView("home.html");
    modelAndView.addObject("categories", categories);
    return modelAndView;
  }
}
