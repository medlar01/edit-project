package com.edit.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/edit")
public class EditController {

    @GetMapping("/hello")
    public String hello() {
        return "OK";
    }
}
