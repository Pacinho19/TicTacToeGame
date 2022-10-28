package pl.pacinho.tictactoegame.utils;

import pl.pacinho.tictactoegame.model.enums.Symbol;

public class BoardUtils {

//    private static final Symbol[] row = {Symbol.FREE, Symbol.FREE, Symbol.FREE};

    public static Symbol[][] emptyBoard() {
        return new Symbol[][]{
                {Symbol.FREE, Symbol.FREE, Symbol.FREE},
                {Symbol.FREE, Symbol.FREE, Symbol.FREE},
                {Symbol.FREE, Symbol.FREE, Symbol.FREE}
        };
    }
}
