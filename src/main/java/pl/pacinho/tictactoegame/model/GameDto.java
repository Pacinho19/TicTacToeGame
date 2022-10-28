package pl.pacinho.tictactoegame.model;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.tictactoegame.model.enums.GameStatus;
import pl.pacinho.tictactoegame.model.enums.Symbol;
import pl.pacinho.tictactoegame.utils.BoardUtils;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

@Getter
public class GameDto {

    private String id;
    @Setter
    private GameStatus status;
    private LinkedList<PlayerDto> players;
    private LocalDateTime startTime;

    private Symbol[][] board;

    public GameDto(String player1) {
        board = BoardUtils.emptyBoard();
        players = new LinkedList<>();
        players.add(new PlayerDto(player1));
        players.add(new PlayerDto());
        this.id = UUID.randomUUID().toString();
        this.status = GameStatus.NEW;
        this.startTime = LocalDateTime.now();
    }
}