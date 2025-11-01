package com.example.lostfound.web;

import com.example.lostfound.model.Item;
import com.example.lostfound.repo.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository repo;

    public ItemController(ItemRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", repo.findAll());
        return "items";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("item", new Item());
        return "form";
    }

    @PostMapping
    public String create(@ModelAttribute Item item) {
        if (item.getStatus() == null || item.getStatus().isBlank()) item.setStatus("LOST");
        repo.save(item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("item", repo.findById(id).orElseThrow());
        return "form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Item updated) {
        Item item = repo.findById(id).orElseThrow();
        item.setTitle(updated.getTitle());
        item.setDescription(updated.getDescription());
        item.setContact(updated.getContact());
        item.setStatus(updated.getStatus());
        item.setLocation(updated.getLocation());
        repo.save(item);
        return "redirect:/items";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/items";
    }
}
