package chess;

import chess.controller.ChessWebController;
import chess.dao.JdbcBoardDao;
import chess.dao.JdbcGameStatusDao;
import chess.dao.JdbcTurnDao;
import chess.domain.ChessGameService;
import chess.domain.board.strategy.WebBasicBoardStrategy;

public class WebApplication {

    private static final ChessWebController controller = new ChessWebController(new ChessGameService(new JdbcBoardDao(), new JdbcTurnDao(), new JdbcGameStatusDao()));

    public static void main(final String... args) {
        controller.start(new WebBasicBoardStrategy());
    }
}

