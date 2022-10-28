package pl.pacinho.tictactoegame.utils;

import org.apache.commons.lang3.tuple.Pair;
import pl.pacinho.tictactoegame.model.FinishGamePropertiesDto;
import pl.pacinho.tictactoegame.model.enums.Symbol;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FinishOptions {

    private static List<List<Pair<Integer, Integer>>> options = List.of(
            List.of(Pair.of(0, 0), Pair.of(0, 1), Pair.of(0, 2)),
            List.of(Pair.of(1, 0), Pair.of(1, 1), Pair.of(1, 2)),
            List.of(Pair.of(2, 0), Pair.of(2, 1), Pair.of(2, 2)),

            List.of(Pair.of(0, 0), Pair.of(1, 0), Pair.of(2, 0)),
            List.of(Pair.of(0, 1), Pair.of(1, 1), Pair.of(2, 1)),
            List.of(Pair.of(0, 2), Pair.of(1, 2), Pair.of(2, 2)),

            List.of(Pair.of(0, 0), Pair.of(1, 1), Pair.of(2, 2)),
            List.of(Pair.of(0, 2), Pair.of(1, 1), Pair.of(2, 0))
    );

    public static FinishGamePropertiesDto check(Symbol[][] board) {
        if (checkDraw(board)) return new FinishGamePropertiesDto("DRAW!", Collections.emptyList());

        for (List<Pair<Integer, Integer>> row : options) {
            List<String> lineValues = getLineValues(board, row);
            if (lineValues.size() == 1 && !lineValues.get(0).equals(Symbol.FREE.name()))
                return new FinishGamePropertiesDto(lineValues.get(0), getCellList(row));
        }
        return null;
    }

    private static List<String> getCellList(List<Pair<Integer, Integer>> row) {
        return row.stream()
                .map(p -> p.getLeft() + "x" + p.getRight())
                .toList();
    }

    private static List<String> getLineValues(Symbol[][] board, List<Pair<Integer, Integer>> row) {
        return row.stream()
                .map(idx -> board[idx.getLeft()][idx.getRight()].name())
                .distinct()
                .toList();
    }

    private static boolean checkDraw(Symbol[][] board) {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .allMatch(s -> s == Symbol.O || s == Symbol.X);
    }
}