package com.skeleton.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BoardController {
    @GetMapping("/board")
    public String board(Model model) {

        model.addAttribute("list", "list");

        return "/board";
    }
}
