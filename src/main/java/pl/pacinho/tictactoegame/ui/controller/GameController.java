package pl.pacinho.tictactoegame.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pacinho.tictactoegame.service.GameService;
import pl.pacinho.tictactoegame.ui.UIConfig;

@RequiredArgsConstructor
@Controller
public class GameController {

    private final GameService gameService;

    @GetMapping(UIConfig.HOME)
    public String gameHome(Model model) {
        return "home";
    }


    @PostMapping(UIConfig.GAMES)
    public String availableGames(Model model) {
        model.addAttribute("games", gameService.getAvailableGames());
        return "availableGames :: availableGamesFrag";
    }

    @PostMapping(UIConfig.PLAYER_MOVE)
    public String playerMove(@PathVariable(value = "gameId") String gameId, Model model) {
        model.addAttribute("game", gameService.findById(gameId));
        return "board :: boardFrag";
    }


    @PostMapping(UIConfig.NEW_GAME)
    public String newGame(Authentication authentication) {
        return "redirect:" + UIConfig.GAMES + "/" + gameService.newGame(authentication.getName());
    }

    @GetMapping(UIConfig.GAME_PAGE)
    public String gamePage(@PathVariable(value = "gameId") String gameId, Model model) {
        try {
            model.addAttribute("game", gameService.findById(gameId));
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return gameHome(model);
        }
        return "game";
    }

    @PostMapping(UIConfig.JOIN_TO_GAME)
    public String joinGame(@PathVariable(value = "gameId") String gameId, Authentication authentication, Model model) {
        try {
            if(gameService.checkOpenGame(gameId, authentication.getName()))
                return "redirect:" + UIConfig.GAMES + "/" +gameId;

            gameService.joinGame(authentication.getName(), gameId);
            model.addAttribute("gameId", gameId);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return gameHome(model);
        }
        return "join-game";
    }

}