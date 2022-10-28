package pl.pacinho.tictactoegame.ui;

public class UIConfig {
    public static final String HOME = "/tic-tac-toe";
    public static final String GAMES = HOME + "/games";
    public static final String NEW_GAME = GAMES + "/new";
    public static final String GAME_PAGE = GAMES + "/{gameId}";
    public static final String JOIN_TO_GAME = GAME_PAGE + "/join";
    public static final String PLAYER_MOVE = GAME_PAGE + "/player-move";
}