package pl.pacinho.tictactoegame.model;

import lombok.Getter;

@Getter
public class MoveDto {

    private String gameId;
    private int x;
    private int y;
}
