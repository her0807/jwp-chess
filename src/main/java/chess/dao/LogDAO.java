package chess.dao;

import chess.domain.Movement;
import chess.exception.NoLogsException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LogDAO {
    private final JdbcTemplate jdbcTemplate;

    public LogDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createLog(final String roomId, final String startPoint, final String endPoint) {
        String query = "INSERT INTO log (room_id, start_position, end_position) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, roomId, startPoint, endPoint);
    }

    public void deleteLogByRoomId(final String roomId) {
        String query = "DELETE FROM log WHERE room_id = ?";
        jdbcTemplate.update(query, roomId);
    }

    public List<Movement> allLogByRoomId(final String roomId) {
        String query = "SELECT start_position, end_position FROM log WHERE room_id = ? ORDER BY register_date";
        List<Movement> logs = jdbcTemplate.query(query, mapper(), roomId);
        if (logs.isEmpty()) {
            throw new NoLogsException(roomId);
        }
        return logs;
    }

    private RowMapper<Movement> mapper() {
        return (resultSet, rowNum) -> new Movement(
                resultSet.getString("start_position"),
                resultSet.getString("end_position")
        );
    }
}
