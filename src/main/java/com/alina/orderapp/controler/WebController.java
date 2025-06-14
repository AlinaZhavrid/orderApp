package com.alina.orderapp.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index"; // Вернёт шаблон src/main/resources/templates/index.html
    }
}
