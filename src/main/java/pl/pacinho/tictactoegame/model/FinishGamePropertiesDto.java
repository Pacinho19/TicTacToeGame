package pl.pacinho.tictactoegame.model;

import lombok.Getter;

import java.util.List;

@Getter
public class FinishGamePropertiesDto {

    private String name;
    private List<String> cells;

    public FinishGamePropertiesDto(String name, List<String> cells) {
        this.name = name;
        this.cells = cells;
    }
}