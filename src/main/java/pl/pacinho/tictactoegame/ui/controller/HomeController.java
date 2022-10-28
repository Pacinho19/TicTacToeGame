package pl.pacinho.tictactoegame.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.pacinho.tictactoegame.ui.UIConfig;

@Controller
public class HomeController {

    @GetMapping
    public String home() {
        return "redirect:" + UIConfig.HOME;
    }
}
