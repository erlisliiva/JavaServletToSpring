package com.erlis.controller;

import com.erlis.model.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ValidationController {

    @PostMapping("validation")
    public void validation(@RequestBody @Valid Order order) {
    }
}