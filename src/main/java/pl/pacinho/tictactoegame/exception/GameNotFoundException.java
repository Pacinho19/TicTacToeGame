package pl.pacinho.tictactoegame.exception;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(String gameId) {
        super("Game " + gameId + " not found!");
    }
}
