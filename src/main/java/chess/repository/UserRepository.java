package chess.repository;

import chess.domain.board.Team;
import chess.domain.exception.DataException;
import chess.dto.UserInfoDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(String roomId, String password, String team) {
        final String query = "INSERT INTO User (room_id, password, team) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, roomId, password, team);
    }

    public Team findTeamByPassword(UserInfoDto userInfo) {
        final String query = "SELECT team FROM User WHERE password = ? AND room_id = ?";
        final String team = jdbcTemplate.queryForObject(query, String.class, userInfo.getPassword(), userInfo.getId());
        if (Objects.isNull(team)) {
            throw new DataException("Team이 존재하지 않습니다.");
        }
        return Team.valueOf(team.toUpperCase());
    }

    public boolean isExist(String roomId, String password) {
        final String query = "SELECT count(*) FROM User WHERE room_id = ? AND password = ?";
        final int counts = jdbcTemplate.queryForObject(query, Integer.class, roomId, password);
        return counts > 0;
    }
}