package ru.practicum.main_service.category.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/categories")
@Validated
public class PublicCategoryController {

//    @GetMapping
//    public ResponseEntity<Object> getCategories(@RequestParam(name = "from", defaultValue = "0") Integer from,
//                                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
//
//    }
//
//    @GetMapping("/{catId}")
//    public ResponseEntity<Object> getCategory(@PathVariable Long catId) {
//
//    }
}
