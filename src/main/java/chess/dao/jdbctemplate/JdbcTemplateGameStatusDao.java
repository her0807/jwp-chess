package chess.dao.jdbctemplate;

import chess.dao.GameStatusDao;
import chess.domain.GameStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateGameStatusDao implements GameStatusDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateGameStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void update(String nowStatus, String nextStatus) {
        String sql = "update game_status set status = ? where status = ?";
        jdbcTemplate.update(sql, nextStatus, nowStatus);
    }

    @Override
    public String getStatus() {
        final String sql = "select * from game_status";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    @Override
    public void reset(GameStatus data) {
        removeAll();
        String sql = "insert into game_status (status) values (?)";
        jdbcTemplate.update(sql, data.toString());
    }

    private void removeAll() {
        String sql = "truncate table game_status";
        jdbcTemplate.update(sql);
    }
}
