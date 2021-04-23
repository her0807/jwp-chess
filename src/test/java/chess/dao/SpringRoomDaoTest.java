package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.dao.spring.SpringRoomDao;
import chess.dto.web.RoomDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class SpringRoomDaoTest {

    private SpringRoomDao springRoomDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE room");

        jdbcTemplate.execute(
            "INSERT INTO room (id, name, is_opened, white, black) VALUES (1, 'room1', true, 'white1', 'black1')");
        jdbcTemplate.execute(
            "INSERT INTO room (id, name, is_opened, white, black) VALUES (2, 'room2', false , 'white2', 'black2')");

        springRoomDao = new SpringRoomDao(jdbcTemplate);
    }

    @DisplayName("열린 방 조회 기능")
    @Test
    void openedRooms() {
        List<RoomDto> openedRooms = springRoomDao.openedRooms();

        assertThat(openedRooms).hasSize(1);
        assertThat(openedRooms.get(0).getName()).isEqualTo("room1");
        assertThat(openedRooms.get(0).getWhite()).isEqualTo("white1");
        assertThat(openedRooms.get(0).getBlack()).isEqualTo("black1");
    }

    @DisplayName("방 생성 기능")
    @Test
    void insert() {
        springRoomDao.insert(new RoomDto("", "room3", "white3", "black2"));

        List<RoomDto> openedRooms = springRoomDao.openedRooms();

        assertThat(
            openedRooms.stream().filter(roomDto -> roomDto.getName().equals("room3")).count())
            .isEqualTo(1);
    }

    @DisplayName("방 삭제 기능")
    @Test
    void close() {
        springRoomDao.close("1");

        List<RoomDto> openedRooms = springRoomDao.openedRooms();

        assertThat(openedRooms).hasSize(0);
    }
}