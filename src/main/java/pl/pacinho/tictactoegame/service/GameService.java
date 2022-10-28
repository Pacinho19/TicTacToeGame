package pl.pacinho.tictactoegame.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pacinho.tictactoegame.exception.GameNotFoundException;
import pl.pacinho.tictactoegame.model.FinishGamePropertiesDto;
import pl.pacinho.tictactoegame.model.GameDto;
import pl.pacinho.tictactoegame.model.MoveDto;
import pl.pacinho.tictactoegame.model.PlayerDto;
import pl.pacinho.tictactoegame.utils.FinishOptions;
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

        checkEndGame(game);
        if (game.getStatus() != GameStatus.FINISHED) game.switchPlayer();
    }

    private void checkEndGame(GameDto game) {
        FinishGamePropertiesDto finishGamePropertiesDto = FinishOptions.check(game.getBoard());
        if (finishGamePropertiesDto == null) return;

        game.setStatus(GameStatus.FINISHED);
        game.setWinnerInfo(getWinnerInfo(finishGamePropertiesDto.getName(), game));
        game.setWinnerCells(finishGamePropertiesDto.getCells());
    }

    private String getWinnerInfo(String win, GameDto game) {
        return game.getPlayers()
                .stream()
                .filter(p -> p.getSymbol().name().equals(win))
                .map(p -> p.getName() + " win the game!")
                .findFirst()
                .orElse(win);
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
        List<GameDto> activeGames = getAvailableGames();
        if (activeGames.size() >= 10)
            throw new IllegalStateException("Cannot create new Game! Active game count : " + activeGames.size());
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
        if (game.getStatus() == GameStatus.FINISHED) return false;
        if (nextPlayer == null) return false;
        if (nextPlayer.getName() == null) return false;
        return nextPlayer.getName().equals(name);
    }
}