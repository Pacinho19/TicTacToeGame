package pl.pacinho.tictactoegame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import pl.pacinho.tictactoegame.model.MoveDto;
import pl.pacinho.tictactoegame.service.GameService;

@Controller
public class GameMoveController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private GameService gameService;

    @MessageMapping("/move")
    public void move(@Payload MoveDto moveDto, Authentication authentication) {
        gameService.playerMove(authentication.getName(), moveDto);
        simpMessagingTemplate.convertAndSend("/game/" + moveDto.getGameId(),authentication.getName());
    }

    @MessageMapping("/join")
    public void join(@Payload MoveDto moveDto, Authentication authentication) {
        simpMessagingTemplate.convertAndSend("/join/" + moveDto.getGameId(), authentication.getName());
    }
}