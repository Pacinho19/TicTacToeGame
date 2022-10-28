package pl.pacinho.tictactoegame.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pacinho.tictactoegame.exception.GameNotFoundException;
import pl.pacinho.tictactoegame.model.GameDto;
import pl.pacinho.tictactoegame.model.MoveDto;
import pl.pacinho.tictactoegame.model.PlayerDto;
import pl.pacinho.tictactoegame.model.enums.GameStatus;
import pl.pacinho.tictactoegame.model.enums.Symbol;
import pl.pacinho.tictactoegame.repository.GameRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;

    public void playerMove(String name, MoveDto moveDto) {
        GameDto game = findById(moveDto.getGameId());
        game.makeMove(moveDto, getPlayerSymbol(name, game));
        game.switchPlayer();
    }

    private Symbol getPlayerSymbol(String name, GameDto game) {
        if (game.getPlayerMove().getName() == null || !game.getPlayerMove().getName().equals(name))
            throw new IllegalStateException("It's not your turn!");

        return game.getPlayerMove().getSymbol();
    }

    public List<GameDto> getAvailableGames() {
        return gameRepository.getAvailableGames();
    }

    public String newGame(String name) {
        return gameRepository.newGame(name);
    }

    public GameDto findById(String gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }

    public boolean checkOpenGame(String gameId, String name) {
        GameDto game = findById(gameId);
        return game.getPlayers()
                .stream()
                .anyMatch(p -> p.getName() != null && p.getName().equals(name));
    }

    public void joinGame(String name, String gameId) {
        GameDto game = gameRepository.joinGame(name, gameId);
        game.setStatus(GameStatus.IN_PROGRESS);
    }

    public boolean checkPlayerMove(GameDto game, String name) {
        PlayerDto nextPlayer = game.getPlayerMove();
        if (nextPlayer == null) return false;
        if (nextPlayer.getName() == null) return false;
        return nextPlayer.getName().equals(name);
    }
}
