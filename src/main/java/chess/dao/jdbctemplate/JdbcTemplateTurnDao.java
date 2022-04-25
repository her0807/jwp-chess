package chess.dao.jdbctemplate;

import chess.dao.TurnDao;
import chess.domain.piece.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateTurnDao implements TurnDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateTurnDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void init() {
        jdbcTemplate.update("INSERT INTO turn(team) VALUES (?)", "white");
    }

    @Override
    public void update(String nowTurn, String nextTurn) {
        String sql = "update turn set team = ? where team = ?";
        jdbcTemplate.update(sql, nextTurn, nowTurn);
    }

    @Override
    public String getTurn() {
        String sql = "select * from turn";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    @Override
    public void reset() {
        removeAll();
        String sql = "insert into turn (team) values (?)";
        jdbcTemplate.update(sql, Team.WHITE.toString());
    }

    private void removeAll() {
        String sql = "truncate table turn";
        this.jdbcTemplate.execute(sql);
    }
}
