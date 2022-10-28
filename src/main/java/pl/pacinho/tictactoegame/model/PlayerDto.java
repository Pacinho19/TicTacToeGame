package pl.pacinho.tictactoegame.model;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.tictactoegame.model.enums.Symbol;

@Setter
@Getter
public class PlayerDto {

    private String name;
    private Symbol symbol;
    public PlayerDto() {
        this.symbol = Symbol.O;
    }

    public PlayerDto(String name) {
        this.name = name;
        this.symbol = Symbol.X;
    }
}
