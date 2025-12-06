package com.example.lostfound.web;

import com.example.lostfound.repo.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ItemRepository repo;

    public HomeController(ItemRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/")
    public String home(Model model) {
        long lostCount = repo.findAll().stream()
                .filter(item -> "LOST".equalsIgnoreCase(item.getStatus()))
                .count();
        long foundCount = repo.findAll().stream()
                .filter(item -> "FOUND".equalsIgnoreCase(item.getStatus()))
                .count();

        model.addAttribute("lostCount", lostCount);
        model.addAttribute("foundCount", foundCount);
        model.addAttribute("totalCount", repo.count());

        return "home";
    }
}