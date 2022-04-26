package chess.controller;

import chess.domain.ChessGameService;
import chess.domain.board.strategy.WebBasicBoardStrategy;
import chess.dto.ErrorDto;
import chess.dto.GameStatusDto;
import chess.dto.MoveDto;
import chess.dto.ScoreDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChessSpringController {

    private final ChessGameService chessGameService;

    public ChessSpringController(ChessGameService chessGameService) {
        this.chessGameService = chessGameService;
    }

    @GetMapping("/")
    public String enter() {
        chessGameService.init();
        return "index";
    }

    @ResponseBody
    @GetMapping("/start")
    public ResponseEntity start() {
        GameStatusDto status = chessGameService.startChessGame(new WebBasicBoardStrategy());
        return ResponseEntity.ok().body(status);
    }

    @ResponseBody
    @PostMapping("/move")
    public ResponseEntity move(@RequestBody MoveDto moveDto) {
        try {
            GameStatusDto status = chessGameService.move(moveDto.getFrom(), moveDto.getTo());
            return ResponseEntity.ok().body(status);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorDto(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping("/status")
    public ResponseEntity status() {
        try {
            ScoreDto score = chessGameService.createScore();
            return ResponseEntity.ok().body(score);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorDto(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping("/end")
    public ResponseEntity end() {
        try {
            ScoreDto score = chessGameService.end();
            return ResponseEntity.ok().body(score);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorDto(e.getMessage()));
        }
    }
}
