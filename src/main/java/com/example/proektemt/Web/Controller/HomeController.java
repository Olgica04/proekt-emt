package com.example.proektemt.Web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping
public class HomeController {

    @GetMapping
    public String index()
    {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getHomePage(HttpServletResponse res, HttpServletRequest req)
    {
        return "home";
    }
}
